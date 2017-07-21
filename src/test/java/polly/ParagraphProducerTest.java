package polly;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;


public class ParagraphProducerTest extends AbstractTest{
	
	@Test
	public void getInstanceTest(){
		try {
			assertNotNull(ParagraphProducer.getInstance());
		} catch (FileNotFoundException e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void getWorkTest(){
		ParagraphProducer instance = null;
		try {
			instance = ParagraphProducer.getInstance();
		} catch (FileNotFoundException e1) {
			fail(e1.getLocalizedMessage());
		}
		WorkPiece work = null;
		try {
			work = instance.getWork();
		} catch (IOException e) {
			fail(e.getLocalizedMessage());
		}
		
		assertNotNull(work);
		assertEquals(LINE+work.getParagraphSeqIdx(),work.getParagraph());
	}
}
