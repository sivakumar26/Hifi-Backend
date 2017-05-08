package com.hifi.pushnotifications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hifi.model.notification.Notification;
import com.hifi.model.notification.TaskAcceptedNotification;
import com.hifi.model.notification.TaskCompleteNotification;

public class PushNotificationHelper {
	public final static String AUTH_KEY_FCM = "AIzaSyAWmUcHDpzQZ4vT4bTqcwTc9qqMN8Fy4xs";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	public void sendPushNotification(String deviceToken, Notification notificationObj) throws IOException {

		URL url = new URL(API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode data = mapper.createObjectNode();
		
		if(notificationObj instanceof TaskCompleteNotification)
		{
			data.put("postId",notificationObj.getPostId());
			data.put("Completed-On", notificationObj.getAcceptedTimeStamp());
			data.put("Completed-By",notificationObj.getSender());
		}
		if(notificationObj instanceof TaskAcceptedNotification)
		{
			data.put("postId",notificationObj.getPostId());
			data.put("Accepted-On", notificationObj.getAcceptedTimeStamp());
			data.put("Accepted-By",notificationObj.getSender());

		}
		
		ObjectNode notification = mapper.createObjectNode();
		notification.put("to", deviceToken);
		notification.put("data", data);

		System.out.println("Sending " + notification + " to fcm server");

		try {
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(notification.toString());
			wr.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		System.out.println("FCM Notification is sent successfully");

	}
	
	
}
