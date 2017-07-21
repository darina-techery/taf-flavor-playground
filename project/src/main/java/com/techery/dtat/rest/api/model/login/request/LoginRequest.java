package com.techery.dtat.rest.api.model.login.request;

import com.google.gson.annotations.SerializedName;
import com.techery.dtat.user.UserCredentials;

public class LoginRequest {
	@SerializedName("username")
	private String username;
	@SerializedName("password")
	private String password;

	public LoginRequest(String login, String password) {
		this.username = login;
		this.password = password;
	}

	public LoginRequest(UserCredentials user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}