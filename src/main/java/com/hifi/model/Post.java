package com.hifi.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@XmlRootElement
@Indexes(@Index(fields = @Field(value = "$**", type = IndexType.TEXT)))
@Entity("posts")
public class Post {

	public enum POST_STATUS {
		ACTIVE, IN_PROCESS, COMPLETED
	};

	private String postDescription;

	@JsonSerialize(using = ToStringSerializer.class)
	@Id
	private ObjectId postId;
	private long postTimeStamp;
	private String postTitle;

	private String userFacebookId;
	private String userPostId;
	private POST_STATUS postStatus;

	public POST_STATUS getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(POST_STATUS postStatus) {
		this.postStatus = postStatus;
	}

	public String getUserPostId() {
		return userPostId;
	}

	public void setUserPostId(String userPostId) {
		this.userPostId = userPostId;
	}

	public String getUserFacebookId() {
		return userFacebookId;
	}

	public void setUserFacebookId(String userFacebookId) {
		this.userFacebookId = userFacebookId;
	}

	@Embedded
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public ObjectId getPostId() {
		return postId;
	}

	public void setPostId(ObjectId postId) {
		this.postId = postId;
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

	@Override
	public String toString() {
		return " {postDescription=" + postDescription + ", postId=" + postId + ", postTimeStamp=" + postTimeStamp
				+ ", postTitle=" + postTitle + ", userFacebookId=" + userFacebookId + ", userPostId=" + userPostId
				+ ", postStatus=" + postStatus + ", user=" + user + "}";
	}

}
