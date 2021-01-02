package polly;

public class PlainWorkPiece extends AbstractWorkPiece {

    public PlainWorkPiece(int idx, String paragraph) {
        this.paragraph = paragraph;
        this.paragraphSeqIdx = idx;
    }

    public SpeechSentence[] format() {
        return new SpeechSentence[]{new SpeechSentence(false, paragraph)};
    }

}
