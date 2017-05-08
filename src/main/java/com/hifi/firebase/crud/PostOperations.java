package com.hifi.firebase.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hifi.firebase.model.FirebasePost;
import com.hifi.firebase.util.FirebaseAccessToken;
import com.hifi.http.HttpConnection;
import com.hifi.http.HttpConnectionImpl;
import com.hifi.model.Post;

public class PostOperations {

	private FirebasePost firebasePost;
	HttpConnection httpConn = new HttpConnectionImpl();
	private ObjectMapper mapper = new ObjectMapper();
	String url = "https://hifi-f43ff.firebaseio.com/posts/";

	public PostOperations(Post post) {
		this.firebasePost = new FirebasePost();
		this.firebasePost.setPostDescription(post.getPostDescription());
		this.firebasePost.setPostTimeStamp(post.getPostTimeStamp());
		this.firebasePost.setPostTitle(post.getPostTitle());
		this.firebasePost.setPostId(post.getPostId());

		// this.firebasePost.setUserProfile(post.get);
	}

	public Response createFirebasePost() throws FileNotFoundException, IOException, Exception {

		if (!FirebaseSingleton.checkIfFirebaseInitialized()) {

			FirebaseSingleton.initializeFirebaseSingleton();

		}

		final FirebaseDatabase database = FirebaseDatabase.getInstance();

		DatabaseReference ref = database.getReference();

		DatabaseReference postRef = ref.child("posts");
		// DatabaseReference userRef = creatorRef.child("users");
		postRef.child(this.firebasePost.getPostId().toString()).setValue(this.firebasePost.toString());

		return Response.ok().build();

	}

}
