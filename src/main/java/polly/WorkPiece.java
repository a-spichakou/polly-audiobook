package polly;

public class WorkPiece {
	private String paragraph;
	private int paragraphSeqIdx;

	public WorkPiece(int idx, String paragraph) {
		this.paragraph = paragraph;
		this.paragraphSeqIdx = idx;
	}

	public String getParagraph() {
		return paragraph;
	}

	public int getParagraphSeqIdx() {
		return paragraphSeqIdx;
	}

}
