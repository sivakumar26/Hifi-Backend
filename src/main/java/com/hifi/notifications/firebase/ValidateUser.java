package com.hifi.notifications.firebase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.hifi.model.ApplicationUsers;

public class ValidateUser {

	private static final String USER_AGENT = "Mozilla/5.0";
	static ApplicationUsers appUser;

	public static ApplicationUsers validateUser(String userFacebookId) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Printingn" + new File("tets.txt").getAbsolutePath());
		System.out.println(new File("tet.txt").getPath());

		GoogleCredential googleCred = GoogleCredential
				.fromStream(ValidateUser.class.getResourceAsStream("hifi-8ee556834657.json"));
		GoogleCredential scoped = googleCred.createScoped(Arrays.asList(
				"https://www.googleapis.com/auth/firebase.database", "https://www.googleapis.com/auth/userinfo.email"));
		scoped.refreshToken();
		String token = scoped.getAccessToken();

		URL obj = new URL("https://hifi-f43ff.firebaseio.com/users/" + userFacebookId + ".json?access_token=" + token);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		System.out.println("Printing user url " + obj.toString());

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + "posts.json");
		System.out.println("Response Code : " + responseCode);
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

		String registrationToken;
		System.out.println("Output from Server .... \n");
		while ((registrationToken = br.readLine()) != null) {
			appUser = mapper.readValue(registrationToken, ApplicationUsers.class);
		}

		return appUser;

	}

}
