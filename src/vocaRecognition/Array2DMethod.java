package vocaRecognition;

public class Array2DMethod {
	public static int[][] localAverage(int[][] data, int[][] weight) throws Exception {
		int dHeight = data.length;
		int dWidth = data[0].length;
		int wHeight = weight.length;
		int wWidth = weight[0].length;
		if (wHeight % 2 == 0 || wWidth % 2 == 0) {
			throw new Exception("weight matrix has to be odd*odd");
		}
		int xShift = wWidth / 2;
		int yShift = wHeight / 2;
		int[][] res = new int[dHeight][dWidth];
		for (int yd = 0; yd < dHeight; ++yd) {
			for (int xd = 0; xd < dWidth; ++xd) {
				int totalWeight = 0;
				int totalData = 0;
				for (int yw = 0; yw < wHeight; ++yw) {
					for (int xw = 0; xw < wWidth; ++xw) {
						int dx = xw - xShift;
						int dy = yw - yShift;
						int tx = xd + dx;
						int ty = yd + dy;
						if (tx > 0 && ty > 0 && tx < dWidth && ty < dHeight) {
							totalWeight += weight[yw][xw];
							totalData += weight[yw][xw] * data[ty][tx];
						}
					}
				}
				res[yd][xd] = totalData / totalWeight;
			}
		}
		return res;
	}

	public static int[][] generateWeight(int range) {
		int len = range * 2 + 1;
		int[][] weight = new int[len][len];

		for (int y = 0; y < len; ++y) {
			for (int x = 0; x < len; ++x) {
				weight[y][x] = 1;
			}
		}

		return weight;
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
}
