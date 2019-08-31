package com.theaiclub.face;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import com.theaiclub.db.Camera;
import com.theaiclub.db.Photo;
import com.theaiclub.ws.Notification;

public class VideoRecognition implements Runnable {

	private String url;
	private long id;
	private Net rec = Dnn.readNetFromModelOptimizer(
			System.getProperty("models.dir") + "/rec/face-recognition.xml",
			System.getProperty("models.dir") + "/rec/face-recognition.bin");
	private Net det = Dnn.readNetFromModelOptimizer(
			System.getProperty("models.dir") + "/det/face-detection.xml",
			System.getProperty("models.dir") + "/det/face-detection.bin");
	private Map<String, double[]> embedding;
	public VideoRecognition(String url, long id) {
		this.url = url;
		this.id = id;
		this.setEmbedding(new HashMap<>());
		initialize();
	}

	private void initialize() {
		JSONArray array = Photo.getPhoto();
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			String name = obj.getString(Photo.USER);
			double[] embed = new double[512];
			for (int j = 0; j < embed.length; j++) {
				embed[j] = obj.getDouble("emb" + (j + 1));
			}
			embedding.put(name, embed);
		}

	}

	@Override
	public void run() {
		VideoCapture cap = null;
		try {
			int u = Integer.parseInt(this.url);
			cap = new VideoCapture(u);
		} catch (Exception e) {
			cap = new VideoCapture(this.url);
		}

		VideoWriter videoWriter = new VideoWriter(
				System.getProperty("videos.dir") + "/" + id + ".mkv",
				VideoWriter.fourcc('H', '2', '6', '4'),
				cap.get(Videoio.CAP_PROP_FPS),
				new Size(cap.get(Videoio.CAP_PROP_FRAME_WIDTH),
						cap.get(Videoio.CAP_PROP_FRAME_HEIGHT)),
				true);

		Mat input = new Mat();

		FaceDetector detector = new FaceDetector(det);
		FaceEmbedder emb = new FaceEmbedder(rec);
		int counter = 0;
		Map<String, Long> nameCounter = new HashMap<String, Long>();
		List<String> skip = new ArrayList<String>();
		while (cap.isOpened()) {
			counter++;
			cap.read(input);
			if (!input.empty()) {
				List<Box> faces = detector.findFaces(input);
				if (!faces.isEmpty()) {

					for (Box face : faces) {
						double[] embed = emb.findEmbedding(face.getFace());
						String name = Face.findFace(getEmbedding(), embed);
						if (name != null) {
							if (nameCounter.containsKey(name)
									&& !skip.contains(name)) {
								long count = nameCounter.get(name);
								if (count > 50) {
									Notification.sendNotification(name);
									skip.add(name);
									nameCounter.remove(name);
								} else {
									nameCounter.put(name, count + 1);
								}
							} else {
								nameCounter.put(name, 1l);
							}
							Imgproc.rectangle(input,
									new Rect(face.getX(), face.getY(),
											face.getWidth(), face.getHeight()),
									new Scalar(255, 0, 0));
							int x = face.getX() - 20 < 0 ? 0 : face.getX() - 20;
							int y = face.getY() - 20 < 0 ? 0 : face.getY() - 20;
							Imgproc.putText(input, name, new Point(x, y),
									Imgproc.FONT_HERSHEY_SIMPLEX, 1,
									new Scalar(0, 0, 255));
						}
					}
				}
				// if (SocketQueue.isSocketConnected) {
				// SocketQueue.put(input);
				// }
				videoWriter.write(input);
			}
			if (counter % 500 == 0) {
				boolean alive = Camera.isAlive(this.id);
				if (!alive) {
					break;
				}
			}
			if (counter % 1800 == 0) {
				counter = 0;
				skip.clear();
			}
		}
		videoWriter.release();
		cap.release();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<String, double[]> getEmbedding() {
		return embedding;
	}

	public void setEmbedding(Map<String, double[]> embedding) {
		this.embedding = embedding;
	}

}