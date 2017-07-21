package polly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ConfigTest extends AbstractTest{

	@Test
	public void getInstanceTest(){
		assertNotNull(Config.getInstance());
	}
	
	@Test
	public void testHandleProperties(){
		final Config instance = Config.getInstance();
		assertEquals(bookPath,instance.getBookPath());
		assertEquals(outputFolder,instance.getOutputFolder());
	}
}
