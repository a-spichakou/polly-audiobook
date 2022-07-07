package polly;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * SOVA TTS consumer
 *
 * @author aspichakou
 */
public class SOVAParagraphConsumer implements IParagraphConsumer {

    final Logger logger = LoggerFactory.getLogger(SOVAParagraphConsumer.class);

    private static final String TTS_04D_WAVE = "%s" + File.separator + "tts%04d.wav";

    @Override
    public void doWork(IWorkPiece work) throws IOException, InterruptedException {
        final String outputFile = getOutputFile(work);
        if (new File(outputFile).canRead()) {
            return;
        }
        Map<Object, Object> data = new HashMap<>();
        data.put("voice", "Ruslan");
        data.put("pitch", "1.0");
        data.put("rate", "1.0");
        data.put("volume", "0.0");
        data.put("text", work.getParagraph());

        HttpClient client = HttpClient.newHttpClient();
        String sovaURL = Config.getInstance().getSovaURL();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(sovaURL))
                .headers("Content-Type",
                        "application/json")
                .POST(oJsonData(data))
                .build();

        logger.info("Sending request to {}", sovaURL);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        InputStream inputStream = SOVAResponseConverter.extractWave(response.body());
        IOUtils.copy(inputStream, new FileOutputStream(new File(outputFile)));
    }

    public String getOutputFile(IWorkPiece work) {
        return String.format(TTS_04D_WAVE,
                Config.getInstance()
                        .getOutputFolder(), work.getParagraphSeqIdx());
    }

    public static HttpRequest.BodyPublisher oJsonData(Map<Object, Object> data) {
        StringBuilder b = new StringBuilder("{");
        Iterator<Map.Entry<Object, Object>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            var e = iterator.next();
            b = b.append("\"")
                    .append(e.getKey())
                    .append("\"")
                    .append(":")
                    .append("\"")
                    .append(e.getValue())
                    .append("\"");
            if(iterator.hasNext()){
                b = b.append(",");
            }
        }
        b = b.append("}");

        return HttpRequest.BodyPublishers.ofString(b.toString());
    }
}