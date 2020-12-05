package polly;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

/**
 * TODO description
 *
 * @author aspichakou
 * @since
 */
public class SOVAResponseConverterTest {

    @Test
    public void testWaveExtraction() throws IOException {
        final String theString = IOUtils.toString(getClass().getResource("/sova.json"), "UTF-8");
        final InputStream inputStream = SOVAResponseConverter.extractWave(theString);
        assertNotNull(inputStream);
    }
}