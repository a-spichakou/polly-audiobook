package polly;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PollyTest extends AbstractTest{

	@Test
	public void inputParamValidTest(){
		assertEquals("",Polly.validateBookParam());
		assertEquals("",Polly.validateOutputParam());
	}
	
	@Test
	public void inputParamInvalidTest(){
		// make sure that Config initialized
		Config.getInstance();
		
		System.setProperty(Config.BOOK_PATH, "");
		System.setProperty(Config.OUTPUT_FOLDER, "");
		assertEquals("Path to book file -DbookPath is not specified or invalid:",Polly.validateBookParam());
		assertEquals("Path to output folder -DoutputFolder is not specified or invalid:",Polly.validateOutputParam());
	}
}
