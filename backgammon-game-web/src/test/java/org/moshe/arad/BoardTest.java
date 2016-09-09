package org.moshe.arad;

import org.junit.Test;
import org.moshe.arad.backgammon.Board;
import org.moshe.arad.backgammon.Pawn;
import static org.junit.Assert.*;

public class BoardTest {

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
