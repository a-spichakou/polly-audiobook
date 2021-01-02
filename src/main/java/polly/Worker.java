package polly;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker implements Callable<String> {

    final Logger logger = LoggerFactory.getLogger(Worker.class);

    @Override
    public String call() {
        final IParagraphConsumer consumer = new SOVAParagraphConsumer();
        IWorkPiece work;
        try {
            while ((work = ParagraphProducer.getInstance().getWork()) != null) {
                while (!doWork(consumer, work)) {
                    logger.error("Retry generation...");
                }
            }
        } catch (IOException e) {
            logger.error("Processing error: ", e);
            return e.getMessage();
        }
        return null;
    }

    private boolean doWork(IParagraphConsumer consumer, IWorkPiece work) {
        try {
            consumer.doWork(work);
            return true;
        } catch (IOException e) {
            logger.error("Processing error: ", e);
        } catch (InterruptedException e) {
            logger.error("Processing interrupted: ", e);
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
