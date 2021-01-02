package polly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParagraphProducer {

    final Logger logger = LoggerFactory.getLogger(ParagraphProducer.class);

    protected static final int MAX_LINE_LENGTH_1000 = 1000;

    private static volatile ParagraphProducer producer;

    private BufferedReader br;

    private AtomicInteger paragraphSeq;

    private List<String> splittedBuf = new ArrayList<>();

    private int linesTotal;

    private AtomicInteger processingLine = new AtomicInteger(0);

    private ParagraphProducer() throws IOException {
        br = new BufferedReader(new FileReader(Config.getInstance().getBookPath(), StandardCharsets.UTF_8));
        paragraphSeq = new AtomicInteger(0);

        try (BufferedReader reader = new BufferedReader(new FileReader(Config.getInstance().getBookPath(), StandardCharsets.UTF_8))) {
            while (reader.readLine() != null) linesTotal++;
        }
    }

    public static ParagraphProducer getInstance() throws IOException {
        ParagraphProducer localParagraphProducer = producer;
        if (localParagraphProducer == null) {
            synchronized (ParagraphProducer.class) {
                localParagraphProducer = producer;
                if (localParagraphProducer == null) {
                    localParagraphProducer = new ParagraphProducer();

                    producer = localParagraphProducer;
                }
            }
        }
        return producer;
    }

    public synchronized IWorkPiece getWork() throws IOException {
        String next = line();

        while (next != null && next.trim().isEmpty()) {
            next = line();
        }

        if (next == null) {
            return null;
        }
        IWorkPiece work;

        if (Config.getInstance().isPlain()) {
            work = new PlainWorkPiece(paragraphSeq.getAndIncrement(), next);
        } else {
            work = new RoledWorkPiece(paragraphSeq.getAndIncrement(), next);
        }
        return work;
    }

    private String line() throws IOException {
        if (splittedBuf.isEmpty()) {
            final String line = br.readLine();

            if (line == null) {
                return null;
            }

            float curentLine = processingLine.getAndIncrement();
            DecimalFormat df = new DecimalFormat("###.##");

            logger.info("Processing line {} of {}, {}% ", curentLine, linesTotal, df.format((curentLine/linesTotal)*100));

            final String[] splitLineIfNeeded = splitLineIfNeeded(line);
            splittedBuf = new ArrayList<>(Arrays.asList(splitLineIfNeeded));
        }
        final Iterator<String> iterator = splittedBuf.iterator();

        final String next = iterator.next();
        iterator.remove();
        return next;
    }

    protected String[] splitLineIfNeeded(String line) {
        if (line != null && line.length() > MAX_LINE_LENGTH_1000) {
            return line.split("\\.");
        } else if (line != null) {
            return new String[]{line};
        }
        return new String[]{};
    }

}
