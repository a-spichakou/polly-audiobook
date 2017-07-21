package polly;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class PollyTestData {

	public static final int TEST_LINES_COUNT_10 = 10;

	private static volatile PollyTestData instance;

	private final String LINE = "line";
	private String bookPath;
	private String outputFolder;
	private BufferedWriter bufferedWriter = null;

	private PollyTestData() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("testFile", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		tempFile.deleteOnExit();

		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(tempFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			int i = 0;
			for (; i < TEST_LINES_COUNT_10; i++) {
				bufferedWriter.write(LINE + i);
				bufferedWriter.newLine();
			}
			// Line has to bi splitted
			
			final StringBuffer buf = new StringBuffer();
			for(int k=0;k<ParagraphProducer.MAX_LINE_LENGTH_1000/2+1;k++){
				buf.append("a"+i+".");
				i++;
			}
			
			bufferedWriter.write(buf.toString());
			bufferedWriter.newLine();
			
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path createTempDirectory = null;
		try {
			createTempDirectory = Files.createTempDirectory("testDir");
		} catch (IOException e) {
			e.printStackTrace();
		}

		bookPath = tempFile.getAbsolutePath();
		outputFolder = createTempDirectory.toString();
	};

	public static PollyTestData getInstance() {
		PollyTestData localInstance = instance;
		if (localInstance == null) {
			synchronized (PollyTestData.class) {
				localInstance = instance;
				if (localInstance == null) {
					localInstance = instance = new PollyTestData();
				}
			}
		}
		return instance;
	}

	public String getLINE() {
		return LINE;
	}

	public String getBookPath() {
		return bookPath;
	}

	public String getOutputFolder() {
		return outputFolder;
	}
	

}
