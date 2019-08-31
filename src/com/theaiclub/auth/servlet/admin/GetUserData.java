package com.theaiclub.auth.servlet.admin;
import java.io.File;
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
import com.theaiclub.db.Booking;
import com.theaiclub.db.Users;

@WebServlet("/admin/userdata")
public class GetUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetUserData() {
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
								"/WEB-INF/views/adminUserData.jsp");

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
				String user = request.getParameter("user");
				JSONArray array = Users.getUsers(Users.USERNAME,
						"'" + user + "'");
				JSONObject result = new JSONObject();
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					obj.remove(Users.PASSWORD);
					String username = obj.getString(Users.USERNAME);
					File f = new File(
							request.getServletContext().getRealPath("")
									+ "/uploads/" + username + ".jpg");
					if (f.exists()) {
						obj.put("photo", username + ".jpg");
						result.put("user", obj);
						JSONArray bookings = Booking.getBookings(user);
						result.put("requests", bookings);
					}

				}
				response.getWriter().write(result.toString());
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