package com.hifi.firebase.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.hifi.notifications.firebase.ValidateUser;

public class FirebaseAccessToken {

	public static String getFirebaseToken() throws FileNotFoundException, IOException {
		GoogleCredential googleCred = GoogleCredential
				.fromStream(FirebaseAccessToken.class.getClassLoader().getResourceAsStream("hifi-8ee556834657.json"));
		GoogleCredential scoped = googleCred.createScoped(Arrays.asList(
				"https://www.googleapis.com/auth/firebase.database", "https://www.googleapis.com/auth/userinfo.email"));
		scoped.refreshToken();
		String token = scoped.getAccessToken();
		return token;
		
	}
}
