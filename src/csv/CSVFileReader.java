package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class CSVFileReader {
	BufferedReader fileReader;

	public CSVFileReader(String path) throws FileNotFoundException {
		fileReader = new BufferedReader(new FileReader(new File(path)));
	}

	public double[][] readToDoubleArrs(int rowOffset, int colOffset) throws IOException {
		Queue<String> lines = new LinkedList<>();
		String line;
		while ((line = fileReader.readLine()) != null) {
			lines.add(line);
		}

		String header = lines.peek();
		String[] headerSplit = header.split(",");
		int width = headerSplit.length;

		double[][] data = new double[lines.size() - rowOffset][width - colOffset];

		for (int i = 0; i < rowOffset; ++i) {
			lines.poll();
		}

		int lineCount = 0;
		while (!lines.isEmpty()) {
			String[] split = lines.poll().split(",");
			for (int i = 0; i < data[lineCount].length; ++i) {
				data[lineCount][i] = Double.parseDouble(split[i + colOffset]);
			}
			lineCount++;
		}
		return data;
	}

}
