package org.moshe.arad.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
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
@ContextConfiguration("classpath:backgammon-context-test.xml")
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
	@SuppressWarnings("unused")
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
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
		board.setPawn(pawn3, new BackgammonBoardLocation(21));
		board.setPawn(pawn4, new BackgammonBoardLocation(21));
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
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
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
		board.setPawn(pawn1, new BackgammonBoardLocation(19));
		board.setPawn(pawn2, new BackgammonBoardLocation(19));
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
		board.setPawn(pawn1, new BackgammonBoardLocation(2));
		board.setPawn(pawn2, new BackgammonBoardLocation(2));
		board.setPawn(pawn3, new BackgammonBoardLocation(4));
		board.setPawn(pawn4, new BackgammonBoardLocation(4));
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
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
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
		board.setPawn(pawn1, new BackgammonBoardLocation(4));
		board.setPawn(pawn2, new BackgammonBoardLocation(4));
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
	public void isValidMoveWhitePlayerIndexesInBoard() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		assertTrue(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardThereIsNoPawnFrom() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardIncorrectMoveDirection() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(20));
		Move move = new Move(new BackgammonBoardLocation(20), new BackgammonBoardLocation(23));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardToIndexIsOut() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardNoDiceMatch() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(4);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardBlackPlayer() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		secondPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		assertFalse(board.isValidMove(secondPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardToIndexIsBlocked() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(20));
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(20));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardEatBlack() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(20));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		assertTrue(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerIndexesInBoardThereIsEatenWhite() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		board.addWhitePawnToEatenQueue(testContext.getBean(WhiteBackgammonPawn.class));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerToIndexIsOut() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(4));
		Move move = new Move(new BackgammonBoardLocation(4), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		assertTrue(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerToIndexIsOutNotExactDiceValue() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(6);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(4));
		Move move = new Move(new BackgammonBoardLocation(4), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		assertTrue(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerToIndexIsOutNotExactDiceValue2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(6);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(4));
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(5));
		Move move = new Move(new BackgammonBoardLocation(4), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerToIndexIsOutNotExactDiceValue3() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(4));
		Move move = new Move(new BackgammonBoardLocation(4), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerToIndexIsOutCanNotTakeOut() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(4));
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		Move move = new Move(new BackgammonBoardLocation(4), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		assertFalse(board.isValidMove(firstPlayer, move));
	}
	
	@Test
	public void isValidMoveWhitePlayerFromIndexIsEaten() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		Move move = new Move(new BackgammonBoardLocation(BackgammonBoard.EATEN_WHITE), new BackgammonBoardLocation(19));
		board.addWhitePawnToEatenQueue(testContext.getBean(WhiteBackgammonPawn.class));
		assertTrue(board.isValidMove(firstPlayer, move));
	}
	
	@Test(expected=Exception.class)
	public void executeMovePlayerIsNull() throws Exception{
		board.executeMove(null, new Move());
	}
	
	@Test(expected=Exception.class)
	public void executeMoveMoveIsNull() throws Exception{
		board.executeMove(firstPlayer, null);
	}
	
	@Test
	public void executeMovePlayerWhiteIndexesInsideBoard() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		board.executeMove(firstPlayer, move);
		assertEquals(0,board.getSizeOfColumn(new BackgammonBoardLocation(23)));
		assertEquals(1,board.getSizeOfColumn(new BackgammonBoardLocation(20)));
		assertEquals(0, board.getWhiteEatenSize());
		assertEquals(0, board.getBlackEatenSize());
	}
	
	@Test
	public void executeMovePlayerWhiteIndexesInsideBoardDestNotEmpty1() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(20));
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(20));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		board.executeMove(firstPlayer, move);
		assertEquals(0,board.getSizeOfColumn(new BackgammonBoardLocation(23)));
		assertEquals(3,board.getSizeOfColumn(new BackgammonBoardLocation(20)));
		assertEquals(0, board.getWhiteEatenSize());
		assertEquals(0, board.getBlackEatenSize());
	}
	
	@Test
	public void executeMovePlayerWhiteIndexesInsideBoardDestNotEmpty2() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(20));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		board.executeMove(firstPlayer, move);
		assertEquals(0,board.getSizeOfColumn(new BackgammonBoardLocation(23)));
		assertEquals(2,board.getSizeOfColumn(new BackgammonBoardLocation(20)));
		assertEquals(0, board.getWhiteEatenSize());
		assertEquals(0, board.getBlackEatenSize());
	}
	
	@Test
	public void executeMoveWhitePlayerIndexesInBoardEatBlack() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(23));
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(20));
		Move move = new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(20));
		board.executeMove(firstPlayer, move);
		assertEquals(0, board.getSizeOfColumn(new BackgammonBoardLocation(23)));
		assertEquals(1, board.getSizeOfColumn(new BackgammonBoardLocation(20)));
		assertEquals(testContext.getBean(WhiteBackgammonPawn.class), board.peekAtColumn(new BackgammonBoardLocation(20)));
		assertEquals(1, board.getBlackEatenSize());
		assertEquals(0, board.getWhiteEatenSize());
	}
	
	@Test
	public void executeMoveWhitePlayerToIndexIsOut() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(4));
		Move move = new Move(new BackgammonBoardLocation(4), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		board.executeMove(firstPlayer, move);
		assertEquals(0, board.getSizeOfColumn(new BackgammonBoardLocation(4)));
		assertEquals(0, board.getWhiteEatenSize());
		assertEquals(0, board.getBlackEatenSize());
	}
	
	@Test
	public void executeMoveWhitePlayerToIndexIsOutNotExactDiceValue() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(6);
		
		board.setPawn(testContext.getBean(WhiteBackgammonPawn.class), new BackgammonBoardLocation(4));
		Move move = new Move(new BackgammonBoardLocation(4), new BackgammonBoardLocation(BackgammonBoard.OUT_WHITE));
		board.executeMove(firstPlayer, move);
		assertEquals(0, board.getSizeOfColumn(new BackgammonBoardLocation(4)));
		assertEquals(0, board.getWhiteEatenSize());
		assertEquals(0, board.getBlackEatenSize());
	}
	
	@Test
	public void executeMoveWhitePlayerFromIndexIsEaten() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		Move move = new Move(new BackgammonBoardLocation(BackgammonBoard.EATEN_WHITE), new BackgammonBoardLocation(19));
		board.addWhitePawnToEatenQueue(testContext.getBean(WhiteBackgammonPawn.class));
		board.executeMove(firstPlayer, move);
		assertEquals(1, board.getSizeOfColumn(new BackgammonBoardLocation(19)));
		assertEquals(testContext.getBean(WhiteBackgammonPawn.class), board.peekAtColumn(new BackgammonBoardLocation(19)));
		assertEquals(0, board.getWhiteEatenSize());
		assertEquals(0, board.getBlackEatenSize());
	}
	
	@Test(expected=Exception.class)
	public void isWinnerPlayerIsNull() throws Exception{
		board.isWinner(null);
	}
	
	@Test
	public void isWinnerWhitePlayerWinner() throws Exception{
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(0));
		assertTrue(board.isWinner(firstPlayer));
		assertFalse(board.isWinner(secondPlayer));
	}
	
	@Test
	public void isHasMoreMoves() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		firstPlayer.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(6);
		when(secondDiceMock.getValue()).thenReturn(6);
	
		
		board.addWhitePawnToEatenQueue(testContext.getBean(WhiteBackgammonPawn.class));
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(18));
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(18));
		board.setPawn(testContext.getBean(BlackBackgammonPawn.class), new BackgammonBoardLocation(18));
		
		board.display();
		
		assertFalse(board.isHasMoreMoves(firstPlayer));
	}
}
