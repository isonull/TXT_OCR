package rSupport;

import org.rosuda.JRI.Rengine;

public final class UniqueRengine {
	private static Rengine rEngine = null;

	private UniqueRengine() {

	}

	public static Rengine getRengine() {
		if (rEngine == null) {
			rEngine = new Rengine(new String[] { "--no-save" }, false, null);
		}
		return rEngine;
	}
}
