package test;

//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javax.imageio.ImageIO;
//
//import org.rosuda.JRI.Rengine;
//
//import detection.CharDetection;
//import imageOutput.ImageOutput;
//import imagePreprocess.ImageMethod;
//
//public class test {
//	public static void main(String[] args) throws IOException {
//		Rengine.versionCheck();
//		Rengine engine = new Rengine();
//		engine.eval("1+1");
//		// engine.assign("a", new int[] { 123131, 421414 });
//		BufferedImage i = ImageIO.read(new File("/Users/zijunyan/Desktop/test/Origin copy.JPG"));
//		int[][] bytes = ImageMethod.imageBGR_3BYTEtoGRAY_BYTE(i);
//		boolean[][] bools = ImageMethod.threshhold(bytes, 100, 64, 0.65);
//		BufferedImage image = ImageMethod.getBinImage(bools);
//		ImageIO.write(image, "PNG", new File("/Users/zijunyan/Desktop/test/output.JPG"));
//		ArrayList<int[]> rects = CharDetection.detect(bools, true, 8);
//		for (int[] rect : rects) {
//			System.out.println("" + rect[0] + "  " + rect[1]);
//		}
//		ImageOutput.addRectangles(i, rects);
//		ImageIO.write(i, "PNG", new File("/Users/zijunyan/Desktop/test/output2.JPG"));
//	}
//}
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

class TextConsole implements RMainLoopCallbacks {
	public void rWriteConsole(Rengine re, String text, int oType) {
		System.out.print(text);
	}

	public void rBusy(Rengine re, int which) {
		System.out.println("rBusy(" + which + ")");
	}

	public String rReadConsole(Rengine re, String prompt, int addToHistory) {
		System.out.print(prompt);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = br.readLine();
			return (s == null || s.length() == 0) ? s : s + "\n";
		} catch (Exception e) {
			System.out.println("jriReadConsole exception: " + e.getMessage());
		}
		return null;
	}

	public void rShowMessage(Rengine re, String message) {
		System.out.println("rShowMessage \"" + message + "\"");
	}

	public String rChooseFile(Rengine re, int newFile) {
		FileDialog fd = new FileDialog(new Frame(), (newFile == 0) ? "Select a file" : "Select a new file",
				(newFile == 0) ? FileDialog.LOAD : FileDialog.SAVE);
		fd.show();
		String res = null;
		if (fd.getDirectory() != null)
			res = fd.getDirectory();
		if (fd.getFile() != null)
			res = (res == null) ? fd.getFile() : (res + fd.getFile());
		return res;
	}

	public void rFlushConsole(Rengine re) {
	}

	public void rLoadHistory(Rengine re, String filename) {
	}

	public void rSaveHistory(Rengine re, String filename) {
	}
}
