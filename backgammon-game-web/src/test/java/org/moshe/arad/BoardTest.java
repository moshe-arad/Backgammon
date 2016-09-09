package org.moshe.arad;

import org.junit.Test;
import org.moshe.arad.backgammon.Board;
import org.moshe.arad.backgammon.Pawn;
import static org.junit.Assert.*;

public class BoardTest {

	@Test
	public void setNullPawnOnBoardTest(){
		Board board = new Board();
		
		boolean actaul = board.setPawn(null, 0);
		
		assertFalse("null pawn test failed.", actaul);
	}
	
	@Test
	public void setNegativeIndexPawnOnBoardTest(){
		Board board = new Board();
		
		boolean actual = board.setPawn(Pawn.black, -1);
		
		assertFalse("negative index pawn test failed.", actual);
	}
	
	@Test
	public void setIllegalPositiveIndexPawnOnBoardTest(){
		Board board = new Board();
		
		boolean actual = board.setPawn(Pawn.black, 24);
		
		assertFalse("positive index pawn test failed.", actual);
	}
	
	@Test
	public void setPawnOnDifferentKindOfPawnsColumnTest(){
		Board borad = new Board();
		
		boolean firstPawnSetting = borad.setPawn(Pawn.black, 0);
		
		assertTrue("Setting first pawn on different kind of pawns column fialed.", firstPawnSetting);
		
		boolean actual = borad.setPawn(Pawn.white, 0);
		
		assertFalse("Setting second pawn on different kind of pawns column fialed.", actual);
	}
	
	@Test
	public void setPawnOnFullColumn(){
		Board board = new Board();
		
		for(int i=0; i<Board.MAX_COLUMN; i++)
			board.setPawn(Pawn.black, 0);
		
		boolean actual = board.setPawn(Pawn.black, 0);
		
		assertFalse("Set pawn in full column failed.", actual);
	}
	
	@Test
	public void setPawnValidTest(){
		Board board = new Board();
		
		boolean actual = board.setPawn(Pawn.black, 0);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(Pawn.black, 0);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(Pawn.white, 1);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(Pawn.white, 1);
		
		assertTrue("Valid pawn Setting failed.", actual);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void peekAtColumnInvalidPositiveIndex(){
		Board board = new Board();
		
		board.setPawn(Pawn.black, 0);
		board.peekAtColumn(Board.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void peekAtColumnInvalidNegativeIndex(){
		Board board = new Board();
		
		board.setPawn(Pawn.black, 0);
		board.peekAtColumn(-1);
	}
	
	@Test
	public void equalBoardTest1(){
		Board actaul = new Board();
		Board expected = new Board();
		
		assertEquals("Boards are not equal test #1", expected, actaul);
	}
	
	@Test
	public void equalBoardTest2(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Pawn.black, 0);
		expected.setPawn(Pawn.black, 0);
		
		assertEquals("Boards are not equal test #2", expected, actaul);
	}
	
	@Test
	public void equalBoardTest3(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Pawn.white, 1); 
		expected.setPawn(Pawn.white, 1);
		
		assertEquals("Boards are not equal test #3", expected, actaul);
	}
	
	@Test
	public void equalBoardTest4(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Pawn.white, 0);
		expected.setPawn(Pawn.black, 0);
		
		assertNotEquals("Boards are equal test #4", expected, actaul);
	}
	
	@Test
	public void equalBoardTest5(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Pawn.white, 0);
		actaul.setPawn(Pawn.white, 0);
		expected.setPawn(Pawn.black, 0);
		
		assertNotEquals("Boards are equal test #5", expected, actaul);
	}
	
	@Test
	public void equalBoardTest6(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Pawn.white, 0);
		actaul.setPawn(Pawn.white, 0);
		expected.setPawn(Pawn.white, 0);
		
		assertNotEquals("Boards are equal test #6", expected, actaul);
	}
	
//	@Test
//	public void equalBoardTest7(){
//		Board actaul = new Board();
//		Board expected = new Board();
//		
//		actaul.setPawn(Pawn.white, 0);
//		actaul.getBoard().get(0).push(null);
//		expected.getBoard().get(0).push(Pawn.white);
//		
//		assertNotEquals("Boards are equal test #7", expected, actaul);
//	}
	
	/**********
	 * 
	 * 
	 * 
	 * should check null as argument
	 */
	@Test
	public void boardConstructorTest(){
		Board someBoard = new Board();
		
		someBoard.setPawn(Pawn.black, 0);
		Board actual = new Board(someBoard);
		Board expected = new Board();
		expected.setPawn(Pawn.black, 0);
		
		assertNotSame("These Borad objects are the same.", expected, actual);
		assertEquals("Board constructor failure.", expected, actual);
	}
	
	public void initBoardTest(){
		
	}
}
