package polly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class RoledWorkPieceTest {
	
	@Test
	public void formatSSMLTest(){
		RoledWorkPiece piece = new RoledWorkPiece(1, "a");
		piece = new RoledWorkPiece(1, "- А, это вы, мистер Байерсон.");
		SpeechSentence[] format = piece.format();
		assertTrue(format[0].isDirectSpeech());
		
		piece = new RoledWorkPiece(1,"– Я вас чуть не потеряла, доктор Огаст.");
		format = piece.format();
		assertEquals("<speak>– Я вас чуть не потеряла, доктор Огаст.</speak>",format[0].getFormattedSentence());
		assertTrue(format[0].isDirectSpeech());
		
		piece = new RoledWorkPiece(1, "- Как это получилось, что мы с ней… - сказал Барни, показывая рукой в сторону спальни. - Вот так, сразу?, - Химия, - сказал чемоданчик.");
		format = piece.format();
		assertEquals("<speak>- Как это получилось, что мы с ней</speak>",format[0].getFormattedSentence());
		assertTrue(format[0].isDirectSpeech());
		assertEquals("<speak> сказал Барни, показывая рукой в сторону спальни</speak>",format[1].getFormattedSentence());
		assertFalse(format[1].isDirectSpeech());
		assertEquals("<speak> Вот так, сразу?</speak>",format[2].getFormattedSentence());
		assertTrue(format[2].isDirectSpeech());
		assertEquals("<speak> Химия</speak>",format[3].getFormattedSentence());
		//TODO
		assertFalse(format[3].isDirectSpeech());
		assertEquals("<speak> сказал чемоданчик.</speak>",format[4].getFormattedSentence());
		//TODO
		assertTrue(format[4].isDirectSpeech());
		
		piece = new RoledWorkPiece(1, "— Теяфай — плохое место для детей.");
		format = piece.format();
		assertEquals("<speak>— Теяфай — плохое место для детей.</speak>",format[0].getFormattedSentence());

		piece = new RoledWorkPiece(1, " —a");
		format = piece.format();
		assertEquals("<speak> —a</speak>",format[0].getFormattedSentence());
		assertTrue(format[0].isDirectSpeech());
		
		piece = new RoledWorkPiece(1, " —a, —g");
		format = piece.format();
		assertEquals("<speak> —a</speak>",format[0].getFormattedSentence());
		assertTrue(format[0].isDirectSpeech());
		assertEquals("<speak>g</speak>",format[1].getFormattedSentence());
		assertFalse(format[1].isDirectSpeech());
		
		piece = new RoledWorkPiece(1, " —a. —g");
		format = piece.format();
		assertEquals("<speak> —a</speak>",format[0].getFormattedSentence());
		assertTrue(format[0].isDirectSpeech());
		assertEquals("<speak>g</speak>",format[1].getFormattedSentence());
		assertFalse(format[1].isDirectSpeech());
		
		piece = new RoledWorkPiece(1, " —a, —g. —blah");
		format = piece.format();
		assertEquals("<speak> —a</speak>",format[0].getFormattedSentence());
		assertTrue(format[0].isDirectSpeech());
		assertEquals("<speak>g</speak>",format[1].getFormattedSentence());
		assertFalse(format[1].isDirectSpeech());
		assertEquals("<speak>blah</speak>",format[2].getFormattedSentence());
		assertTrue(format[2].isDirectSpeech());
		
		piece = new RoledWorkPiece(1, "— Да, — ответил Ога. — Возвращаемся домой.");
		format = piece.format();
		assertEquals("<speak>— Да</speak>",format[0].getFormattedSentence());
		assertTrue(format[0].isDirectSpeech());
		assertEquals("<speak> ответил Ога</speak>",format[1].getFormattedSentence());
		assertFalse(format[1].isDirectSpeech());
		assertEquals("<speak> Возвращаемся домой.</speak>",format[2].getFormattedSentence());
		assertTrue(format[2].isDirectSpeech());
	}

}
