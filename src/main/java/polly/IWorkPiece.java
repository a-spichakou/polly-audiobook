package polly;

public interface IWorkPiece {

	public SpeechSentence[] format();

	public int getParagraphSeqIdx();

	public Object getParagraph();
}
