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

@Entity("users")
public class User {

	private String profile_pic;
	private String name;
	private String facebookId;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String birthday;
	@Embedded
	private Location location;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	@JsonSerialize(using = ToStringSerializer.class)

	@Id
	private ObjectId userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ObjectId getUserId() {
		return this.userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	@Override
	public String toString() {
		return " {profile_pic=" + profile_pic + ", name=" + name + ", facebookId=" + facebookId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + ", birthday="
				+ birthday + ", location=" + location + ", userId=" + userId + "}";
	}
	
}
