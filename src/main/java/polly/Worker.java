package polly;

import java.io.IOException;
import java.util.concurrent.Callable;

public class Worker implements Callable<String>{

	@Override
	public String call() {
		final IParagraphConsumer consumer = new SOVAParagraphConsumer();
		IWorkPiece work = null;
		try {
			for(; (work = ParagraphProducer.getInstance().getWork()) !=null;){
					consumer.doWork(work);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} 
		return null;
	}
}
