package vocaRecognition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ClusterDetection {

	public static ArrayList<int[]> detect(boolean[][] image, boolean detectVal, int range, int xBase, int xLimit,
			int yBase, int yLimit) {
		// returns a set of block, {(X,Y,Xlim,Ylim)}, which contains a char
		int imageHeight = image.length;
		int imageWidth = image[0].length;
		boolean[][] detected = new boolean[imageHeight][imageWidth];
		ArrayList<int[]> rects = new ArrayList<>();
		for (int y = yBase; y < yLimit; ++y) {
			for (int x = xBase; x < xLimit; ++x) {
				if (!detected[y][x] && image[y][x] == detectVal) {
					ArrayList<int[]> coordinators = formCluster(image, x, y, range);
					// use Gaussian distribution to further improve this for
					// slant case
					int maxX = 0;
					int maxY = 0;
					int minX = imageWidth;
					int minY = imageHeight;
					for (int[] XY : coordinators) {
						int X = XY[0];
						int Y = XY[1];
						maxX = X > maxX ? X : maxX;
						maxY = Y > maxY ? Y : maxY;
						minX = X < minX ? X : minX;
						minY = Y < minY ? Y : minY;

					}

					rects.add(new int[] { minX, minY, maxX + 1, maxY + 1 });
					for (int y1 = minY; y1 <= maxY; ++y1) {
						for (int x1 = minX; x1 <= maxX; ++x1) {
							detected[y1][x1] = true;
						}
					}
				}
			}
		}

		return rects;
	}

	public static ArrayList<int[]> detect(boolean[][] image, boolean detectVal, int range) {
		return detect(image, detectVal, range, 0, image[0].length, 0, image.length);
	}

	private static ArrayList<int[]> formCluster(boolean[][] image, int x, int y, int range) {
		// return a set of points {(x,y)} in a cluster;
		int imageHeight = image.length;
		int imageWidth = image[0].length;
		boolean clusterVal = image[y][x];
		int[][] searchVectors = generateSearchVector(range);

		Queue<int[]> processing = new LinkedList<int[]>();
		ArrayList<int[]> cluster = new ArrayList<int[]>();
		boolean[][] seen = new boolean[imageHeight][imageWidth];

		processing.offer(new int[] { x, y });
		seen[y][x] = true;

		while (!processing.isEmpty()) {
			int[] targetXY = processing.peek();
			int[] checkXY;
			int checkX, checkY;
			for (int[] searchVector : searchVectors) {
				checkXY = addIntArray(targetXY, searchVector, imageWidth, imageHeight);
				if (checkXY != null) {
					checkX = checkXY[0];
					checkY = checkXY[1];
					// a seen pixel will not be checked again
					if (!seen[checkY][checkX] && image[checkY][checkX] == clusterVal) {
						processing.offer(checkXY);
					}
					seen[checkY][checkX] = true;
				}
			}
			cluster.add(targetXY);
			processing.poll();
		}

		return cluster;
	}

	public static int[][] generateSearchVector(int range) {
		int len = range * 2 + 1;
		int size = len * len - 1;
		int[][] searchVector = new int[size][2];
		int c = 0;
		for (int i = -range; i <= range; ++i) {
			for (int j = -range; j <= range; ++j) {
				if (i != 0 || j != 0) {
					searchVector[c++] = new int[] { i, j };
				}
			}
		}

		return searchVector;
	}

	public static int[] addIntArray(int[] xy1, int[] xy2, int xLim, int yLim) {

		int x = xy1[0] + xy2[0];
		int y = xy1[1] + xy2[1];
		if (x < 0 || y < 0 || x >= xLim || y >= yLim) {
			return null;
		}
		return new int[] { x, y };
	}

}
