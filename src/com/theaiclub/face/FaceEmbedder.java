package com.theaiclub.face;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;

public class FaceEmbedder {

	private static final Logger LOGGER = Logger
			.getLogger(FaceEmbedder.class.getName());
	private Net net;

	public FaceEmbedder(Net net) {
		this.net = net;
	}

	public double[] findEmbedding(Mat input) {
		long time = System.currentTimeMillis();
		Imgproc.resize(input, input, new Size(160, 160));
		Mat imageblob = Dnn.blobFromImage(input);

		net.setInput(imageblob);

		Mat output = net.forward();
		double[] embedding = new double[512];
		for (int i = 0; i < output.rows(); i++) {
			for (int j = 0; j < output.cols(); j++) {
				embedding[j] = (output.get(i, j)[0]);
			}
		}
		long time2 = System.currentTimeMillis();
		LOGGER.log(Level.INFO,
				"Time for generating embedding : " + (time2 - time));
		return embedding;
	}

}
