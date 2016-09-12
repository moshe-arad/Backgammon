package org.moshe.arad.backgammon;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Color;


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
		
		boolean actual = board.setPawn(Color.black, -1);
		
		assertFalse("negative index pawn test failed.", actual);
	}
	
	@Test
	public void setIllegalPositiveIndexPawnOnBoardTest(){
		Board board = new Board();
		
		boolean actual = board.setPawn(Color.black, 24);
		
		assertFalse("positive index pawn test failed.", actual);
	}
	
	@Test
	public void setPawnOnDifferentKindOfPawnsColumnTest(){
		Board borad = new Board();
		
		boolean firstPawnSetting = borad.setPawn(Color.black, 0);
		
		assertTrue("Setting first pawn on different kind of pawns column fialed.", firstPawnSetting);
		
		boolean actual = borad.setPawn(Color.white, 0);
		
		assertFalse("Setting second pawn on different kind of pawns column fialed.", actual);
	}
	
	@Test
	public void setPawnOnFullColumn(){
		Board board = new Board();
		
		for(int i=0; i<Board.MAX_COLUMN; i++)
			board.setPawn(Color.black, 0);
		
		boolean actual = board.setPawn(Color.black, 0);
		
		assertFalse("Set pawn in full column failed.", actual);
	}
	
	@Test
	public void setPawnValidTest(){
		Board board = new Board();
		
		boolean actual = board.setPawn(Color.black, 0);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(Color.black, 0);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(Color.white, 1);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(Color.white, 1);
		
		assertTrue("Valid pawn Setting failed.", actual);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void peekAtColumnInvalidPositiveIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.peekAtColumn(Board.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void peekAtColumnInvalidNegativeIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.peekAtColumn(-1);
	}
	
	@Test
	public void peekAtEmptyColumnTest(){
		Board board = new Board();
		
		Color actualColor = board.peekAtColumn(0);
		
		assertNull("Peek at EMPTY column failed.", actualColor);
	}
	
	@Test
	public void peekAtColumnTest(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		Color actual = board.peekAtColumn(0);
		
		assertThat("Peek at column failed.", actual, is(Color.black));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void sizeOfColumnInvalidPositiveIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.getSizeOfColumn(Board.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void sizeOfColumnInvalidNegativeIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.getSizeOfColumn(-1);
	}
	
	@Test
	public void sizeOfColumnTest(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.setPawn(Color.black, 0);
		board.setPawn(Color.black, 0);
		int actual = board.getSizeOfColumn(0);
		
		assertThat("Size of column failed.", actual, is(3));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void isEmptyColumnInvalidPositiveIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.isEmptyColumn(Board.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void isEmptyColumnInvalidNegativeIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.isEmptyColumn(-1);
	}
	
	@Test
	public void isEmptyColumnNotEmptyTest(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.setPawn(Color.black, 0);
		board.setPawn(Color.black, 0);
		boolean actual = board.isEmptyColumn(0);
		
		assertThat("Is empty test with non empty column failed.", actual, is(false));
	}
	
	@Test
	public void isEmptyColumnEmptyTest(){
		Board board = new Board();
		
		boolean actual = board.isEmptyColumn(0);
		
		assertThat("Is empty test with empty column failed.", actual, is(true));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void popAtColumnInvalidPositiveIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.popAtColumn(Board.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void popAtColumnInvalidNegativeIndex(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.popAtColumn(-1);
	}
	
	@Test
	public void popAtColumnBlackTest(){
		Board board = new Board();
		
		board.setPawn(Color.black, 0);
		board.setPawn(Color.black, 0);
		board.setPawn(Color.black, 0);
		Color actual = board.popAtColumn(0);
		
		assertThat("Pop at column, Black, failed.", actual, is(Color.black));
	}
	
	@Test
	public void popAtColumnWhiteTest(){
		Board board = new Board();
		
		board.setPawn(Color.white, 0);
		board.setPawn(Color.white, 0);
		board.setPawn(Color.white, 0);
		Color actual = board.popAtColumn(0);
		
		assertThat("Pop at column, white, failed.", actual, is(Color.white));
	}
	
	@Test
	public void popAtColumnEmptyTest(){
		Board board = new Board();
		
		Color actual = board.popAtColumn(0);
		
		assertNull("Pop at column, Null, failed.", actual);
	}
	
	@Test
	public void simpleEqualBoardTest(){
		Board actaul = new Board();
		Board expected = new Board();
		
		assertEquals("Boards are not equal test. simple test failed.", expected, actaul);
	}
	
	@Test
	public void simpleValidBlackPawnEqualBoardTest(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Color.black, 0);
		expected.setPawn(Color.black, 0);
		
		assertEquals("Boards are not equal test. simple Valid Black Pawn Equal test failed.", expected, actaul);
	}
	
	@Test
	public void simpleValidWhitePawnEqualBoardTest(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Color.white, 1); 
		expected.setPawn(Color.white, 1);
		
		assertEquals("Boards are not equal test. simple Valid White Pawn Equal test failed.", expected, actaul);
	}
	
	@Test
	public void equalBoardNotEqualTest(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Color.white, 0);
		expected.setPawn(Color.black, 0);
		
		assertNotEquals("Boards are equal test. not equal test.", expected, actaul);
	}
	
	@Test
	public void equalBoardDifferentBoardsTest(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Color.white, 0);
		actaul.setPawn(Color.white, 0);
		expected.setPawn(Color.black, 0);
		
		assertNotEquals("Boards are equal test. different boards test.", expected, actaul);
	}
	
	@Test
	public void equalBoardSamePawnDifferentSizeTest6(){
		Board actaul = new Board();
		Board expected = new Board();
		
		actaul.setPawn(Color.white, 0);
		actaul.setPawn(Color.white, 0);
		expected.setPawn(Color.white, 0);
		
		assertNotEquals("Boards are equal test. Same Pawn Different Size test.", expected, actaul);
	}
	
	@Test
	public void boardConstructorTest(){
		Board someBoard = new Board();
		
		someBoard.setPawn(Color.black, 0);
		Board actual = new Board(someBoard);
		Board expected = new Board();
		expected.setPawn(Color.black, 0);
		
		assertNotSame("These Borad objects are the same.", expected, actual);
		assertEquals("Board constructor failure.", expected, actual);
	}
	
	@Test(expected=NullPointerException.class)
	public void boardConstructorNullTest(){
		new Board(null);
	}
}
