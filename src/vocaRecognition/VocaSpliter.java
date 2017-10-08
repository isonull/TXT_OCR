package vocaRecognition;

import java.util.ArrayList;
import java.util.List;

public class VocaSpliter {

	public static int[][] split(boolean[][] image, int[][] vocaRects) {
		int[][] splitPositions = new int[vocaRects.length][];
		for (int[] rect : vocaRects) {

		}
		return splitPositions;
	}

	private static int boundYieldRatio = 2;

	public static List<int[]> naiveCharDetection(boolean[][] image, int[] rect, int minWidth, int maxWidth) {
		int[] proj = getVerticalProjection(image, rect);
		int boundary = Integer.MAX_VALUE;
		List<Integer> splitPos = new ArrayList<>();
		for (int i : proj) {
			boundary = i < boundary ? i : boundary;
		}

		// boundary *= boundYieldRatio;

		int prevPos = 0;
		splitPos.add(prevPos);
		for (int i = 0; i < proj.length; ++i) {
			if (proj[i] <= boundary && i - prevPos >= minWidth) {
				splitPos.add(i);
				prevPos = i;
			}
		}

		return splitPos;

	}

	public static List<int[]> naiveCharDetectionByCluster(boolean[][] image, boolean detectVal, int[] rect, int range) {
		return ClusterDetection.detect(image, detectVal, range, rect[0], rect[2], rect[1], rect[3]);
	}

	public static int[] getVerticalProjection(boolean[][] image, int[] rect) {
		int[] proj = new int[rect[2]];
		for (int x = rect[0]; x < rect[0] + rect[2]; x++) {
			proj[x - rect[0]] = 0;
			for (int y = rect[1]; y < rect[1] + rect[3]; y++) {
				if (image[y][x]) {
					proj[x - rect[0]]++;
				}
			}
		}
		return proj;
	}

}
