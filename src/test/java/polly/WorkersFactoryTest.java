package polly;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class WorkersFactoryTest {

	@Test
	public void getInstanceTest(){
		assertNotNull(WorkersFactory.getInstance());
	}
	
	@Test
	public void produceTest(){
		List<Worker> produce = WorkersFactory.getInstance().produce(-1);
		assertNotNull(produce);
		assertEquals(0, produce.size());
		produce = WorkersFactory.getInstance().produce(1);

		assertEquals(1, produce.size());
		assertNotNull(produce.get(0));
		
	}
}
