package com.hifi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hifi.database.DatabaseOperations;
import com.hifi.database.RedisSingleton;
import com.hifi.model.Post;
import com.hifi.model.VerifyAuthentication;

import redis.clients.jedis.Jedis;

@Path("/friendsPost")
public class FriendsPostResource {

	private RedisSingleton redisSingleton;
	private Jedis jedis;
	Response response;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{userFacebookId}")

	public Response getFriendsPost(@HeaderParam("token") String token,
			@PathParam("userFacebookId") String userFacebookId) throws IOException {

		JSONObject jObj = new JSONObject();
		System.out.println("Hi there" + userFacebookId);

		response = VerifyAuthentication.verify(token);
		if (response.getStatus() == 200) {
			redisSingleton = RedisSingleton.getInstance();
			jedis = redisSingleton.getJedis();
			List<Post> postResults = new ArrayList<Post>();

			List<String> postIds = jedis.lrange(userFacebookId, 0, -1);

			// Iterate over and give the relevant array of posts
			for (String pid : postIds) {
				System.out.println(pid);
				postResults.add(DatabaseOperations.getFriendsPost(new ObjectId(pid)));
			}

			ObjectMapper objectMapper = new ObjectMapper();
			ArrayNode array = objectMapper.createArrayNode();

			for (Post post : postResults) {
				String arrayToJson = objectMapper.writeValueAsString(post);
				JsonNode parsedJson = objectMapper.readTree(arrayToJson);
				array.add(parsedJson);
			}

			// System.out.println(arrayToJson);
			jObj.put("posts", array);

			response = Response.ok().entity(jObj.toString()).build();
		}
		return response;

	}
}
