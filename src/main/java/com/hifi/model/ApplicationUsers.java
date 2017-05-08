package com.hifi.model;

public class ApplicationUsers {

	public enum ROLES {
		BASIC_USER, PREMIUM_USER, ADMIN
	}

	private String firstName;
	private String lastName;
	private ROLES role;

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

}
