package com.techery.dtat.user;

public class UserCredentials {
	public static final String DATA_FILE_NAME = "fixtures/user_credentials.json";
	private String username;
	private String password;

	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return username + " / " + password;
	}
}
