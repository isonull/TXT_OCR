package imageWarp;

import java.util.ArrayList;
import java.util.List;

import vocaRecognition.VocaSpliter;

public class VocaWarp {
	protected ImageWarp imageWarp;
	private int[] posInfo;
	private ArrayList<CharWarp> charWarpArr;

	public VocaWarp(ImageWarp imgWarp, int[] rect) {
		imageWarp = imgWarp;
		posInfo = rect;
	}

	public int[] getPosInfo() {
		return posInfo;
	}

	public ArrayList<CharWarp> getCharWarps() {
		return charWarpArr;
	}

	public void generateNaiveCharWarps(int range) throws Exception {
		charWarpArr = new ArrayList<>();
		List<int[]> charRects = VocaSpliter.naiveCharDetectionByCluster(imageWarp.getBinData(), true, posInfo, range);
		for (int[] rect : charRects) {
			charWarpArr.add(new CharWarp(this, rect));
		}
	}
}
