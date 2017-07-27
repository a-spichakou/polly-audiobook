package polly;

import org.junit.Test;

import polly.WorkPiece;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WorkPieceTest {
	
	@Test
	public void formatSSMLTest(){
		WorkPiece piece = new WorkPiece(1, "a");
		assertEquals("<speak>a</speak>",piece.formatSSML());
		
		piece = new WorkPiece(1, "-a");
		assertEquals("<speak><prosody rate=\"fast\">-a</prosody></speak>",piece.formatSSML());
		
		piece = new WorkPiece(1, " -a");
		assertEquals("<speak><prosody rate=\"fast\"> -a</prosody></speak>",piece.formatSSML());
		
		piece = new WorkPiece(1, " —a");
		assertEquals("<speak><prosody rate=\"fast\"> —a</prosody></speak>",piece.formatSSML());
		
		piece = new WorkPiece(1, " —a, - g");
		assertEquals("<speak><prosody rate=\"fast\"> —a</prosody>, - g</speak>",piece.formatSSML());
		
		piece = new WorkPiece(1, " —a, — g");
		assertEquals("<speak><prosody rate=\"fast\"> —a</prosody>, - g</speak>",piece.formatSSML());
	}

}
