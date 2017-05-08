package com.hifi.firebase.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.core.Response;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssigneeOperations {

	public Response buildAssignee(String userFacebookId, String postId)
			throws FileNotFoundException, URISyntaxException {

		if (!FirebaseSingleton.checkIfFirebaseInitialized()) {

			FirebaseSingleton.initializeFirebaseSingleton();

		}

		final FirebaseDatabase database = FirebaseDatabase.getInstance();

		DatabaseReference ref = database.getReference();

		DatabaseReference creatorRef = ref.child("assignee");
		DatabaseReference userRef = creatorRef.child("users");
		userRef.child(userFacebookId).child("posts").child(postId).setValue(true);

		return Response.ok().build();

	}

	public Response removeAssginee(String userFacebookId, String postId)
			throws FileNotFoundException, URISyntaxException {

		if (!FirebaseSingleton.checkIfFirebaseInitialized()) {

			FirebaseSingleton.initializeFirebaseSingleton();

		}

		final FirebaseDatabase database = FirebaseDatabase.getInstance();

		DatabaseReference ref = database.getReference();

		DatabaseReference creatorRef = ref.child("assignee");
		DatabaseReference userRef = creatorRef.child("users");
		userRef.child(userFacebookId).child("posts").child(postId).removeValue();

		return Response.ok().build();
	}
}
