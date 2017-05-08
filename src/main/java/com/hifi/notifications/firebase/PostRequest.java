package com.hifi.notifications.firebase;

import org.bson.types.ObjectId;

public class PostRequest {

	private ObjectId postId;
	private String postDescription;
	private ObjectId userId;
	private String userEmail;
	private String destination;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public ObjectId getPostId() {
		return postId;
	}

	public void setPostId(ObjectId postId) {
		this.postId = postId;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "{ \"data\": { \"postId\":" + this.postId.toString() + "," + "\n" + "\"postDescription\":"
				+ this.postDescription + "," + "\n" + "\"userId\":" + this.userId.toString() + "," + "\n"
				+ "\"userEmail\":" + this.userEmail + "\n" + "}," + "\n" + "\"to\":" + this.destination + "\n" + "}";

	}
}
