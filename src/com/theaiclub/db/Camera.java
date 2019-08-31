package com.theaiclub.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Camera {
	public static final String TABLENAME = "camera";
	public static final String ID = "id";
	public static final String URL = "url";
	public static final String STATUS = "status";

	public static JSONArray getCameras() {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					ID + "," + URL + "," + STATUS, null, null, null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray getCameras(String whereCol, String whereVal) {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					ID + "," + URL + "," + STATUS, whereCol, whereVal, null);
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
			return DBUtils.insert(TABLENAME, URL + "," + STATUS, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void off(String val) {
		try {
			DBUtils.update(TABLENAME, STATUS, "'OFF'", ID, val);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	public static JSONArray on(String val) {
		try {
			DBUtils.update(TABLENAME, STATUS, "'ON'", ID, val);
			return getCameras(ID, val);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return new JSONArray();

	}

	public static boolean isAlive(long id) {
		JSONObject result;
		try {
			result = DBUtils.select(TABLENAME, STATUS, ID, "" + id, null);
			if (result.has("result")) {
				JSONArray res = result.getJSONArray("result");
				if (res.length() > 0) {
					return res.getJSONObject(0).getString(STATUS)
							.equalsIgnoreCase("ON");
				}

			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
}
