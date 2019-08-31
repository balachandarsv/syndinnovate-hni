package com.theaiclub.auth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SecurityConfig {

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_USER_NORMAL = "NORMAL_USER";
	public static final String ROLE_USER_HNI = "HNI_USER";

	private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

	static {
		init();
	}

	private static void init() {
		List<String> urlPatterns1 = new ArrayList<String>();

		urlPatterns1.add("/user/");
		urlPatterns1.add("/assets/");

		mapConfig.put(ROLE_USER_NORMAL, urlPatterns1);

		mapConfig.put(ROLE_USER_HNI, urlPatterns1);

		List<String> urlPatterns2 = new ArrayList<String>();

		urlPatterns2.add("/admin");
		urlPatterns2.add("/assets/");

		mapConfig.put(ROLE_ADMIN, urlPatterns2);
	}

	public static Set<String> getAllAppRoles() {
		return mapConfig.keySet();
	}

	public static List<String> getUrlPatternsForRole(String role) {
		return mapConfig.get(role);
	}

}