package org.moshe.arad;

import org.junit.Test;
import org.moshe.arad.backgammon.Board;
import org.moshe.arad.backgammon.Pawn;
import static org.junit.Assert.*;

public class BoardTest {

	@Test
	public void boardConstructorTest(){
		Board someBoard = new Board();
		
		someBoard.getBoard().get(0).push(Pawn.black);
		Board actual = new Board(someBoard);
		Board expected = new Board();
		expected.getBoard().get(0).push(Pawn.black);
		
		assertNotSame("These Borad objects are the same.", expected, actual);
		assertEquals("Board constructor failure.", expected, actual);
	}
}
