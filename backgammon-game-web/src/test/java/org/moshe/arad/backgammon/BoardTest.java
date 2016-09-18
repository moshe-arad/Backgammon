package org.moshe.arad.backgammon;

/*
public class BoardTest {

	@Test
	public void setNullPawnOnBoardTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		boolean actaul = board.setPawn(null, 0);
		
		assertFalse("null pawn test failed.", actaul);
	}
	
	@Test
	public void setNegativeIndexPawnOnBoardTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		boolean actual = board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), -1);
		
		assertFalse("negative index pawn test failed.", actual);
	}
	
	@Test
	public void setIllegalPositiveIndexPawnOnBoardTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		boolean actual = board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 24);
		
		assertFalse("positive index pawn test failed.", actual);
	}
	
	@Test
	public void setPawnOnDifferentKindOfPawnsColumnTest(){
		BackgammonBoard borad = new BackgammonBoard();
		
		boolean firstPawnSetting = borad.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertTrue("Setting first pawn on different kind of pawns column fialed.", firstPawnSetting);
		
		boolean actual = borad.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		
		assertFalse("Setting second pawn on different kind of pawns column fialed.", actual);
	}
	
	@Test
	public void setPawnOnFullColumn(){
		BackgammonBoard board = new BackgammonBoard();
		
		for(int i=0; i<BackgammonBoard.MAX_COLUMN; i++)
			board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		boolean actual = board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertFalse("Set pawn in full column failed.", actual);
	}
	
	@Test
	public void setPawnValidTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		boolean actual = board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 1);
		
		assertTrue("Valid pawn Setting failed.", actual);
		
		actual = board.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 1);
		
		assertTrue("Valid pawn Setting failed.", actual);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void peekAtColumnInvalidPositiveIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.peekAtColumn(BackgammonBoard.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void peekAtColumnInvalidNegativeIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.peekAtColumn(-1);
	}
	
	@Test
	public void peekAtEmptyColumnTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		BackgammonPawn actual = board.peekAtColumn(0);
		
		assertNull("Peek at EMPTY column failed.", actual);
	}
	
	@Test
	public void peekAtColumnTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		Color actual = board.peekAtColumn(0).getColor();
		
		assertThat("Peek at column failed.", actual, is(Color.black));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void sizeOfColumnInvalidPositiveIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.getSizeOfColumn(BackgammonBoard.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void sizeOfColumnInvalidNegativeIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.getSizeOfColumn(-1);
	}
	
	@Test
	public void sizeOfColumnTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		int actual = board.getSizeOfColumn(0);
		
		assertThat("Size of column failed.", actual, is(3));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void isEmptyColumnInvalidPositiveIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.isEmptyColumn(BackgammonBoard.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void isEmptyColumnInvalidNegativeIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.isEmptyColumn(-1);
	}
	
	@Test
	public void isEmptyColumnNotEmptyTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		boolean actual = board.isEmptyColumn(0);
		
		assertThat("Is empty test with non empty column failed.", actual, is(false));
	}
	
	@Test
	public void isEmptyColumnEmptyTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		boolean actual = board.isEmptyColumn(0);
		
		assertThat("Is empty test with empty column failed.", actual, is(true));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void popAtColumnInvalidPositiveIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.popAtColumn(BackgammonBoard.LENGTH);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void popAtColumnInvalidNegativeIndex(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.popAtColumn(-1);
	}
	
	@Test
	public void popAtColumnBlackTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		Color actual = board.popAtColumn(0).getColor();
		
		assertThat("Pop at column, Black, failed.", actual, is(Color.black));
	}
	
	@Test
	public void popAtColumnWhiteTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		board.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		board.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		Color actual = board.popAtColumn(0).getColor();
		
		assertThat("Pop at column, white, failed.", actual, is(Color.white));
	}
	
	@Test
	public void popAtColumnEmptyTest(){
		BackgammonBoard board = new BackgammonBoard();
		
		BackgammonPawn actual = board.popAtColumn(0);
		
		assertNull("Pop at column, Null, failed.", actual);
	}
	
	@Test
	public void simpleEqualBoardTest(){
		BackgammonBoard actaul = new BackgammonBoard();
		BackgammonBoard expected = new BackgammonBoard();
		
		assertEquals("Boards are not equal test. simple test failed.", expected, actaul);
	}
	
	@Test
	public void simpleValidBlackPawnEqualBoardTest(){
		BackgammonBoard actaul = new BackgammonBoard();
		BackgammonBoard expected = new BackgammonBoard();
		
		actaul.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		expected.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertEquals("Boards are not equal test. simple Valid Black Pawn Equal test failed.", expected, actaul);
	}
	
	@Test
	public void simpleValidWhitePawnEqualBoardTest(){
		BackgammonBoard actaul = new BackgammonBoard();
		BackgammonBoard expected = new BackgammonBoard();
		
		actaul.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 1); 
		expected.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 1);
		
		assertEquals("Boards are not equal test. simple Valid White Pawn Equal test failed.", expected, actaul);
	}
	
	@Test
	public void equalBoardNotEqualTest(){
		BackgammonBoard actaul = new BackgammonBoard();
		BackgammonBoard expected = new BackgammonBoard();
		
		actaul.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		expected.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertNotEquals("Boards are equal test. not equal test.", expected, actaul);
	}
	
	@Test
	public void equalBoardDifferentBoardsTest(){
		BackgammonBoard actaul = new BackgammonBoard();
		BackgammonBoard expected = new BackgammonBoard();
		
		actaul.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		actaul.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		expected.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertNotEquals("Boards are equal test. different boards test.", expected, actaul);
	}
	
	@Test
	public void equalBoardSamePawnDifferentSizeTest6(){
		BackgammonBoard actaul = new BackgammonBoard();
		BackgammonBoard expected = new BackgammonBoard();
		
		actaul.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		actaul.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		expected.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		
		assertNotEquals("Boards are equal test. Same Pawn Different Size test.", expected, actaul);
	}
	
	@Test
	public void boardConstructorTest(){
		BackgammonBoard someBoard = new BackgammonBoard();
		
		someBoard.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		BackgammonBoard actual = new BackgammonBoard(someBoard);
		BackgammonBoard expected = new BackgammonBoard();
		expected.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		
		assertNotSame("These Borad objects are the same.", expected, actual);
		assertEquals("Board constructor failure.", expected, actual);
	}
	
	@Test(expected=NullPointerException.class)
	public void boardConstructorNullTest(){
		new BackgammonBoard(null);
	}
	
	@Test
	public void clearBoardTest(){
		BackgammonBoard someBoard = new BackgammonBoard();
		someBoard.initBoard();
		someBoard.clearBoard();
		for(int i=0; i<BackgammonBoard.LENGTH; i++){
			assertTrue("clear board test failed.", someBoard.isEmptyColumn(i));
		}
	}
	
	@Test
	public void isHasColorBlackInvalidTest(){
		BackgammonBoard someBoard = new BackgammonBoard();
		someBoard.setPawn(new BackgammonPawn(Color.white.getInnerValue()), 0);
		assertFalse(someBoard.isHasColor(Color.black));
	}
	
	@Test
	public void isHasColorBlackValidTest(){
		BackgammonBoard someBoard = new BackgammonBoard();
		someBoard.setPawn(new BackgammonPawn(Color.black.getInnerValue()), 0);
		assertTrue(someBoard.isHasColor(Color.black));
	}
}
*/