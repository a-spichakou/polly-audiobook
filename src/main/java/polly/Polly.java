package polly;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Polly {

	public static void main(String[] args) {
		
		final String validateBookParam = validateBookParam();
		final String validateOutputParam = validateOutputParam();
		if(validateBookParam!=null && !validateBookParam.isEmpty() || 
				(validateOutputParam!=null && !validateOutputParam.isEmpty())){
			System.out.println(validateBookParam);
			System.out.println(validateOutputParam);
		}
		
		try {
			Processor.getInstance().process();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	protected static String validateBookParam(){
		final String bookPath = System.getProperty(Config.BOOK_PATH);
		final StringBuffer buf = new StringBuffer();
		if(bookPath==null || bookPath.isEmpty() || !(new File(bookPath).exists())){
			buf.append("Path to book file -DbookPath is not specified or invalid:");
			buf.append(bookPath);
		}
		return buf.toString();
	}

	protected static String validateOutputParam(){
		final String outputFolder = System.getProperty(Config.OUTPUT_FOLDER);
		final StringBuffer buf = new StringBuffer();
		if(outputFolder==null || outputFolder.isEmpty()){
			buf.append("Path to output folder -DoutputFolder is not specified or invalid:");
			buf.append(outputFolder);
		}
		return buf.toString();
	}
}
