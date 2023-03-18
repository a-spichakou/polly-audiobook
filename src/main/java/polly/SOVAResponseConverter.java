package polly;

import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extracting WAVE from JSON
 *
 * @author aspichakou
 */
public class SOVAResponseConverter {
    final static Logger logger = LoggerFactory.getLogger(SOVAResponseConverter.class);

    private SOVAResponseConverter() {

    }

    public static InputStream extractWave(String jsonResponse) {
        String base64Wave = JsonPath.read(jsonResponse, "$.response[0].response_audio");
        byte[] decoded = Base64.getDecoder().decode(base64Wave);
        return new ByteArrayInputStream(decoded);
    }
}