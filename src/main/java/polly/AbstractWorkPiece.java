package polly;

public abstract class AbstractWorkPiece implements IWorkPiece {

    protected String paragraph;
    protected int paragraphSeqIdx;

    @Override
    public String getParagraph() {
        return paragraph;
    }

    public int getParagraphSeqIdx() {
        return paragraphSeqIdx;
    }

}
