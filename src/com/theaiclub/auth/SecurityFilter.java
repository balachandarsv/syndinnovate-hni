package com.theaiclub.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencv.core.Core;

import com.theaiclub.db.DBUtils;

@WebFilter(urlPatterns = {"/*"}, initParams = {
		@WebInitParam(name = "excludedExt", value = "jpeg jpg png pdf js css")})
public class SecurityFilter implements Filter {

	private static Set<String> excluded;

	public SecurityFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String servletPath = request.getServletPath();

		// User information stored in the Session.
		// (After successful login).
		UserAccount loginedUser = AppUtils.getLoginedUser(request.getSession());

		if (isExcluded(request)) {
			chain.doFilter(request, response);
			return;
		}
		if (servletPath.equals("/login")) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest wrapRequest = request;
		if (loginedUser != null) {
			// User Name
			String userName = loginedUser.getUserName();

			// Roles
			List<String> roles = loginedUser.getRoles();

			// Wrap old request by a new Request with userName and Roles
			// information.
			wrapRequest = new UserRoleRequestWrapper(userName, roles, request);
		}
		// Pages must be signed in.
		if (SecurityUtils.isSecurityPage(request)) {

			// If the user is not logged in,
			// Redirect to the login page.
			if (loginedUser == null) {

				String requestUri = request.getRequestURI();

				// Store the current page to redirect to after successful login.
				int redirectId = AppUtils.storeRedirectAfterLoginUrl(
						request.getSession(), requestUri);

				response.sendRedirect(wrapRequest.getContextPath()
						+ "/login?redirectId=" + redirectId);
				return;
			}

			// Check if the user has a valid role?
			boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
			if (!hasPermission) {

				RequestDispatcher dispatcher //
						= request.getServletContext().getRequestDispatcher(
								"/WEB-INF/views/accessDeniedView.jsp");

				dispatcher.forward(request, response);
				return;
			}
		}

		chain.doFilter(wrapRequest, response);
	}

	private boolean isExcluded(HttpServletRequest request) {
		String path = request.getRequestURI();
		if (path.endsWith("idea") || path.endsWith("team")) {
			return true;
		}
		String extension = path
				.substring(path.lastIndexOf('.') + 1, path.length())
				.toLowerCase();
		return excluded.contains(extension);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.setProperty("models.dir",
				fConfig.getServletContext().getRealPath("WEB-INF/models"));
		System.setProperty("videos.dir",
				fConfig.getServletContext().getRealPath("videos"));
		LogManager.getLogManager().reset();
		try {
			// DBUtils.drop();
			DBUtils.initialize();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		String excludedString = fConfig.getInitParameter("excludedExt");
		if (excludedString != null) {
			excluded = Collections.unmodifiableSet(
					new HashSet<>(Arrays.asList(excludedString.split(" ", 0))));
		} else {
			excluded = Collections.<String>emptySet();
		}
	}

}