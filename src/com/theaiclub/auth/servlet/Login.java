package com.theaiclub.auth.servlet;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.theaiclub.auth.AppUtils;
import com.theaiclub.auth.DataDAO;
import com.theaiclub.auth.SecurityConfig;
import com.theaiclub.auth.UserAccount;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());
		if (userAccount != null) {
			if (userAccount.getRoles().get(0)
					.equals(SecurityConfig.ROLE_USER_NORMAL)) {
				response.sendRedirect(request.getContextPath() + "/user/home");
			} else if (AppUtils.getLoginedUser(request.getSession()).getRoles()
					.get(0).equals(SecurityConfig.ROLE_ADMIN)) {
				response.sendRedirect(request.getContextPath() + "/admin/home");
			}
		} else {
			RequestDispatcher dispatcher //
					= this.getServletContext()
							.getRequestDispatcher("/WEB-INF/views/index.jsp");

			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		UserAccount userAccount = DataDAO.findUser(userName, password);
		if (userAccount == null) {
			String errorMessage = "Invalid Username or Password";

			request.setAttribute("errorMessage", errorMessage);

			RequestDispatcher dispatcher //
					= this.getServletContext()
							.getRequestDispatcher("/WEB-INF/views/index.jsp");

			dispatcher.forward(request, response);
			return;
		}

		AppUtils.storeLoginedUser(request.getSession(), userAccount);

		//
		int redirectId = -1;
		try {
			redirectId = Integer.parseInt(request.getParameter("redirectId"));
		} catch (Exception e) {
		}
		String requestUri = AppUtils
				.getRedirectAfterLoginUrl(request.getSession(), redirectId);
		if (requestUri != null) {
			response.sendRedirect(requestUri);
		} else {
			if (userAccount.getRoles().get(0)
					.equals(SecurityConfig.ROLE_USER_NORMAL)) {
				response.sendRedirect(request.getContextPath() + "/user/home");
			} else if (AppUtils.getLoginedUser(request.getSession()).getRoles()
					.get(0).equals(SecurityConfig.ROLE_USER_HNI)) {
				response.sendRedirect(request.getContextPath() + "/user/home");
			} else if (AppUtils.getLoginedUser(request.getSession()).getRoles()
					.get(0).equals(SecurityConfig.ROLE_ADMIN)) {
				response.sendRedirect(request.getContextPath() + "/admin/home");
			}

		}

	}

}