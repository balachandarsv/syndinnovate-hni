package com.theaiclub.auth;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {

	// Check whether this 'request' is required to login or not.
	public static boolean isSecurityPage(HttpServletRequest request) {
		String urlPattern = UrlPatternUtils.getUrlPattern(request);

		Set<String> roles = SecurityConfig.getAllAppRoles();

		for (String role : roles) {
			List<String> urlPatterns = SecurityConfig
					.getUrlPatternsForRole(role);
			if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}

	// Check if this 'request' has a 'valid role'?
	public static boolean hasPermission(HttpServletRequest request) {
		String urlPattern = UrlPatternUtils.getUrlPattern(request);

		Set<String> allRoles = SecurityConfig.getAllAppRoles();

		for (String role : allRoles) {
			if (!request.isUserInRole(role)) {
				continue;
			}
			List<String> urlPatterns = SecurityConfig
					.getUrlPatternsForRole(role);
			if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}
}