package polly;

public interface IWorkPiece {

    SpeechSentence[] format();

    int getParagraphSeqIdx();

    Object getParagraph();
}
