package imageWarp;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import csv.CSVFile;
import vocaRecognition.ImageOutput;

public class _test {
	public static void main(String[] args) throws Exception {
		BufferedImage bfImage = ImageIO.read(new File("/Users/zijunyan/Desktop/test/testSet2/1.JPG"));
		ImageWarp iw = new ImageWarp(bfImage);
		iw.generateGrayData();
		iw.generateBinData();
		iw.generateVocaPositionInfo();
		ArrayList<int[]> vocaRects = new ArrayList<>();
		ArrayList<int[]> charRects = new ArrayList<>();
		CSVFile cf = new CSVFile();
		cf.setHeader(CSVFile.generateDefaultHeader(60 * 60));
		for (VocaWarp vocaWarp : iw.getVocaWarps()) {
			vocaRects.add(vocaWarp.getPosInfo());
			vocaWarp.generateNaiveCharWarps(1);
			for (CharWarp charWarp : vocaWarp.getCharWarps()) {
				charRects.add(charWarp.getPosInfo());
				charWarp.generateData(60, 60);
				BufferedImage charImage = charWarp.generateDataImage(60, 60);
				String name = charWarp.getSuggestedFileName();
				System.out.println("outputing char at " + name);
				ImageIO.write(charImage, "PNG", new File("/Users/zijunyan/Desktop/test/testSet2/chars/" + name));
				cf.addDataRow(charWarp.getCSVDataRowString());
			}
		}

		cf.outputFile("/Users/zijunyan/Desktop/test/testSet2/charData.csv");

		ImageOutput.addRectangles(bfImage, vocaRects, Color.red);
		ImageOutput.addRectangles(bfImage, charRects, Color.blue);
		ImageIO.write(bfImage, "PNG", new File("/Users/zijunyan/Desktop/test/testSet2/test"));
		ImageIO.write(iw.getBinImage(), "PNG", new File("/Users/zijunyan/Desktop/test/testSet2/testBin"));
		ImageIO.write(iw.getGrayImage(), "PNG", new File("/Users/zijunyan/Desktop/test/testSet2/testGray"));
	}
}
