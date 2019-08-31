package com.theaiclub.face;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;

public class FaceDetector {

	private static final Logger LOGGER = Logger
			.getLogger(FaceDetector.class.getName());
	private Net net;
	public FaceDetector(Net net) {
		this.net = net;
	}

	public List<Box> findFaces(Mat input) {
		long time = System.currentTimeMillis();
		List<Box> faces = new ArrayList<>();
		Mat imageblob = Dnn.blobFromImage(input, 1.0, new Size(300, 300),
				new Scalar(1, 1, 1), false, false);
		net.setInput(imageblob);
		Mat result = net.forward();
		if (result != null) {
			result = result.reshape(1, (int) result.total() / 7);
			for (int i = 0; i < result.rows(); i++) {
				double confidence = result.get(i, 2)[0];
				if (confidence > 0.5f) {
					// int classId = (int) result.get(i, 1)[0];
					int left = (int) (result.get(i, 3)[0] * input.cols());
					int top = (int) (result.get(i, 4)[0] * input.rows());
					int right = (int) (result.get(i, 5)[0] * input.cols());
					int bottom = (int) (result.get(i, 6)[0] * input.rows());
					left = left - (int) ((right - left) * 0.1);
					right = right + (int) ((right - left) * 0.1);
					top = top - (int) ((bottom - top) * 0.1);
					left = left < 0 ? 0 : left;
					top = top < 0 ? 0 : top;
					right = right > input.width() ? input.width() : right;
					bottom = bottom > input.height() ? input.height() : bottom;
					Rect rect = new Rect(new Point(left, top),
							new Point(right, bottom));
					Mat crop = new Mat(input, rect);
					faces.add(new Box(crop, rect.x, rect.y, rect.width,
							rect.height, confidence, ""));
				}
			}
		}
		long time2 = System.currentTimeMillis();
		LOGGER.log(Level.INFO, "Time for detection : " + (time2 - time));
		return faces;
	}

}
