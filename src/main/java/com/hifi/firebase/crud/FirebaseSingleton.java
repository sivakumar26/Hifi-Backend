package com.hifi.firebase.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

public class FirebaseSingleton {

	public static boolean initialize=false; 
	
	public static void initializeFirebaseSingleton() throws URISyntaxException, FileNotFoundException
	{
		URL resource = CreatorOperations.class.getClassLoader()
				.getResource("hifi-f43ff-firebase-adminsdk-ecvta-9515dc5532.json");

		File file = new File(resource.toURI());
		FileInputStream serviceAccount = new FileInputStream(file);

		// Initialize the app with a service account, granting admin privileges
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
				.setDatabaseUrl("https://hifi-f43ff.firebaseio.com").build();
		
		FirebaseApp.initializeApp(options);
		initialize=true;
		
	}
	
	public static boolean checkIfFirebaseInitialized()
	{
		return initialize;
	}
	
	
}
