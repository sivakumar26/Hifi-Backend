package com.hifi.firebase.crud;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hifi.firebase.model.FirebaseUserProfile;
import com.hifi.firebase.util.FirebaseAccessToken;
import com.hifi.http.HttpConnection;
import com.hifi.http.HttpConnectionImpl;
import com.hifi.model.User;

public class UserOperations {

	private FirebaseUserProfile userProfile;
	private ObjectMapper mapper = new ObjectMapper();
	String url = "https://hifi-f43ff.firebaseio.com/users/";
	HttpConnection httpConn = new HttpConnectionImpl();

	public UserOperations(User user) {

		this.userProfile = new FirebaseUserProfile();
		this.userProfile.setBirthday(user.getBirthday());
		this.userProfile.setEmail(user.getEmail());
		this.userProfile.setFirstName(user.getFirstName());
		this.userProfile.setLastName(user.getLastName());
		this.userProfile.setGender(user.getGender());
		this.userProfile.setUserFacebookId(user.getFacebookId());
		this.userProfile.setUserProfilePicture(user.getProfile_pic());

	}

	public Response createUserProfile() throws Exception {

		if (!FirebaseSingleton.checkIfFirebaseInitialized()) {

			FirebaseSingleton.initializeFirebaseSingleton();

		}

		final FirebaseDatabase database = FirebaseDatabase.getInstance();

		DatabaseReference ref = database.getReference();

		DatabaseReference userRef = ref.child("users");
		//DatabaseReference userRef = creatorRef.child("users");
		userRef.child(this.userProfile.getUserFacebookId().toString()).setValue(this.userProfile.toString());

		return Response.ok().build();

	}

	public static void main(String arg[]) throws Exception {
		User user = new User();
		user.setBirthday("2121212");
		user.setEmail("ssdsdewed");
		user.setFacebookId("ddsdasdasdsadsad");
		user.setFirstName("Abhish");
		user.setGender("Male");
		user.setLastName("Kulkarni");

		UserOperations uops = new UserOperations(user);
		uops.createUserProfile();
	}

}