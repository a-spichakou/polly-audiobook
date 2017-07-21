package polly;

import java.io.IOException;

import org.junit.BeforeClass;

public class AbstractTest {
	protected static String LINE;
	protected static String bookPath;
	protected static String outputFolder;

	@BeforeClass
	public static void beforeClass() throws IOException{
		final PollyTestData instance = PollyTestData.getInstance();
		LINE=instance.getLINE();
		bookPath = instance.getBookPath();
		outputFolder = instance.getOutputFolder();
		
		System.setProperty(Config.BOOK_PATH, instance.getBookPath());
		System.setProperty(Config.OUTPUT_FOLDER, instance.getOutputFolder());
		
	}

}
