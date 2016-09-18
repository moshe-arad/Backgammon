package org.moshe.arad.after_refactor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.game.instrument.BackgammonBoard;
import org.moshe.arad.game.instrument.BackgammonDice;
import org.moshe.arad.game.instrument.BlackBackgammonPawn;
import org.moshe.arad.game.instrument.WhiteBackgammonPawn;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test2.xml")
public class BackgammonBoardTest {

	@Autowired
	ApplicationContext testContext;
	@Autowired
	BackgammonBoard board;
	@Resource
	Player firstPlayer;
	@Resource
	Player secondPlayer;
	@Autowired
	BackgammonTurn turn;
	
	@After
	public void cleanup(){
		firstPlayer.setTurn(turn);
		secondPlayer.setTurn(null);
		board.clearBoard();
		board.clearPawnsOutsideGame();
	}
	
	@Test(expected=Exception.class)
	public void copyConstructorNull() throws Exception{
		BackgammonBoard copyBoard = new BackgammonBoard(null);
		
	}
	
	@Test
	public void copyConstructorValid() throws Exception{
		WhiteBackgammonPawn pawn = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn, new BackgammonBoardLocation(0));
		BackgammonBoard copyBoard = new BackgammonBoard(board);
		assertEquals(board, copyBoard);
	}
	
	@Test
	public void copyConstructorInvalid() throws Exception{
		WhiteBackgammonPawn pawn = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn, new BackgammonBoardLocation(0));
		BackgammonBoard copyBoard = new BackgammonBoard(board);
		board.initBoard();
		assertNotEquals(board, copyBoard);
	}
	
	@Test(expected=Exception.class)
	public void isHasMoreMovesNullPlayer() throws Exception{
		board.isHasMoreMoves(null);
	}
	
	@Test
	public void isHasMoreMovesBothDicesNoneValue() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		assertFalse(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerBothDicesAreNotNoneHaveEatenCanNotGoBackToGame() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn3 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn4 = testContext.getBean(BlackBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		board.setPawn(pawn3, new BackgammonBoardLocation(2));
		board.setPawn(pawn4, new BackgammonBoardLocation(2));
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertFalse(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerBothDicesAreNotNoneHaveEatenCanGoBackToGame1() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		
		board.addWhitePawnToEatenQueue(testContext.getBean(WhiteBackgammonPawn.class));
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerBothDicesAreNotNoneHaveEatenCanGoBackToGame2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		
		board.addWhitePawnToEatenQueue(testContext.getBean(WhiteBackgammonPawn.class));
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerBothDicesAreNotNoneHaveEatenCanGoBackToGame3() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		
		board.addWhitePawnToEatenQueue(testContext.getBean(WhiteBackgammonPawn.class));
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerFirstDiceIsNotNoneHaveEatenCanNotGoBackToGame() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertFalse(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerFirstDiceIsNotNoneHaveEatenCanGoBackToGame1() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerFirstDiceIsNotNoneHaveEatenCanGoBackToGame2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerFirstDiceIsNotNoneHaveEatenCanGoBackToGame3() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerSecondDiceIsNotNoneHaveEatenCanNotGoBackToGame() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertFalse(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerSecondDiceIsNotNoneHaveEatenCanGoBackToGame1() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerSecondDiceIsNotNoneHaveEatenCanGoBackToGame2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerSecondDiceIsNotNoneHaveEatenCanGoBackToGame3() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		WhiteBackgammonPawn pawn5 = testContext.getBean(WhiteBackgammonPawn.class);
		board.addWhitePawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	@Test
	public void isHasMoreMovesWhitePlayerDicesIsNotNoneDoNotHaveEaten() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		assertTrue(board.isHasMoreMoves(firstPlayer));
	}
	
	//Black player
	
	@Test
	public void isHasMoreMovesBlackPlayerBothDicesAreNotNoneHaveEatenCanNotGoBackToGame() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn3 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn4 = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		board.setPawn(pawn3, new BackgammonBoardLocation(21));
		board.setPawn(pawn4, new BackgammonBoardLocation(21));
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertFalse(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerBothDicesAreNotNoneHaveEatenCanGoBackToGame1() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		
		board.addBlackPawnToEatenQueue(testContext.getBean(BlackBackgammonPawn.class));
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerBothDicesAreNotNoneHaveEatenCanGoBackToGame2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		
		board.addBlackPawnToEatenQueue(testContext.getBean(BlackBackgammonPawn.class));
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerBothDicesAreNotNoneHaveEatenCanGoBackToGame3() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		
		board.addBlackPawnToEatenQueue(testContext.getBean(BlackBackgammonPawn.class));
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerFirstDiceIsNotNoneHaveEatenCanNotGoBackToGame() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertFalse(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerFirstDiceIsNotNoneHaveEatenCanGoBackToGame1() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerFirstDiceIsNotNoneHaveEatenCanGoBackToGame2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerFirstDiceIsNotNoneHaveEatenCanGoBackToGame3() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerSecondDiceIsNotNoneHaveEatenCanNotGoBackToGame() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		WhiteBackgammonPawn pawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertFalse(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerSecondDiceIsNotNoneHaveEatenCanGoBackToGame1() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		BlackBackgammonPawn pawn1 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn pawn2 = testContext.getBean(BlackBackgammonPawn.class);
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerSecondDiceIsNotNoneHaveEatenCanGoBackToGame2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		WhiteBackgammonPawn pawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerSecondDiceIsNotNoneHaveEatenCanGoBackToGame3() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(BackgammonDice.NONE);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		BlackBackgammonPawn pawn5 = testContext.getBean(BlackBackgammonPawn.class);
		board.addBlackPawnToEatenQueue(pawn5);
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	@Test
	public void isHasMoreMovesBlackPlayerDicesIsNotNoneDoNotHaveEaten() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		assertTrue(board.isHasMoreMoves(secondPlayer));
	}
	
	// is Valid Move
	
	@Test(expected=Exception.class)
	public void isValidMovePlayerIsNull() throws Exception{
		board.isValidMove(null, new Move());
	}
	
	@Test(expected=Exception.class)
	public void isValidMoveMoveIsNull() throws Exception{
		board.isValidMove(firstPlayer, null);
	}
	

	@Test
	public void isValidMoveWhitePlayerEmptyBoard() throws Exception{
		Move move = new Move(new BackgammonBoardLocation(22), new BackgammonBoardLocation(19));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerOppositeDirectionMove() throws Exception{
		WhiteBackgammonPawn pawnMock = mock(WhiteBackgammonPawn.class);
		board.setPawn(pawnMock, new BackgammonBoardLocation(19));
		Move move = new Move(new BackgammonBoardLocation(19), new BackgammonBoardLocation(22));
		assertFalse(board.isValidMove(firstPlayer, move));
		verify(pawnMock).isAbleToDo(move);
	}
	
	@Test
	public void isValidMoveWhitePlayerSameColumnsMove() throws Exception{
		WhiteBackgammonPawn pawnMock = mock(WhiteBackgammonPawn.class);
		board.setPawn(pawnMock, new BackgammonBoardLocation(19));
		Move move = new Move(new BackgammonBoardLocation(19), new BackgammonBoardLocation(19));
		assertFalse(board.isValidMove(firstPlayer, move));
		verify(pawnMock).isAbleToDo(move);
	}
}
