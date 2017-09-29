package vocaRecognition;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageOutput {
	public static BufferedImage addRectangles(BufferedImage image, ArrayList<int[]> rects) {
		Graphics gra = image.getGraphics();
		for (int[] rect : rects) {
			gra.setColor(Color.RED);
			gra.drawRect(rect[0], rect[1], rect[2], rect[3]);
		}

		image.flush();
		return image;
	}
}
