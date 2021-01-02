package polly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoledWorkPiece extends AbstractWorkPiece{

	protected static final String[] MINUSES = new String[] {"—", "-", "–"};
	protected static final String[] SPACES = new String[] {" ", " "};
	protected static final String[] ENDINGS = new String[] {"\\.",",","\\?", "!", "…"};
	
	protected static List<String> ends = new ArrayList<>();
	protected static List<String> startings = new ArrayList<>();
	
	static{
		for(String ending: ENDINGS){
			for(String space: SPACES){
				for(String minus: MINUSES){
					String speachEnding = ending+space+minus;
					ends.add(speachEnding);
				}
			}
		}
		
		startings.addAll(Arrays.asList(MINUSES));
		
		for(String space: SPACES){
			for(String minus: MINUSES){
				String speachEnding = space+minus;
				startings.add(speachEnding);
			}
		}
	}
	
	
	public RoledWorkPiece(int idx, String paragraph) {
		this.paragraph = paragraph;
		this.paragraphSeqIdx = idx;
	}
	
	protected String[] splitDirectSpeech(String line) {
		String[] split = new String[] { line };
		for (String ending : ends) {
			split = line.split(ending);
			if (split.length > 1) {
				return split;
			}
		}
		return split;
	}

	public SpeechSentence[] format(){
		final List<SpeechSentence> sentences = new ArrayList<>();
		if(startWith(paragraph)){
			splitBySentences(paragraph, sentences);
		} else{
			sentences.add(new SpeechSentence(false, paragraph));
		}
		
		return sentences.toArray(new SpeechSentence[]{});
	}

	private void splitBySentences(String line, final List<SpeechSentence> sentences) {
		String[] split = splitDirectSpeech(line);
		boolean flag = true;
		
		for(int i=0;i<split.length;i++){
			final String sentence = split[i];
			
			final String[] splitDirectSpeech = splitDirectSpeech(sentence);
			if(splitDirectSpeech.length>1){
				splitBySentences(sentence, sentences);
				flag = !flag;
			}else{
				if(i%2==0){
					sentences.add(new SpeechSentence(flag, sentence));
				} else{
					sentences.add(new SpeechSentence(!flag, sentence));
				}
			}
			
		}
	}
	
	private boolean startWith(String line){
		final String trimmed = line.trim();
		for(String minus: startings){
			if(trimmed.startsWith(minus)){
				return true;
			}
		}
		return false;
	}
}
