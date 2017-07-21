package polly;

import java.io.IOException;
import java.util.concurrent.Callable;

public class Worker implements Callable<String>{

	@Override
	public String call() {
		final ParagraphConsumer consumer = new ParagraphConsumer();
		try {
			for(WorkPiece work = null; (work = ParagraphProducer.getInstance().getWork()) !=null;){
					consumer.doWork(work);	
			}
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} 
		return null;
	}
}
