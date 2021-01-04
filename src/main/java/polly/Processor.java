package polly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Processor {

    final Logger logger = LoggerFactory.getLogger(Processor.class);

    private static volatile Processor instance;

    private Processor() {
    }

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

    public synchronized void process() throws InterruptedException {
        final ExecutorService workStealingPool = Executors.newWorkStealingPool();
        final List<Worker> produce = WorkersFactory.getInstance().produce(6);
        final List<Future<String>> invokeAll = workStealingPool.invokeAll(produce);
        invokeAll.parallelStream().forEach(t -> {
            try {
                t.get();
            } catch (InterruptedException e) {
                logger.error("Processing error: ", e);
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                logger.error("Processing error: ", e);
            }
        });
    }

}
