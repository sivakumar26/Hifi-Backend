package com.hifi.firebase.model;

import org.bson.types.ObjectId;

public class FirebaseCreator {

	private String userFacebookId;
	private ObjectId postId;

	public String getUserFacebookId() {
		return userFacebookId;
	}

	public void setUserFacebookId(String userFacebookId) {
		this.userFacebookId = userFacebookId;
	}

	public ObjectId getPostId() {
		return postId;
	}

	public void setPostId(ObjectId postId) {
		this.postId = postId;
	}
}
