package com.hifi;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hifi.ObjectMapper.UserMapJson;
import com.hifi.model.User;
import com.hifi.model.VerifyAuthentication;
import com.hifi.neo4j.CreateIdentities;
import com.hifi.neo4j.FindIdentities;
import com.hifi.repository.User.UserRepository;
import com.hifi.repository.User.UserRepositoryStub;

@Path("/user") /* http://localhost:8080/hifi-services/webapi/user */
public class UserResource {

	UserRepository userRepository = new UserRepositoryStub();
	CreateIdentities neo4jobj = new CreateIdentities();
	FindIdentities findneo4j = new FindIdentities();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createPost(@HeaderParam("token") String token, User user) {
		User pojoUser = new User();
		Response response = null;

		try {

			response = VerifyAuthentication.verify(token);

			if (response.getStatus() == 200) {
				UserMapJson userMapJson = new UserMapJson(user);
				pojoUser = userMapJson.getObject();

				// adding user in Neo4j
				neo4jobj.createUser(pojoUser.getLastName() + "," + pojoUser.getFirstName(), pojoUser.getFacebookId());

				// adding user in MongoDB
				userRepository.createUser(pojoUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@POST
	@Path("/relation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createRelation(@HeaderParam("token") String token, String FriendListJson) {

		Response response = VerifyAuthentication.verify(token);
		JSONParser jsonParser = new JSONParser();
		try {

			if (response.getStatus() == 200) {
				JSONObject json = (JSONObject) jsonParser.parse(FriendListJson);
				String userId = json.get("facebookId").toString();
				JSONArray slideContent = (JSONArray) json.get("friends");
				Iterator i = slideContent.iterator();
				while (i.hasNext()) {
					JSONObject slide = (JSONObject) i.next();
					String friendId = (String) slide.get("facebookId");
					System.out.println("Printing friend id now ---------------->" + friendId);
					if (findneo4j.isUser(friendId)) {
						neo4jobj.createRelationship(userId, friendId);
					}
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return response;
	}

}
