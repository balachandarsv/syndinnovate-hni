package com.theaiclub.auth.servlet.admin;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.theaiclub.auth.AppUtils;
import com.theaiclub.auth.SecurityConfig;
import com.theaiclub.auth.UserAccount;
import com.theaiclub.db.Camera;

@WebServlet("/admin/addcamera")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
// 100 MB
public class AddCamera extends HttpServlet {

	private static final long serialVersionUID = 205242440643911308L;
	/**
	 * Directory where uploaded files will be saved, its relative to the web
	 * application directory.
	 */
	private static final String UPLOAD_DIR = "uploads";

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAccount account = AppUtils.getLoginedUser(request.getSession());
		if (account != null) {
			if (account.getRoles().contains(SecurityConfig.ROLE_ADMIN)) {
				RequestDispatcher dispatcher //
						= this.getServletContext().getRequestDispatcher(
								"/WEB-INF/views/admin-addcamera.jsp");

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

		String url = request.getParameter("url");

		String values[] = new String[1];
		values[0] = "'" + url + "'," + "'OFF'";
		Camera.insert(values);
		response.sendRedirect(
				request.getServletContext().getContextPath() + "/admin/home");

	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 */
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2,
						token.length() - 1);
			}
		}
		return "";
	}
}
