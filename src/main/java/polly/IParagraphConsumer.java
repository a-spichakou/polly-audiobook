package polly;

import java.io.IOException;

public interface IParagraphConsumer {
    void doWork(IWorkPiece work) throws IOException, InterruptedException;
}
