package com.hifi.pushnotifications;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.hifi.firebase.util.FirebaseAccessToken;
import com.hifi.model.notification.Notification;

public class FirebaseAuth {

	private final String USER_AGENT = "Mozilla/5.0";
	PushNotificationHelper pushNotification;

	public void authenticateAndSendNotification(Notification notification) throws IOException {

		GoogleCredential googleCred = GoogleCredential
				.fromStream(FirebaseAuth.class.getClassLoader().getResourceAsStream("hifi-8ee556834657.json"));
		
		GoogleCredential scoped = googleCred.createScoped(Arrays.asList(
				"https://www.googleapis.com/auth/firebase.database", "https://www.googleapis.com/auth/userinfo.email"));
		scoped.refreshToken();
		String token = scoped.getAccessToken();

		URL obj = new URL("https://hifi-f43ff.firebaseio.com/tokens/"+notification.getRecepient()+".json?access_token=" + token);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		// con.setRequestProperty("Authorization", "bearer="+token);
		// con.setRequestProperty("Authorization", "key=" +
		// "AIzaSyAWmUcHDpzQZ4vT4bTqcwTc9qqMN8Fy4xs");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + "posts.json");
		System.out.println("Response Code : " + responseCode);
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

		String regsitrationToken;
		System.out.println("Output from Server .... \n");
		while ((regsitrationToken = br.readLine()) != null) {

		}
		pushNotification=new PushNotificationHelper();

		pushNotification.sendPushNotification(regsitrationToken,notification);

	}

}
