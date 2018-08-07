package com.bcm.dto;

public class CustomerLoginResponse {

	private int error = 1;
	private String token;
	private CustomerTO user;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public CustomerTO getUser() {
		return user;
	}

	public void setUser(CustomerTO user) {
		this.user = user;
	}
}