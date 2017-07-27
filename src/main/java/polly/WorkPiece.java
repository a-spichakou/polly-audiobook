package polly;

public class WorkPiece {
	private static final String REGEX_DIRECT_SPEACH_ENDING = ", -";
	private static final String REGEX_DIRECT_SPEACH_ENDING1 = ", —";
	private String paragraph;
	private int paragraphSeqIdx;
	private boolean directSpeech;

	public WorkPiece(int idx, String paragraph) {
		this.paragraph = paragraph;
		this.paragraphSeqIdx = idx;
	}

	public String formatSSML(){
		final StringBuilder builder = new StringBuilder();
		builder.append("<speak>");
		if(paragraph.trim().startsWith("-") || 
				paragraph.trim().startsWith("—")){
			directSpeech = true;
			
			String[] split = paragraph.split(REGEX_DIRECT_SPEACH_ENDING);
			
			if(split.length==1){
				split = paragraph.split(REGEX_DIRECT_SPEACH_ENDING1);			 
			}
			
			if(paragraphSeqIdx%2==0){
				builder.append("<prosody pitch=\"low\">");
			}else{
				builder.append("<prosody pitch=\"high\">");
			}
			builder.append(split[0]);
			builder.append("</prosody>");
			
			if(split.length>1){
				builder.append(REGEX_DIRECT_SPEACH_ENDING);
			}
			for(int i=1;i<split.length;i++){
				builder.append(split[i]);
			}
		}else{
			builder.append(paragraph);
		}
		builder.append("</speak>");
		return builder.toString();
	}
	
	public String getParagraph() {
		return paragraph;
	}

	public int getParagraphSeqIdx() {
		return paragraphSeqIdx;
	}

	public boolean isDirectSpeech() {
		return directSpeech;
	}
	

}
