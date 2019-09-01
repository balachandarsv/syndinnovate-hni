package com.theaiclub.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.theaiclub.auth.UserAccount;

public class Users {
	public static final String TABLENAME = "users";
	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String MOBILE = "mobile";
	public static final String ROLE = "role";

	public static JSONArray getUsers() {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					USERNAME + "," + ROLE + "," + EMAIL + "," + MOBILE, null,
					null, null);

			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean insert(String[] values) {
		try {
			return DBUtils.insert(TABLENAME, USERNAME + "," + PASSWORD + ","
					+ EMAIL + "," + MOBILE + "," + ROLE, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static JSONArray getUsers(String wherecol, String whereVal) {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					USERNAME + "," + ROLE + "," + EMAIL + "," + MOBILE,
					wherecol, whereVal, null);

			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static UserAccount checkUser(String username, String password) {
		try {
			JSONObject result = DBUtils.select(TABLENAME, PASSWORD + "," + ROLE,
					USERNAME, "'" + username + "'", null);
			if (result.has("result")) {
				JSONArray array = result.getJSONArray("result");
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					String pass = obj.getString(PASSWORD);
					if (DBUtils.getMd5(password).equals(pass)) {
						UserAccount userAccount = new UserAccount(username,
								obj.getString(ROLE));
						return userAccount;
					}
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
