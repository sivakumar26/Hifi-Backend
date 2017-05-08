package com.hifi.firebase.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class FirebasePost {

	public ObjectId getPostId() {
		return postId;
	}

	public void setPostId(ObjectId postId) {
		this.postId = postId;
	}

	private String userProfile;
	private String postTitle;
	private long postTimeStamp;
	private String postDescription;
	private String postCreator;
	private String postAssignee;
	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId postId;

	public String getPostCreator() {
		return postCreator;
	}

	public void setPostCreator(String postCreator) {
		this.postCreator = postCreator;
	}

	public String getPostAssignee() {
		return postAssignee;
	}

	public void setPostAssignee(String postAssignee) {
		this.postAssignee = postAssignee;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public long getPostTimeStamp() {
		return postTimeStamp;
	}

	public void setPostTimeStamp(long postTimeStamp) {
		this.postTimeStamp = postTimeStamp;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}
	
	@Override
	public String toString() {
		return "FirebasePost [userProfile=" + userProfile + ", postTitle=" + postTitle + ", postTimeStamp="
				+ postTimeStamp + ", postDescription=" + postDescription + ", postCreator=" + postCreator
				+ ", postAssignee=" + postAssignee + ", postId=" + postId + "]";
	}
	
	

}
