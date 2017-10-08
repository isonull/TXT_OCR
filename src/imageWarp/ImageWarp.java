package imageWarp;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import vocaRecognition.ClusterDetection;
import vocaRecognition.ImagePreprocess;

public class ImageWarp {
	private BufferedImage image;
	private int[][] grayData;
	private boolean[][] binData;

	private int RGB_GRAY_AVERAGE_RANGE = 2;

	private int GRAY_BIN_DIVISION = 50;
	private int GRAY_BIN_ERASE_BASE = 80;
	private double GRAY_BIN_BOUNDARY_RATIO = 0.63;

	private int VOCA_DETECT_RANGE = 8;
	private boolean VOCA_DETECT_VAL = true;

	private ArrayList<VocaWarp> vocaWarpArr;

	public ImageWarp(BufferedImage img) {
		image = img;
	}

	public int[][] getGrayData() throws Exception {
		return grayData;
	}

	public void generateGrayData() throws Exception {
		grayData = ImagePreprocess.imageBGR_3BYTEtoGRAY_BYTE(image);
		grayData = ImagePreprocess.GrayLocalAverage(grayData, RGB_GRAY_AVERAGE_RANGE);
	}

	public boolean[][] getBinData() throws Exception {
		return binData;
	}

	public void generateBinData() throws Exception {
		binData = ImagePreprocess.grayThreshold(grayData, GRAY_BIN_DIVISION, GRAY_BIN_ERASE_BASE,
				GRAY_BIN_BOUNDARY_RATIO);
	}

	public ArrayList<VocaWarp> getVocaWarps() {
		return vocaWarpArr;
	}

	public void generateVocaPositionInfo() {
		ArrayList<int[]> rectsPos = ClusterDetection.detect(binData, VOCA_DETECT_VAL, VOCA_DETECT_RANGE);
		vocaWarpArr = new ArrayList<>(rectsPos.size());
		for (int[] rectPos : rectsPos) {
			vocaWarpArr.add(new VocaWarp(this, rectPos));
		}
	}

	public BufferedImage getGrayImage() {
		return ImagePreprocess.makeByteGrayImage(grayData, 0, grayData[0].length, 0, grayData.length);
	}

	public BufferedImage getBinImage() {
		return ImagePreprocess.makeBinImage(binData);
	}

}
