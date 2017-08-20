package polly;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class ParagraphConsumerTest extends AbstractTest{
	
	@Test
	public void getOutputFileTest(){
		final ParagraphConsumer paragraphConsumer = new ParagraphConsumer();
		final String outputFile = paragraphConsumer.getOutputFile(new RoledWorkPiece(11, "Line1"));
		assertEquals(outputFolder+File.separator+"tts0011.ogg", outputFile);
	}
	
	@Test
	public void getOutputFileTest2(){
		final ParagraphConsumer paragraphConsumer = new ParagraphConsumer();
		final String outputFile = paragraphConsumer.getOutputFile(new RoledWorkPiece(11, "Line1"), 1);
		assertEquals(outputFolder+File.separator+"complex_tts0011_1.ogg", outputFile);
	}

}
