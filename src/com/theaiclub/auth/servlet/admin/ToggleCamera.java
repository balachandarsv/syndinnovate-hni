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
import com.theaiclub.db.Camera;
import com.theaiclub.face.Face;
import com.theaiclub.face.VideoRecognition;

@WebServlet("/admin/tcamera")
public class ToggleCamera extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ToggleCamera() {
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
								"/WEB-INF/views/admin-camera.jsp");

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
		String id = request.getParameter("id");
		id = id.replaceAll("s", "");
		String flag = request.getParameter("val");
		if (flag.equalsIgnoreCase("on")) {
			JSONArray array = Camera.on(id);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				VideoRecognition rec = new VideoRecognition(
						obj.getString(Camera.URL), Long.parseLong(id));
				Face.executor.execute(rec);
			}

		} else {
			Camera.off(id);
		}
	}

}