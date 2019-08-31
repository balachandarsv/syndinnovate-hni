package com.theaiclub.ws;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.websocket.Session;

import org.json.JSONObject;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.theaiclub.auth.Constants;
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
				sendWhatsApp(user);
				sendMail(user);
				json.put("user", user);
				sess.getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private static void sendWhatsApp(String user) {
		Twilio.init(Constants.TWILIO_APP, Constants.TWILIO_AUTHTOKEN);
		// Need to change the mobile number to admin mobile number, leaving it
		// hard coded for now
		Message message = Message.creator(
				new com.twilio.type.PhoneNumber(
						"whatsapp:+91" + Constants.ADMIN_MOBILE),
				new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
				"A HNI with name '" + user
						+ "' has visited your branch. Kindly make the necessary arrangements.")
				.create();

	}

	private static void sendMail(String user) {
		Email from = new Email(Constants.FROM_EMAIL);
		String subject = "HNI visited your branch";
		String email = Users.getUsers(Users.USERNAME, "'" + user + "'")
				.getJSONObject(0).getString(Users.EMAIL);
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
