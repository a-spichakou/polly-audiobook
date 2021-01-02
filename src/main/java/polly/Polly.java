package polly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Polly {

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Polly.class);

        final String validateBookParam = validateBookParam();
        final String validateOutputParam = validateOutputParam();
        if (!validateBookParam.isEmpty() || !validateOutputParam.isEmpty()) {
            logger.info(validateBookParam);
            logger.info(validateOutputParam);
            return;
        }

        try {
            Processor.getInstance().process();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Execution fail: ", e);
            Thread.currentThread().interrupt();
        }
    }

    protected static String validateBookParam() {
        final String bookPath = System.getProperty(Config.BOOK_PATH);
        final StringBuilder buf = new StringBuilder();
        if (bookPath == null || bookPath.isEmpty() || !(new File(bookPath).exists())) {
            buf.append("Path to book file -DbookPath is not specified or invalid:");
            buf.append(bookPath);
        }
        return buf.toString();
    }

    protected static String validateOutputParam() {
        final String outputFolder = System.getProperty(Config.OUTPUT_FOLDER);
        final StringBuilder buf = new StringBuilder();
        if (outputFolder == null || outputFolder.isEmpty()) {
            buf.append("Path to output folder -DoutputFolder is not specified or invalid:");
            buf.append(outputFolder);
        }
        return buf.toString();
    }
}
