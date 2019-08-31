package com.theaiclub.auth.servlet.admin;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.theaiclub.auth.AppUtils;
import com.theaiclub.auth.SecurityConfig;
import com.theaiclub.auth.UserAccount;
import com.theaiclub.db.Blacklist;
import com.theaiclub.db.Booking;
import com.theaiclub.db.Users;

@WebServlet("/admin/home")
public class AdminHome extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminHome() {
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
								"/WEB-INF/views/adminHome.jsp");

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
				JSONArray users = Users.getUsers(Users.ROLE,
						"'" + SecurityConfig.ROLE_USER_HNI + "'");
				JSONArray normal = Users.getUsers(Users.ROLE,
						"'" + SecurityConfig.ROLE_USER_NORMAL + "'");
				JSONArray bookings = Booking.getBookings();
				JSONArray blacklist = Blacklist.getBlacklist();
				JSONObject object = new JSONObject();
				object.put("hni", users.length());
				object.put("normal", normal.length());
				object.put("bookings", bookings.length());
				object.put("blacklists", blacklist.length());
				response.getWriter().write(object.toString());
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