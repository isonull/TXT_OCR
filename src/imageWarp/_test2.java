package imageWarp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import csv.CSVFileReader;
import vocaRecognition.ImageOutput;
import vocaRecognition.ImagePreprocess;

public class _test2 {
	public static void main(String[] args) throws IOException {
		CSVFileReader reader = new CSVFileReader("/Users/zijunyan/Desktop/test/testSet2/charCen.csv");
		int i = 0;
		double[][] datas = reader.readToDoubleArrs(1, 1);
		for (double[] data : datas) {
			int[] grayData = ImageOutput.makeGrayDataFromProb(data);
			BufferedImage img = ImagePreprocess.makeByteGrayImage(grayData, 60, 60);
			ImageIO.write(img, "PNG", new File("/Users/zijunyan/Desktop/test/testSet2/clusterInfo/" + (i++) + ".PNG"));
		}

	}
}
