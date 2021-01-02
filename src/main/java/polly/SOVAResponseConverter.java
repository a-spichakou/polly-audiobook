package polly;

import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * Extracting WAVE from JSON
 *
 * @author aspichakou
 */
public class SOVAResponseConverter {

    private SOVAResponseConverter() {

    }

    public static InputStream extractWave(String jsonResponse) {
        String base64Wave = JsonPath.read(jsonResponse, "$.response[0].response_audio");
        byte[] decoded = Base64.getDecoder().decode(base64Wave);
        return new ByteArrayInputStream(decoded);
    }
}