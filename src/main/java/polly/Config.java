package polly;

import java.util.concurrent.ThreadLocalRandom;

public class Config {

    public static final String OUTPUT_FOLDER = "outputFolder";

    public static final String BOOK_PATH = "bookPath";

    public static final String SOVA_URL = "sovaUrl";

    private static volatile Config instance;

    private String bookPath;
    private String outputFolder;
    private boolean isPlain = false;
    public String[] urls;

    private Config() {
    }

    public static Config getInstance() {
        Config localInstance = instance;
        if (localInstance == null) {
            synchronized (Config.class) {
                if (localInstance == null) {
                    localInstance = instance;

                    localInstance = new Config();
                    localInstance.bookPath = System.getProperty(BOOK_PATH);
                    localInstance.outputFolder = System.getProperty(OUTPUT_FOLDER);

                    String property = System.getProperty(SOVA_URL);
                    if (property == null) {
                        localInstance.urls = new String[]{"http://localhost:8899/synthesize/"};
                    } else {
                        localInstance.urls = property.split(";");
                    }

                    instance = localInstance;
                }
            }
        }
        return instance;
    }

    public String getBookPath() {
        return bookPath;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public boolean isPlain() {
        return isPlain;
    }

    public String getSovaURL() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, urls.length);
        return urls[randomNum];
    }
}
