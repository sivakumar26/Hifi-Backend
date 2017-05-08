package com.hifi;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.json.simple.JSONObject;

import com.hifi.model.ApplicationUsers;
import com.hifi.model.JsonWebKeys;
import com.hifi.model.StatusMessage;
import com.hifi.notifications.firebase.ValidateUser;

@Path("/security")
public class Authentication {
	static Logger logger = Logger.getLogger(Authentication.class);
	static List<JsonWebKey> jwkList = null;

	static {
		logger.info("Inside static initializer...");
		jwkList = new LinkedList<JsonWebKey>();
		for (int kid = 1; kid <= 3; kid++) {
			JsonWebKey jwk = null;
			try {
				jwk = RsaJwkGenerator.generateJwk(2048);
				logger.info("PUBLIC KEY (" + kid + "): " + jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
			} catch (JoseException e) {
				e.printStackTrace();
			}
			jwk.setKeyId(String.valueOf(kid));
			jwkList.add(jwk);
		}
		JsonWebKeys.setJwkList(jwkList);

	}

	@Path("/status")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "JwtSecurityExample Status is OK...";
	}

	@Path("/authenticate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateCredentials(@HeaderParam("username") String username) throws Exception {

		logger.info("Authenticating User Credentials...");

		if (username == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.PRECONDITION_FAILED.getStatusCode());
			statusMessage.setMessage("Username value is missing!!!");
			return Response.status(Status.PRECONDITION_FAILED.getStatusCode()).entity(statusMessage).build();
		}

		ApplicationUsers user = ValidateUser.validateUser(username);
		if (user == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(0);

		senderJwk.setKeyId("1");
		logger.info("JWK (1) ===> " + senderJwk.toJson());

		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("hifi.com");
		claims.setExpirationTimeMinutesInTheFuture(10);
		claims.setGeneratedJwtId();
		claims.setIssuedAtToNow();
		claims.setNotBeforeMinutesInThePast(2);
		claims.setSubject(user.getFirstName() + " " + user.getLastName());
		// claims.setStringClaim("roles", user.getRole().toString());

		JsonWebSignature jws = new JsonWebSignature();

		jws.setPayload(claims.toJson());

		jws.setKeyIdHeaderValue(senderJwk.getKeyId());
		jws.setKey(senderJwk.getPrivateKey());

		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		String jwt = null;
		try {
			jwt = jws.getCompactSerialization();
		} catch (JoseException e) {
			e.printStackTrace();
		}

		logger.info("Printing jwt" + jwt);

		JSONObject tokenObj = new JSONObject();
		tokenObj.put("token", jwt);

		return Response.ok().entity(tokenObj.toString()).build();
	}

	// --- Protected resource using JWT Tokens` ---
	@Path("/test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findItemById(@HeaderParam("token") String token)
			throws JsonGenerationException, JsonMappingException, IOException {

		if (token == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		JsonWebKeySet jwks = new JsonWebKeySet(jwkList);
		JsonWebKey jwk = jwks.findJsonWebKey("1", null, null, null);
		logger.info("JWK (1) ===> " + jwk.toJson());

		// Validate Token's authenticity and check claims
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime().setAllowedClockSkewInSeconds(30)
				.setRequireSubject().setExpectedIssuer("hifi.com").setVerificationKey(jwk.getKey()).build();

		try {
			// Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			logger.info("JWT validation succeeded! " + jwtClaims);
		} catch (InvalidJwtException e) {
			logger.error("JWT is Invalid: " + e);
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		/*
		 * MongoDBSingleton mongoDB = MongoDBSingleton.getInstance();
		 * MongoDatabase db = mongoDB.getDatabase();
		 * 
		 * BasicDBObject query = new BasicDBObject(); query.put("_id", item_id);
		 * List<Document> results =
		 * db.getCollection("items").find(query).into(new
		 * ArrayList<Document>()); int size = results.size();
		 * 
		 * if (size == 0) { StatusMessage statusMessage = new StatusMessage();
		 * statusMessage.setStatus(Status.PRECONDITION_FAILED.getStatusCode());
		 * statusMessage.setMessage("Unable to find that item !!!"); return
		 * Response.status(Status.PRECONDITION_FAILED.getStatusCode()).entity(
		 * statusMessage).build(); }
		 * 
		 * for (Document current : results) { ObjectMapper mapper = new
		 * ObjectMapper(); try { logger.info(current.toJson()); item =
		 * mapper.readValue(current.toJson(), Item.class); } catch
		 * (JsonParseException e) { e.printStackTrace(); } catch
		 * (JsonMappingException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); } }
		 */

		return Response.status(200).entity("HI THERE").build();
	}

	/*
	 * private User validUser(String username, String password) {
	 * MongoDBSingleton mongoDB = MongoDBSingleton.getInstance(); MongoDatabase
	 * db = mongoDB.getDatabase(); List<Document> results = null;
	 * 
	 * results = db.getCollection("users").find(new Document("username",
	 * username)).limit(1) .into(new ArrayList<Document>()); int size =
	 * results.size();
	 * 
	 * if (size == 1) { for (Document current : results) { ObjectMapper mapper =
	 * new ObjectMapper(); User user = null; try { //
	 * logger.info(current.toJson()); user = mapper.readValue(current.toJson(),
	 * User.class); } catch (JsonParseException e) { e.printStackTrace(); }
	 * catch (JsonMappingException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } if (user != null &&
	 * username.equals(user.getUsername()) &&
	 * password.equals(user.getPassword())) { return user; } else { return null;
	 * } } return null; } else { return null; } }
	 */
}