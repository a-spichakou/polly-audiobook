package polly;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IParagraphConsumer {
    void doWork(IWorkPiece work) throws FileNotFoundException, IOException, InterruptedException;
}
