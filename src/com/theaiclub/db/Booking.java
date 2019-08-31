package com.theaiclub.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Booking {
	public static final String TABLENAME = "booking";
	public static final String ID = "id";
	public static final String USER = "username";
	public static final String TITLE = "title";
	public static final String DESC = "description";
	public static final String CATEGORY = "category";
	public static final String DATETIME = "datetime";

	public static JSONArray getBookings() {
		try {
			JSONObject result = DBUtils.select(TABLENAME, USER + "," + TITLE
					+ "," + DESC + "," + CATEGORY + "," + DATETIME, null, null,
					null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static JSONArray getBookings(String username) {
		try {
			JSONObject result = DBUtils
					.select(TABLENAME,
							USER + "," + TITLE + "," + DESC + "," + CATEGORY
									+ "," + DATETIME,
							USER, "'" + username + "'", null);
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
			return DBUtils.insert(TABLENAME, USER + "," + DATETIME + "," + TITLE
					+ "," + DESC + "," + CATEGORY, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
