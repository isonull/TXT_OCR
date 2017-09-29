package vocaRecognition;

import java.util.ArrayList;
import java.util.List;

public class VocaSpliter {

	// public static int[][] split(boolean[][] image, int[][] vocaRects) {
	// int[][] splitPositions = new int[vocaRects.length][];
	// for (int[] rect : vocaRects) {
	//
	// }
	// return splitPositions;
	// }

	private static int boundYieldRatio = 2;

	public static List<Integer> NaiveHorizontalSplit(boolean[][] image, int[] rect, int minWidth, int maxWidth) {
		int[] proj = getProjection(image, rect);
		int boundary = Integer.MAX_VALUE;
		List<Integer> splitPos = new ArrayList<>();
		for (int i : proj) {
			boundary = i < boundary ? i : boundary;
		}

		boundary *= boundYieldRatio;

		int prevPos = 0;
		for (int i = 0; i < proj.length; ++i) {
			if (proj[i] <= boundary) {
				splitPos.add(i);
			}
		}
		if (!splitPos.isEmpty()) {
			if (splitPos.get(splitPos.size() - 1) < rect[2]) {
				splitPos.add(rect[2]);
			}
		}

		return splitPos;

	}

	public static int[] getProjection(boolean[][] image, int[] rect) {
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
