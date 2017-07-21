package polly;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Processor {

	private static volatile Processor instance;

	private Processor() {
	};

	public static Processor getInstance() {
		Processor localInstance = instance;
		if (localInstance == null) {
			synchronized (Processor.class) {
				localInstance = instance;
				if (localInstance == null) {
					localInstance = new Processor();

					instance = localInstance;
				}
			}
		}
		return instance;
	}

	public synchronized void process() throws IOException,
			InterruptedException, ExecutionException {
		
		final ExecutorService workStealingPool = Executors.newWorkStealingPool();
		final List<Worker> produce = WorkersFactory.getInstance().produce(10);
		final List<Future<String>> invokeAll = workStealingPool.invokeAll(produce);
		for (Future<String> task : invokeAll) {
			task.get();
		}
	}

}
