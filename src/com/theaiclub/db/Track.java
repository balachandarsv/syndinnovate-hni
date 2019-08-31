package com.theaiclub.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Track {
	public static final String TABLENAME = "track";
	public static final String ID = "id";
	public static final String USER = "username";
	public static final String DATETIME = "datetime";
	public static final String BRANCH = "branch";

	public static JSONArray getTrack() {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					USER + "," + DATETIME + "," + BRANCH, null, null, null);
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
			return DBUtils.insert(TABLENAME,
					USER + "," + DATETIME + "," + BRANCH, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
