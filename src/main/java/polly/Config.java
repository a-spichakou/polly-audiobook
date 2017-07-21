package polly;

public class Config {
	
	public static final String OUTPUT_FOLDER = "outputFolder";

	public static final String BOOK_PATH = "bookPath";

	private static volatile Config instance;
	
	private String bookPath;
	private String outputFolder;
	
	private Config(){}
	
	public static Config getInstance(){
		Config localInstance = instance;
		if(localInstance==null){
			synchronized (Config.class) {
				if(localInstance==null){
					localInstance = instance;
					
					localInstance = new Config();
					localInstance.bookPath = System.getProperty(BOOK_PATH);
					localInstance.outputFolder = System.getProperty(OUTPUT_FOLDER);
					
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
}
