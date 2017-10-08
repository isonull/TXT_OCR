package imageWarp;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import vocaRecognition.ImagePreprocess;

public class CharWarp {
	private VocaWarp vocaWarp;
	private char charr;
	// base and len is relative to vocaWarp
	private int[] posInfo;
	private boolean[] data;

	public int getWidth() {
		return posInfo[2] - posInfo[0];
	}

	public int getHeidht() {
		return posInfo[3] - posInfo[1];
	}

	public char getChar() {
		return charr;
	}

	public int[] getPosInfo() {
		return posInfo;
	}

	public CharWarp(VocaWarp vocaWp, int[] rect) {
		vocaWarp = vocaWp;
		posInfo = rect;
	}

	public boolean[] generateData(int width, int height) throws Exception {
		boolean[][] binData = vocaWarp.imageWarp.getBinData();
		data = new boolean[width * height];
		Arrays.fill(data, false);
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				int Y = y + posInfo[1];
				int X = x + posInfo[0];
				if (X < posInfo[2] && Y < posInfo[3]) {
					data[y * width + x] = binData[Y][X];
				}
			}
		}
		return data;
	}

	public BufferedImage generateDataImage(int width, int height) {
		return ImagePreprocess.makeBinImage(data, width, height);
	}

	public String getSuggestedFileName() {
		return "" + posInfo[0] + "-" + posInfo[1];
	}

	public String getCSVDataRowString() {
		String dataRow = "";
		for (int i = 0; i < data.length - 1; ++i) {
			dataRow += (data[i] ? 1 : 0) + ",";
		}
		if (data.length != 0) {
			dataRow += data[data.length - 1] ? 1 : 0;
		}
		return dataRow;
	}

}
