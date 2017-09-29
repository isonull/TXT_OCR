package imageWarp;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import vocaRecognition.ImagePreprocess;
import vocaRecognition.VocaDetection;

public class ImageWarp {
	private BufferedImage image;
	private int[][] grayData;
	private boolean[][] binData;

	private int RGB_GRAY_AVERAGE_RANGE = 2;

	private int GRAY_BIN_DIVISION = 50;
	private int GRAY_BIN_ERASE_BASE = 64;
	private double GRAY_BIN_BOUNDARY_RATIO = 0.63;

	private int VOCA_DETECT_RANGE = 5;
	private boolean VOCA_DETECT_VAL = true;

	private ArrayList<VocaWarp> vocaWarp;

	public ImageWarp(BufferedImage img) {
		image = img;
	}

	public int[][] getGrayData() throws Exception {
		if (grayData == null) {
			ImagePreprocess.GrayLocalAverage(grayData, RGB_GRAY_AVERAGE_RANGE);
		}
		return grayData;
	}

	public boolean[][] getBinData() throws Exception {
		if (binData == null) {
			ImagePreprocess.grayThreshold(getGrayData(), GRAY_BIN_DIVISION, GRAY_BIN_ERASE_BASE,
					GRAY_BIN_BOUNDARY_RATIO);
		}
		return binData;
	}

	public ArrayList<VocaWarp> getVocaInfo() {
		ArrayList<int[]> rects = VocaDetection.detect(binData, VOCA_DETECT_VAL, VOCA_DETECT_RANGE);

		for (int[] rect : rects) {

		}

		return vocaWarp;
	}

}
