package com.theaiclub.auth.servlet.user;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.theaiclub.auth.AppUtils;
import com.theaiclub.auth.SecurityConfig;
import com.theaiclub.auth.UserAccount;

@WebServlet("/user/kyc")
public class Kyc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Kyc() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAccount account = AppUtils.getLoginedUser(request.getSession());
		if (account != null) {
			if (account.getRoles().contains(SecurityConfig.ROLE_USER_NORMAL)
					|| account.getRoles()
							.contains(SecurityConfig.ROLE_USER_HNI)) {
				RequestDispatcher dispatcher //
						= this.getServletContext().getRequestDispatcher(
								"/WEB-INF/views/user-kyc.jsp");

				dispatcher.forward(request, response);
			} else {
				response.sendRedirect(
						request.getServletContext().getContextPath()
								+ "/login");
			}
		} else {
			response.sendRedirect(
					request.getServletContext().getContextPath() + "/login");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}