package com.theaiclub.auth;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {

	private String userName;

	private List<String> roles;

	public UserAccount() {

	}

	public UserAccount(String userName, String... roles) {
		this.userName = userName;

		this.roles = new ArrayList<String>();
		if (roles != null) {
			for (String r : roles) {
				this.roles.add(r);
			}
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}