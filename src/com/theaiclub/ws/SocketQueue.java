package com.theaiclub.ws;

import java.util.LinkedList;

import org.json.JSONObject;

public class SocketQueue {

	public static boolean isSocketConnected = false;

	public static LinkedList<JSONObject> message = new LinkedList<JSONObject>();

	public static void put(JSONObject frame) {
		message.addLast(frame);
	}
	public static boolean has() {
		return !message.isEmpty();
	}

	public static JSONObject get() {
		return message.pollFirst();
	}

}
