package imageWarp;

import java.util.ArrayList;
import java.util.List;

import vocaRecognition.VocaSpliter;

public class VocaWarp {
	ImageWarp imageWarp;
	private int[] posInfo;
	private ArrayList<CharWarp> charWarp;

	public VocaWarp(ImageWarp imgWarp, int[] rect) {
		imageWarp = imgWarp;
		posInfo = rect;
	}

	public int[] getPosInfo() {
		return posInfo;
	}

	public ArrayList<CharWarp> getCharWarps() {
		return charWarp;
	}

	public void generateCharWarps(int minWidth, int maxWidth) throws Exception {
		List<Integer> splitPos = VocaSpliter.NaiveHorizontalSplit(imageWarp.getBinData(), posInfo, minWidth, maxWidth);

	}
}
