package com.theaiclub.ws;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.theaiclub.auth.Constants;
import com.theaiclub.auth.SecurityConfig;
import com.theaiclub.db.Track;
import com.theaiclub.db.Users;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class Notification {

	public static boolean sendNotification(String user) {
		List<Session> session = Socket.sessions;
		for (Session sess : session) {
			try {
				JSONObject json = new JSONObject();
				String value = "'" + user + "','"
						+ Calendar.getInstance().getTime().toString()
						+ "','Bangalore'";
				Track.insert(new String[]{value});
				sess.getBasicRemote().sendText(json.toString());
				JSONArray users = Users.getUsers(Users.ROLE,
						"'" + SecurityConfig.ROLE_ADMIN + "'");
				for (int i = 0; i < users.length(); i++) {
					JSONObject userObj = users.getJSONObject(i);
					String email = userObj.getString(Users.EMAIL);
					String mobile = userObj.getString(Users.MOBILE);
					sendWhatsApp(user, mobile);
					sendMail(user, email);
				}
				json.put("user", user);

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private static void sendWhatsApp(String user, String mobile) {
		Twilio.init(Constants.TWILIO_APP, Constants.TWILIO_AUTHTOKEN);
		if (mobile.contains("+")) {
			mobile = mobile.replace("+", "");
		}
		if (mobile.length() > 10) {
			mobile = mobile.substring(mobile.length() - 10, mobile.length());
		}
		try {

			Message message = Message.creator(
					new com.twilio.type.PhoneNumber("whatsapp:+91" + mobile),
					new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
					"A HNI with name '" + user
							+ "' has visited your branch. Kindly make the necessary arrangements.")
					.create();
			System.out.println(message.getStatus().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void sendMail(String user, String email) {
		Email from = new Email(Constants.FROM_EMAIL);
		String subject = "HNI visited your branch";
		Email to = new Email(email);
		Content content = new Content("text/plain", "A HNI with name '" + user
				+ "' has visited your branch. Kindly make the necessary arrangements.");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(Constants.SENDGRID);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
