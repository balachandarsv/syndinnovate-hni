package com.theaiclub.face;

import org.opencv.core.Mat;

public class Box {

	private Mat face;
	private int x;
	private int y;
	private int width;
	private int height;
	private double conf;
	private String name;

	public Box(Mat face, int x, int y, int width, int height, double conf,
			String name) {
		this.face = face;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.conf = conf;
		this.name = name;
	}
	public Mat getFace() {
		return face;
	}
	public void setFace(Mat face) {
		this.face = face;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getConf() {
		return conf;
	}
	public void setConf(double conf) {
		this.conf = conf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
