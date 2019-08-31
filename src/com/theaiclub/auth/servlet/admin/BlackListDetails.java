package com.theaiclub.auth.servlet.admin;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.theaiclub.auth.AppUtils;
import com.theaiclub.auth.SecurityConfig;
import com.theaiclub.auth.UserAccount;
import com.theaiclub.db.Blacklist;

@WebServlet("/admin/blacklist")
public class BlackListDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BlackListDetails() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAccount account = AppUtils.getLoginedUser(request.getSession());
		if (account != null) {
			if (account.getRoles().contains(SecurityConfig.ROLE_ADMIN)) {
				RequestDispatcher dispatcher //
						= this.getServletContext().getRequestDispatcher(
								"/WEB-INF/views/admin-blacklist.jsp");

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

		UserAccount account = AppUtils.getLoginedUser(request.getSession());

		if (account != null) {
			if (account.getRoles().contains(SecurityConfig.ROLE_ADMIN)) {
				JSONArray blacklist = Blacklist.getBlacklist();
				response.getWriter().write(blacklist.toString());
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

}