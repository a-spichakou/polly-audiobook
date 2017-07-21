package polly;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class AWSResourceProviderTest extends AbstractTest{

	@Test
	public void getInstanceTest(){
		final AWSResourceProvider instance = AWSResourceProvider.getInstance();
		assertNotNull(instance);
		assertNotNull(instance.getVoice());
	}
}
