package com.theaiclub.auth.servlet.user;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.theaiclub.auth.DataDAO;
import com.theaiclub.auth.UserAccount;
import com.theaiclub.db.Photo;
import com.theaiclub.face.Face;

@WebServlet("/user/addkyc")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)
// 100 MB
public class AddUserKYC extends HttpServlet {

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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String applicationPath = request.getServletContext().getRealPath("");
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}

		System.out.println(
				"Upload File Directory=" + fileSaveDir.getAbsolutePath());

		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		UserAccount userAccount = DataDAO.findUser(userName, password);
		if (userAccount == null) {
			String errorMessage = "Invalid Password";

			request.setAttribute("errorMessage", errorMessage);

			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher(
							"/WEB-INF/views/user-kyc.jsp");

			dispatcher.forward(request, response);
			return;
		}

		String fileName = null;
		for (Part part : request.getParts()) {
			fileName = getFileName(part);
			fileName = fileName.replaceAll("[^a-zA-Z0-9.]", "");
			part.write(uploadFilePath + File.separator + userName + ".jpg");
		}

		double[] embed = Face.generateEmbedding(
				uploadFilePath + File.separator + userName + ".jpg");
		String values[] = new String[1];
		values[0] = "'" + userName + "','"
				+ Calendar.getInstance().getTime().toString()
				+ "','Bangalore','" + userName + ".jpg',";
		for (int i = 0; i < embed.length; i++) {
			values[0] += String.valueOf(embed[i]);
			if (i < 511) {
				values[0] += ",";
			}
		}
		Photo.insert(values);
		response.sendRedirect(
				request.getServletContext().getContextPath() + "/user/home");

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
