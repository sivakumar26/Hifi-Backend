package com.hifi.model;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import com.hifi.PostResource;

public class VerifyAuthentication {
	
	static Logger logger = Logger.getLogger(VerifyAuthentication.class);


	public static Response verify(String token)
	{
		if (token == null) {
			StatusMessage statusMessage = new StatusMessage();
			statusMessage.setStatus(Status.FORBIDDEN.getStatusCode());
			statusMessage.setMessage("Access Denied for this functionality !!!");
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity(statusMessage).build();
		}

		JsonWebKeySet jwks = new JsonWebKeySet(JsonWebKeys.getJwkList());
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
		
		return Response.ok().build();

	}
}
