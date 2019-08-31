package com.theaiclub.face;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opencv.core.Mat;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;

import com.theaiclub.db.Photo;

public class Face {
	private static final Logger LOGGER = Logger.getLogger(Face.class.getName());
	public static final ExecutorService executor = Executors
			.newFixedThreadPool(10);
	private Face() {
	}
	private static Net rec = Dnn.readNetFromModelOptimizer(
			System.getProperty("models.dir") + "/rec/face-recognition.xml",
			System.getProperty("models.dir") + "/rec/face-recognition.bin");
	private static Net detector = Dnn.readNetFromModelOptimizer(
			System.getProperty("models.dir") + "/det/face-detection.xml",
			System.getProperty("models.dir") + "/det/face-detection.bin");
	public static double[] generateEmbedding(String path) {
		Mat input = Imgcodecs.imread(path);
		FaceDetector det = new FaceDetector(detector);
		FaceEmbedder emb = new FaceEmbedder(rec);
		List<Box> faces = det.findFaces(input);
		double[] embed = new double[512];
		for (Box box : faces) {
			embed = emb.findEmbedding(box.getFace());
		}
		return embed;
	}
	public static String findFace(Map<String, double[]> map, double b[]) {
		String name[] = new String[map.size()];
		float result[] = new float[map.size()];
		Iterator<String> it = map.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			String key = it.next();
			double[] a = map.get(key);
			double sum = 0.0d;
			double a2Sum = 0.0d;
			double b2Sum = 0.0d;
			for (int j = 0; j < b.length; j++) {
				sum = sum + (a[j] * b[j]);
				a2Sum = a2Sum + (a[j] * a[j]);
				b2Sum = b2Sum + (b[j] * b[j]);
			}
			a2Sum = Math.sqrt(a2Sum);
			b2Sum = Math.sqrt(b2Sum);
			result[i] = (float) (sum / (a2Sum * b2Sum));
			name[i] = key;
			i++;
		}
		if (result.length > 0) {
			float max = result[0];
			int index = 0;

			for (int l = 0; l < result.length; l++) {
				if (max < result[l]) {
					max = result[l];
					index = l;
				}
			}
			if (max > 0.4) {
				return name[index];
			}
		}
		return null;
	}

	public static String findFace(double[] embedding) {
		JSONArray array = Photo.getPhoto();
		String name[] = new String[array.length()];
		double result[] = new double[array.length()];
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);

			double[] embFromMap = new double[512];
			for (int j = 0; j < 512; j++) {
				String emb = "emb" + (j + 1);
				embFromMap[j] = object.getDouble(emb);
			}
			double sum = 0.0d;
			double a2Sum = 0.0d;
			double b2Sum = 0.0d;
			for (int j = 0; j < embedding.length; j++) {
				sum = sum + (embFromMap[j] * embedding[j]);
				a2Sum = a2Sum + (embFromMap[j] * embFromMap[j]);
				b2Sum = b2Sum + (embedding[j] * embedding[j]);
			}
			a2Sum = Math.sqrt(a2Sum);
			b2Sum = Math.sqrt(b2Sum);
			result[i] = (float) (sum / (a2Sum * b2Sum));
			name[i] = object.getString(Photo.USER);
		}
		double max = result[0];
		int index = 0;

		for (int l = 0; l < result.length; l++) {
			if (max < result[l]) {
				max = result[l];
				index = l;
			}
		}
		if (max > 0.4) {
			return name[index];
		}

		return null;
	}

}
