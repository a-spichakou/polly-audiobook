package polly;

public class SpeechSentence {

	private boolean directSpeech;
	
	private String formattedSentence;
	
	public SpeechSentence(boolean directSpeech, String sentence){
		this.directSpeech = directSpeech;
		this.formattedSentence = sentence;
	}
	
	protected String formatToSSML(String line){
		final StringBuilder builder = new StringBuilder();
		builder.append("<speak>");
		builder.append(line);
		builder.append("</speak>");
		return builder.toString();
	}

	public boolean isDirectSpeech() {
		return directSpeech;
	}

	public String getFormattedSentence() {
		return formatToSSML(formattedSentence);
	}
	
	
}
