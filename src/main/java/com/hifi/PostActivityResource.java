package com.hifi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.hifi.database.RedisSingleton;
import com.hifi.http.HttpConnectionImpl;
import com.hifi.notifications.firebase.PostRequest;
import com.hifi.util.Constants;

@Path("/postActivity") /* http://localhost:8080/hifi-services/webapi/posts */

public class PostActivityResource {

	private HttpConnectionImpl httpConn;
	private RedisSingleton redisSingleton;
	private PostRequest postRequest;

	@GET
	@Path("/requestPostTask/{postRequest}")
	public Response requestPostTask(@PathParam("postRequest") String postRequestStr) throws Exception {
		JSONParser jsonParser = new JSONParser();
		JSONObject json = (JSONObject) jsonParser.parse(postRequestStr);

		if (json.get("postId") == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		// Get registration token for this userid from Cache and send to
		// Firebase Cloud Messaging/

		redisSingleton = RedisSingleton.getInstance();
		String registrationToken = redisSingleton.getJedis().get((String) json.get("userEmail"));

		// Prepare json string to send to Firebase Cloud Messaging

		
		postRequest = new PostRequest();
		postRequest.setPostDescription((String) json.get("postDescription"));
		postRequest.setPostId((ObjectId) json.get("postId"));
		postRequest.setUserEmail((String) json.get("userEmail"));
		postRequest.setUserId((ObjectId) json.get("userId"));
		postRequest.setDestination(registrationToken);

		httpConn = new HttpConnectionImpl();
		int conCode = httpConn.sendPost(Constants.FIREBASE_CLOUD_MESSAGING_SERVER, postRequest.toString());

		if (conCode == 201) {
			return Response.ok().entity("Requested Successfully").build();
		}
		return Response.serverError().entity("Unexpected server error").build();
	}

}
