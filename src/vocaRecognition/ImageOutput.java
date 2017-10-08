package vocaRecognition;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageOutput {
	public static BufferedImage addRectangles(BufferedImage image, ArrayList<int[]> rects, Color color) {
		Graphics gra = image.getGraphics();
		for (int[] rect : rects) {
			gra.setColor(color);
			gra.drawRect(rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
		}

		image.flush();
		return image;
	}

	public static int[] makeGrayDataFromProb(double[] probs) {
		int[] data = new int[probs.length];
		int c = 0;
		for (double p : probs) {
			data[c++] = (int) (p * 255);
		}
		return data;
	}
}
