package csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class CSVFile {
	private Queue<String> data;
	private String header;

	public CSVFile() {
		header = null;
		data = new LinkedList<>();
	}

	public static String generateDefaultHeader(int n) {
		String header = "";
		for (int i = 0; i < n - 1; ++i) {
			header += i + ",";
		}
		if (n != 0) {
			header += n - 1;
		}

		return header;
	}

	public void setHeader(String h) {
		header = h;
	}

	public void setHeader(String[] hs) {
		header = "";
		for (int i = 0; i < hs.length - 1; ++i) {
			header += hs[i] + ",";
		}
		if (hs.length != 0) {
			header += hs[hs.length - 1];
		}
	}

	public void addDataRow(String dataRow) {
		data.offer(dataRow);
	}

	public void outputFile(String path) throws IOException {
		FileWriter fw = new FileWriter(path);
		fw.write(header + "\n");
		while (!data.isEmpty()) {
			fw.write(data.poll() + "\n");
		}
		fw.close();
	}

}
