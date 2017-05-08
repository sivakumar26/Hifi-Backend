package com.hifi;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hifi.model.VerifyAuthentication;
import com.hifi.model.notification.TaskAcceptedNotification;
import com.hifi.model.notification.TaskCompleteNotification;
import com.hifi.pushnotifications.FirebaseAuth;

@Path("/push-notification")
public class PushNotificationResource {

	FirebaseAuth firebase;
	Response response;

	@Path("/accepted-notification")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)

	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public Response sendPushNotificationToDevice(@HeaderParam("token") String token,
			TaskAcceptedNotification notification) throws FileNotFoundException, IOException {

		response = VerifyAuthentication.verify(token);
		if (response.getStatus() == 200) {
			ObjectMapper objectMapper = new ObjectMapper();
			String arrayToJson = objectMapper.writeValueAsString(notification);

			firebase = new FirebaseAuth();
			System.out.println(notification.getPostId());
			firebase.authenticateAndSendNotification(notification);

			response = Response.ok().entity(arrayToJson).build();
		}

		return response;

	}

	@Path("/completed-notification")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)

	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public Response sendPushNotificationToDevice(@HeaderParam("token") String token,
			TaskCompleteNotification notification) throws FileNotFoundException, IOException {

		response = VerifyAuthentication.verify(token);
		if (response.getStatus() == 200) {
			ObjectMapper objectMapper = new ObjectMapper();
			String arrayToJson = objectMapper.writeValueAsString(notification);

			firebase = new FirebaseAuth();
			System.out.println(notification.getPostId());
			firebase.authenticateAndSendNotification(notification);

			response = Response.ok().entity(arrayToJson).build();
		}

		return response;

	}
}
