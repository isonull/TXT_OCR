package vocaRecognition;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.rosuda.JRI.Rengine;

public class ImagePreprocess {
	// FURTHER IMPROVEMENT:
	// local average
	// use line detection and merge two result

	public static int pixelBGR_3BYTEtoGRAY_BYTE(int BGR) {
		int R = (BGR & 0x00FF0000) >>> 16;
		int G = (BGR & 0x0000FF00) >>> 8;
		int B = (BGR & 0x000000FF);
		return (R * 30 + G * 59 + B * 11 + 50) / 100;
	}

	public static int[][] imageBGR_3BYTEtoGRAY_BYTE(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
		int[][] grayPixels = new int[height][width];

		int c = 0;
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				grayPixels[y][x] = pixelBGR_3BYTEtoGRAY_BYTE(pixels[c++]);
			}
		}
		return grayPixels;
	}

	public static int[][] GrayLocalAverage(int[][] grayData, int range) throws Exception {
		return Array2DMethod.localAverage(grayData, Array2DMethod.generateWeight(1));
	}

	public static boolean[][] grayThreshold(int[][] grayPixel, int div, int eraseBase, double bdRatio) {

		int height = grayPixel.length;
		int width = grayPixel[0].length;
		int step = width / div;
		boolean[][] binPixel = new boolean[height][width];
		for (int yBase = 0; yBase <= height - step; yBase += step) {
			int yLimit = (yBase + 2 * step <= height) ? yBase + step : height;
			for (int xBase = 0; xBase <= width - step; xBase += step) {
				int xLimit = (xBase + 2 * step <= width) ? xBase + step : width;

				int min = 255;
				int max = 0;
				int G;
				for (int y = yBase; y < yLimit; ++y) {
					for (int x = xBase; x < xLimit; ++x) {
						G = grayPixel[y][x];
						min = G < min ? G : min;
						max = G > max ? G : max;
					}
				}

				int dif = max - min;
				if (dif < eraseBase) {
					for (int y = yBase; y < yLimit; ++y) {
						for (int x = xBase; x < xLimit; ++x) {
							binPixel[y][x] = false;
						}
					}
				} else {
					int boundary = min + (int) (dif * bdRatio);
					for (int y = yBase; y < yLimit; ++y) {
						for (int x = xBase; x < xLimit; ++x) {
							binPixel[y][x] = grayPixel[y][x] < boundary ? true : false;
						}
					}
				}

			}
		}
		return binPixel;
	}

	public static boolean[][] thresholdTest(int[][] grayPixel, int div, int eraseBase, double bdRatio, Rengine rengine)
			throws IOException {
		rengine.eval("source(\"/Users/zijunyan/Desktop/JAVAWORKDIR/ORC/Rsrc/ImagePreprocess.R\")");
		int height = grayPixel.length;
		int width = grayPixel[0].length;
		int step = width / div;
		boolean[][] binPixel = new boolean[height][width];
		for (int yBase = 0; yBase <= height - step; yBase += step) {
			int yLimit = (yBase + 2 * step <= height) ? yBase + step : height;
			for (int xBase = 0; xBase <= width - step; xBase += step) {
				int xLimit = (xBase + 2 * step <= width) ? xBase + step : width;

				int[] cPixels = new int[(xLimit - xBase) * (yLimit - yBase)];

				int counter_cPixels = 0;
				int min = 255;
				int max = 0;
				int G;
				for (int y = yBase; y < yLimit; ++y) {
					for (int x = xBase; x < xLimit; ++x) {
						G = grayPixel[y][x];
						cPixels[counter_cPixels++] = grayPixel[y][x];
						min = G < min ? G : min;
						max = G > max ? G : max;
					}
				}

				rengine.assign("a", cPixels);
				String filePath = "/Users/zijunyan/Desktop/test/dist/" + xBase + "&" + yBase;
				rengine.assign("b", filePath);
				rengine.eval("showDist(a,b)");
				BufferedImage img = makeByteGrayImage(grayPixel, xBase, xLimit, yBase, yLimit);
				ImageIO.write(img, "PNG", new File(filePath + "d"));

				int dif = max - min;
				if (dif < eraseBase) {
					for (int y = yBase; y < yLimit; ++y) {
						for (int x = xBase; x < xLimit; ++x) {
							binPixel[y][x] = false;
						}
					}
				} else {
					int boundary = min + (int) (dif * bdRatio);
					for (int y = yBase; y < yLimit; ++y) {
						for (int x = xBase; x < xLimit; ++x) {
							binPixel[y][x] = grayPixel[y][x] < boundary ? true : false;
						}
					}
				}

			}
		}
		return binPixel;
	}

	public static BufferedImage makeBinImage(boolean[][] binData) {
		int height = binData.length;
		int width = binData[0].length;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		WritableRaster raster = image.getRaster();

		byte[] data = new byte[height * width];

		int c = 0;
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				data[c++] = (byte) (binData[y][x] ? 0 : 0x000000FF);
			}
		}

		raster.setDataElements(0, 0, width, height, data);

		return image;
	}

	public static BufferedImage makeByteGrayImage(int[][] grayData, int xBase, int xLimit, int yBase, int yLimit) {

		int height = yLimit - yBase;
		int width = xLimit - xBase;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = image.getRaster();

		byte[] data = new byte[height * width];

		int c = 0;
		for (int y = yBase; y < yLimit; ++y) {
			for (int x = xBase; x < xLimit; ++x) {
				data[c++] = (byte) grayData[y][x];
			}
		}

		raster.setDataElements(0, 0, width, height, data);

		return image;
	}

}
