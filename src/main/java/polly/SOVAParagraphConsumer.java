package polly;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        data.put("voice", "Natasha");
        data.put("text", work.getParagraph());

        String boundary = "-------------oiawn4tp89n4e9p5";

        HttpClient client = HttpClient.newHttpClient();
        String sovaURL = Config.getInstance().getSovaURL();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(sovaURL))
                .headers("Content-Type",
                        "multipart/form-data; boundary=" + boundary)
                .POST(oMultipartData(data, boundary))
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

    public static HttpRequest.BodyPublisher oMultipartData(Map<Object, Object> data,
                                                           String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("--" + boundary
                + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            byteArrays.add(separator);

            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\""
                        + path.getFileName() + "\"\r\nContent-Type: " + mimeType
                        + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(
                        ("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue()
                                + "\r\n").getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays
                .add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }

}