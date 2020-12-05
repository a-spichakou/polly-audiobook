package polly;

public class Config {

    public static final String OUTPUT_FOLDER = "outputFolder";

    public static final String BOOK_PATH = "bookPath";

    public static final String SOVA_URL = "sovaUrl";

    private static volatile Config instance;

    private String bookPath;
    private String outputFolder;
    private boolean isPlain = false;
    private String sovaURL;


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
                    localInstance.sovaURL = System.getProperty(SOVA_URL) != null ? System.getProperty(SOVA_URL) : "http://192.168.1.123:8899/synthesize/";

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
        return sovaURL;
    }
}
