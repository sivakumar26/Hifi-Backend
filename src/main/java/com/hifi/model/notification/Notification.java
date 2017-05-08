package com.hifi.model.notification;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notification {

	private String sender;
	private String recepient;
	private boolean accepted;
	private String postId;
	private long acceptedTimeStamp;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecepient() {
		return recepient;
	}
	public void setRecepient(String recepient) {
		this.recepient = recepient;
	}
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public long getAcceptedTimeStamp() {
		return acceptedTimeStamp;
	}
	public void setAcceptedTimeStamp(long acceptedTimeStamp) {
		this.acceptedTimeStamp = acceptedTimeStamp;
	}
	
	
	
	
}
