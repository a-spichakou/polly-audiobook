package polly;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class ParagraphConsumerTest extends AbstractTest{
	
	@Test
	public void getOutputFileTest(){
		final ParagraphConsumer paragraphConsumer = new ParagraphConsumer();
		final String outputFile = paragraphConsumer.getOutputFile(new WorkPiece(11, "Line1"));
		assertEquals(outputFolder+File.separator+"tts0011.ogg", outputFile);
	}

}
