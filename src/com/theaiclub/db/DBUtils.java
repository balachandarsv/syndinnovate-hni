package com.theaiclub.db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.theaiclub.auth.Constants;
import com.theaiclub.auth.SecurityConfig;

public class DBUtils {

	static {
		try {
			initialize();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection()
			throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		String URL = "jdbc:derby:synInnovate;create=true";
		Connection conn = DriverManager.getConnection(URL);
		return conn;
	}

	public static boolean initialize()
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		Set<String> set = getDBTables(conn);
		if (!set.contains(Users.TABLENAME)) {
			String query = "CREATE TABLE " + Users.TABLENAME + "( " + Users.ID
					+ " INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
					+ Users.USERNAME + " VARCHAR(255), " + Users.PASSWORD
					+ " VARCHAR(255), " + Users.ROLE + " VARCHAR(255), "
					+ Users.EMAIL + " VARCHAR(255), " + Users.MOBILE
					+ " VARCHAR(255), " + "PRIMARY KEY (" + Users.ID + "))";
			stmt.execute(query);
			query = "INSERT INTO " + Users.TABLENAME + "(" + Users.USERNAME
					+ "," + Users.PASSWORD + "," + Users.ROLE + ","
					+ Users.EMAIL + "," + Users.MOBILE + ") VALUES "
					+ "('balachandar', 'ee11cbb19052e40b07aac0ca060c23ee', '"
					+ SecurityConfig.ROLE_USER_HNI + "','"
					+ Constants.FROM_EMAIL + "','" + Constants.ADMIN_MOBILE
					+ "'), "
					+ "('sujatha', 'ee11cbb19052e40b07aac0ca060c23ee', '"
					+ SecurityConfig.ROLE_USER_HNI + "','"
					+ Constants.FROM_EMAIL + "','" + Constants.ADMIN_MOBILE
					+ "'), " + "('userY', 'ee11cbb19052e40b07aac0ca060c23ee', '"
					+ SecurityConfig.ROLE_USER_NORMAL + "','"
					+ Constants.FROM_EMAIL + "','" + Constants.ADMIN_MOBILE
					+ "'), " + "('userX', 'ee11cbb19052e40b07aac0ca060c23ee', '"
					+ SecurityConfig.ROLE_USER_NORMAL + "','"
					+ Constants.FROM_EMAIL + "','" + Constants.ADMIN_MOBILE
					+ "'), " + "('admin', '21232f297a57a5a743894a0e4a801fc3', '"
					+ SecurityConfig.ROLE_ADMIN + "','" + Constants.FROM_EMAIL
					+ "','" + Constants.ADMIN_MOBILE + "') ";
			stmt.execute(query);
		}
		if (!set.contains(Booking.TABLENAME)) {
			String query = "CREATE TABLE " + Booking.TABLENAME + "( "
					+ Booking.ID
					+ " INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
					+ Booking.USER + " VARCHAR(255), " + Booking.TITLE
					+ " VARCHAR(255), " + Booking.DESC + " VARCHAR(255), "
					+ Booking.DATETIME + " VARCHAR(255), " + Booking.CATEGORY
					+ " VARCHAR(255), " + "PRIMARY KEY (" + Booking.ID + "))";
			stmt.execute(query);
		}
		if (!set.contains(Track.TABLENAME)) {
			String query = "CREATE TABLE " + Track.TABLENAME + "( " + Track.ID
					+ " INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
					+ Track.USER + " VARCHAR(255), " + Track.DATETIME
					+ " VARCHAR(255), " + Track.BRANCH + " VARCHAR(255), "
					+ "PRIMARY KEY (" + Track.ID + "))";
			stmt.execute(query);
		}
		if (!set.contains(Blacklist.TABLENAME)) {
			String query = "CREATE TABLE " + Blacklist.TABLENAME + "( "
					+ Blacklist.ID
					+ " INT NOT NULL GENERATED ALWAYS AS IDENTITY, ";
			String split[] = Blacklist.EMBEDDING.split(",");
			for (int i = 0; i < split.length; i++) {
				query += split[i] + " DOUBLE, ";
			}
			query += Blacklist.USER + " VARCHAR(255), " + Blacklist.DATETIME
					+ " VARCHAR(255), " + Blacklist.BRANCH + " VARCHAR(255), "
					+ Blacklist.LOCATION + " VARCHAR(255), " + "PRIMARY KEY ("
					+ Blacklist.ID + "))";
			stmt.execute(query);
		}

		if (!set.contains(Camera.TABLENAME)) {
			String query = "CREATE TABLE " + Camera.TABLENAME + "( " + Track.ID
					+ " INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
					+ Camera.URL + " VARCHAR(255), " + Camera.STATUS
					+ " VARCHAR(255), " + "PRIMARY KEY (" + Track.ID + "))";
			stmt.execute(query);
		}

		if (!set.contains(Photo.TABLENAME)) {
			String query = "CREATE TABLE " + Photo.TABLENAME + "( " + Photo.ID
					+ " INT NOT NULL GENERATED ALWAYS AS IDENTITY, ";
			String split[] = Photo.EMBEDDING.split(",");
			for (int i = 0; i < split.length; i++) {
				query += split[i] + " DOUBLE, ";
			}
			query += Photo.USER + " VARCHAR(255), " + Photo.DATETIME
					+ " VARCHAR(255), " + Photo.BRANCH + " VARCHAR(255), "
					+ Photo.LOCATION + " VARCHAR(255), " + "PRIMARY KEY ("
					+ Photo.ID + "))";
			stmt.execute(query);
		}
		return true;
	}

	public static boolean drop() throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		Set<String> tables = getDBTables(conn);
		for (String string : tables) {
			String query = "DROP TABLE " + string;
			stmt.execute(query);
		}
		return true;
	}

	public static boolean insert(String tablename, String columns,
			String[] values) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "INSERT INTO " + tablename + "(" + columns + ") VALUES ";
		for (int i = 0; i < values.length; i++) {
			query += "(" + values[i] + ")";
			if (i < values.length - 1) {
				query += ",";
			}
		}
		stmt.execute(query);
		return true;
	}

	public static JSONObject select(String tablename, String columns,
			String whereCol, String whereVal, String groupBY)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT " + columns + " FROM " + tablename;
		if (whereCol != null) {
			query += " WHERE " + whereCol + " = " + whereVal;
		}
		ResultSet rs = stmt.executeQuery(query);
		JSONObject jsonresult = new JSONObject();
		JSONArray result = new JSONArray();
		String col[] = columns.split("[,]");
		int count = col.length;
		while (rs.next()) {

			JSONObject json = new JSONObject();
			for (int i = 0; i < col.length; i++) {
				json.put(col[i], rs.getString(col[i]));
			}
			result.put(json);
		}
		jsonresult.put("tablename", tablename);
		jsonresult.put("result", result);
		return jsonresult;
	}

	public static boolean update(String tablename, String columns,
			String values, String whereCol, String whereVal)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "UPDATE  " + tablename + " SET " + columns + " = "
				+ values;
		if (whereCol != null) {
			query += " WHERE " + whereCol + " = " + whereVal;
		}

		if (stmt.executeUpdate(query) <= 0) {
			return false;
		}
		return true;

	}

	public static boolean delete(String tablename, String whereCol,
			String whereVal) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "DELETE FROM  " + tablename;
		if (whereCol != null) {
			query += " WHERE " + whereCol + " = " + whereVal;
		}
		if (stmt.executeUpdate(query) <= 0) {
			return false;
		}
		return true;
	}

	public static String getMd5(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static Set<String> getDBTables(Connection targetDBConn)
			throws SQLException {
		Set<String> set = new HashSet<String>();
		DatabaseMetaData dbmeta = targetDBConn.getMetaData();
		readDBTable(set, dbmeta, "TABLE", null);
		readDBTable(set, dbmeta, "VIEW", null);
		return set;
	}

	private static void readDBTable(Set<String> set, DatabaseMetaData dbmeta,
			String searchCriteria, String schema) throws SQLException {
		ResultSet rs = dbmeta.getTables(null, schema, null,
				new String[]{searchCriteria});
		while (rs.next()) {
			set.add(rs.getString("TABLE_NAME").toLowerCase());
		}
	}
}
