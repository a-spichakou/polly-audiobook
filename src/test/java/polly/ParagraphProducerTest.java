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
		
		for(int i=0;i<PollyTestData.TEST_LINES_COUNT_10;i++){
			try {
				work = instance.getWork();
				assertNotNull(work);
				assertEquals(LINE+work.getParagraphSeqIdx(),work.getParagraph());	
			} catch (IOException e) {
				fail(e.getLocalizedMessage());
			}
		}
		
		for (int i = 0; i < ParagraphProducer.MAX_LINE_LENGTH_1000 / 2 + 1; i++) {
			try {
				work = instance.getWork();
				assertNotNull(work);
				assertEquals("a" + work.getParagraphSeqIdx(), work.getParagraph());

			} catch (IOException e) {
				fail(e.getLocalizedMessage());
			}
		}
	}
	
	@Test
	public void splitLineTest(){
		ParagraphProducer instance = null;
		try {
			instance = ParagraphProducer.getInstance();
		} catch (FileNotFoundException e1) {
			fail(e1.getLocalizedMessage());
		}
		
		assertNotNull(instance.splitLineIfNeeded(null));
		
		assertEquals("line", instance.splitLineIfNeeded("line")[0]);
		assertEquals(1, instance.splitLineIfNeeded("line").length);
		assertEquals("li.ne", instance.splitLineIfNeeded("li.ne")[0]);
		
		final StringBuffer buf = new StringBuffer();
		for(int i=0;i<ParagraphProducer.MAX_LINE_LENGTH_1000/2+1;i++){
			buf.append("a.");
		}
		
		final String[] splitLineIfNeeded = instance.splitLineIfNeeded(buf.toString());
		
		assertEquals(ParagraphProducer.MAX_LINE_LENGTH_1000/2+1, splitLineIfNeeded.length);
	}
}
