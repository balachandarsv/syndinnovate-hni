package com.theaiclub.ws;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/streams")
public class Socket {

	public static List<Session> sessions = new ArrayList<>();

	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Opened " + session.getId());
		sessions.add(session);
	}
	@OnClose
	public void onClose(Session session) {
		System.out.println("Closed " + session.getId());
		sessions.remove(session);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(
				"Message  From=" + session.getId() + " Message=" + message);

	}

	@OnError
	public void onError(Throwable t) {
		System.out.println("Error " + t.getMessage());
	}
}