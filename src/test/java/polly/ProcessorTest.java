package polly;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ProcessorTest extends AbstractTest {

	@Test
	public void getInstanceTest() {
		assertNotNull(Processor.getInstance());
	}
}
