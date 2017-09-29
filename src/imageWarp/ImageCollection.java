package imageWarp;

import java.util.ArrayList;

public class ImageCollection {
	private ArrayList<ImageWarp> imageWarpArr;

	public ImageCollection() {
		imageWarpArr = new ArrayList<>();
	}

	public ImageWarp get(int index) {
		return imageWarpArr.get(index);
	}

	public int size() {
		return imageWarpArr.size();
	}
}
