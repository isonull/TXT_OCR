package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.rosuda.JRI.Rengine;

import rSupport.UniqueRengine;
import vocaRecognition.ImageOutput;
import vocaRecognition.ImagePreprocess;
import vocaRecognition.VocaDetection;
import vocaRecognition.VocaRecognition;

public class test1 {
	public static void main(String[] args) throws Exception {
		Rengine eg = UniqueRengine.getRengine();
		// eg.assign("a", new int[] { 1 });
		// eg.assign("b", new int[] { 2 });
		// REXP res = eg.eval("a+b");
		// System.out.println(res);

		BufferedImage image = ImageIO.read(new File("/Users/zijunyan/Desktop/test/Origin.JPG"));
		int[][] arrGray = ImagePreprocess.imageBGR_3BYTEtoGRAY_BYTE(image);
		// boolean[][] imageArrBin =

		// ImageIO.write(ImagePreprocess.makeByteGrayImage(arrGray, 0,
		// arrGray[0].length, 0, arrGray.length), "PNG",
		// new File("/Users/zijunyan/Desktop/test/imageArrGray.PNG"));

		int[][] arrGrayAvr = ImagePreprocess.GrayLocalAverage(arrGray, 1);

		ImageIO.write(ImagePreprocess.makeByteGrayImage(arrGrayAvr, 0, arrGrayAvr[0].length, 0, arrGrayAvr.length),
				"PNG", new File("/Users/zijunyan/Desktop/test/imageArrGrayAvr.PNG"));

		// boolean[][] arrBin = ImagePreprocess.grayThreshold(arrGray, 50, 64,
		// 0.65);
		boolean[][] arrBinAvr = ImagePreprocess.grayThreshold(arrGrayAvr, 50, 64, 0.65);
		// ImagePreprocess.thresholdTest(arrGrayAvr, 10, 64, 0.65, eg);
		System.out.println("threahold done");
		// BufferedImage imageArrBin = ImagePreprocess.makeBinImage(arrBin);
		BufferedImage imageArrBinAvr = ImagePreprocess.makeBinImage(arrBinAvr);
		System.out.println("mkBinImage done");
		// ArrayList<int[]> rects = VocaDetection.detect(arrBin, true, 10);
		ArrayList<int[]> rectsAvr = VocaDetection.detect(arrBinAvr, true, 10);
		System.out.println("detect done");

		// BufferedImage imageOut = ImageOutput.addRectangles(imageArrBin,
		// rects);
		BufferedImage imageAvrOut = ImageOutput.addRectangles(imageArrBinAvr, rectsAvr);

		VocaRecognition.naiveFeatureFetch(arrBinAvr, rectsAvr.toArray(new int[rectsAvr.size()][]), 50, 50);
		// ImageIO.write(imageOut, "PNG", new
		// File("/Users/zijunyan/Desktop/test/imageOut.PNG"));
		ImageIO.write(imageAvrOut, "PNG", new File("/Users/zijunyan/Desktop/test/imageAvrOut.PNG"));

		eg.end();
	}
}
