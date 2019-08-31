package com.theaiclub.auth;

import com.theaiclub.db.Users;

public class DataDAO {

	public static UserAccount findUser(String userName, String password) {
		UserAccount u = Users.checkUser(userName, password);
		return u;
	}

}