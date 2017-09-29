package imageWarp;

public class CharWarp {
	private VocaWarp vocaWarp;
	private char charr;
	// base and len is relative to vocaWarp
	private int base;
	private int len;

	public char getChar() {
		return charr;
	}

	public CharWarp(VocaWarp vocaWp, int base, int len) {
		vocaWarp = vocaWp;
	}
}
