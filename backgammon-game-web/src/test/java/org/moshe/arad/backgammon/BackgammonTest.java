package org.moshe.arad.backgammon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Scanner;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Color;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.instrument.Pawn;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.Turn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.portlet.MockActionRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test.xml")
public class BackgammonTest {

	@Autowired
	Backgammon backgammon;
	@Autowired
	Board board;
	@Resource
	Pawn blackPawn;
	@Resource
	Pawn whitePawn;
	@Resource
	Player whitePawnPlayer;
	@Resource
	Player blackPawnPlayer;
	
	@Before
	public void setup(){
		board.clearBoard();
		assertNotNull("Board object is null. can't run tests.", board);
		assertNotNull("Pawn object is null. can't run tests.", blackPawn);
	}
	
	@Test
	public void isHasWinnerOnlyBlackOnBoardWhiteIsWinnerTest(){
		boolean pawnSet = board.setPawn(blackPawn, 0);
		assertTrue("Pawn set failed. is has winner with only black pawns failed." ,pawnSet);
		boolean actual = backgammon.isHasWinner();
		assertTrue("Is Has Winner Only Black On Board White Is Winner test is failed.", actual);
	}
	
	@Test
	public void isHasWinnerOnlyWhiteOnBoardBlackIsWinnerTest(){
		boolean pawnSet = board.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is has winner with only white pawns failed." ,pawnSet);
		boolean actual = backgammon.isHasWinner();
		assertTrue("Is Has Winner Only White On Board Black Is Winner test is failed.", actual);
	}
	
	@Test
	public void isHasWinnerDoesNotHaveWinnerBothColorsTest(){
		boolean pawnSet = board.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is has winner Does Not Have Winner Both Colors failed first." ,pawnSet);
		pawnSet = board.setPawn(blackPawn, 1);
		assertTrue("Pawn set failed. is has winner Does Not Have Winner Both Colors failed second." ,pawnSet);
		boolean actual = backgammon.isHasWinner();
		assertFalse("is has winner Does Not Have Winner Both Colors failed. actual test.", actual);
	}
	
	@Test
	public void isHasWinnerEmptyBoardTest(){
		boolean actual = backgammon.isHasWinner();
		assertFalse("is has winner empty board failed.", actual);
	}
	
	@Test
	public void isWinnerWhitePawnBlackIsWinnerTest(){
		boolean pawnSet = board.setPawn(whitePawn, 0);
		assertTrue("Is winner test, black is winner, white pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(blackPawnPlayer, board);
		assertTrue("Is winner test, black is winner failed.", actual);
	}
	
	@Test
	public void isWinnerWhitePawnWhiteIsNotWinnerTest(){
		boolean pawnSet = board.setPawn(whitePawn, 0);
		assertTrue("Is winner test, white pawn, white is not winner, white pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(whitePawnPlayer, board);
		assertFalse("Is winner test, white pawn, white is not winner failed.", actual);
	}
	
	@Test
	public void isWinnerBlackPawnWhiteIsWinnerTest(){
		boolean pawnSet = board.setPawn(blackPawn, 0);
		assertTrue("Is winner test, black pawn, black is winner, black pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(whitePawnPlayer, board);
		assertTrue("Is winner test, black pawn, white is winner failed.", actual);
	}
	
	@Test
	public void isWinnerBlackPawnBlackIsNotWinnerTest(){
		boolean pawnSet = board.setPawn(blackPawn, 0);
		assertTrue("Is winner test, black pawn, black is not winner, black pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(blackPawnPlayer, board);
		assertFalse("Is winner test, black pawn, black is not winner failed.", actual);
	}
	
	@Test
	public void isWinnerEmptyBoardBlackPawn(){
		boolean actual = backgammon.isWinner(blackPawnPlayer, board);
		assertFalse("Is winner test, empty board check black pawn winner failed.", actual);
	}
	
	@Test
	public void isWinnerEmptyBoardWhitePawn(){
		boolean actual = backgammon.isWinner(whitePawnPlayer, board);
		assertFalse("Is winner test, empty board check white pawn winner failed.", actual);
	}
	
	@Test
	public void isWinnerBlackPawnDoesNotHaveWinnerBothColorsTest(){
		boolean pawnSet = board.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is Winner Black Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		pawnSet = board.setPawn(blackPawn, 1);
		assertTrue("Pawn set failed. is Winner Black Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		boolean actual = backgammon.isWinner(blackPawnPlayer, board);
		assertFalse("is Winner Black Pawn Does Not Have Winner Both Colors Test.", actual);
	}
	
	@Test
	public void isWinnerWhitePawnDoesNotHaveWinnerBothColorsTest(){
		boolean pawnSet = board.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is Winner White Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		pawnSet = board.setPawn(blackPawn, 1);
		assertTrue("Pawn set failed. is Winner White Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		boolean actual = backgammon.isWinner(whitePawnPlayer, board);
		assertFalse("is Winner White Pawn Does Not Have Winner Both Colors Test.", actual);
	}
	
	@Test
	public void isWinnerPlayerIsNullTest(){
		boolean actual = backgammon.isWinner(null, board);
		assertFalse("Is Winner, player is null, failed. ", actual);
	}
	
	@Test
	public void isWinnerBoardIsNullTest(){
		boolean actual = backgammon.isWinner(blackPawnPlayer, null);
		assertFalse("Is Winner, player is null, failed. ", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerIsNullTest(){
		boolean actual = backgammon.isHasMoreMoves(null);
		assertFalse("Is has more moves, player is null, failed.", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerWithoutTurnTest(){
		Player withoutTurn = backgammon.howIsNextInTurn();
		boolean actual = backgammon.isHasMoreMoves(withoutTurn);
		assertFalse("Is has more moves, player without turn, failed.", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerWithTurnDoesNotHaveMoreMovesTest(){
		Player withTurn = backgammon.howHasTurn();
		withTurn.getTurn().getFirstDice().initDiceValue();
		withTurn.getTurn().getSecondDice().initDiceValue();
		boolean actual = backgammon.isHasMoreMoves(withTurn);
		assertFalse("Is has more moves, player with turn & player does not have more moves, failed.", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerWithTurnHasMoreMovesTest(){
		Player withTurn = backgammon.howHasTurn();
		withTurn.getTurn().getFirstDice().rollDice();
		withTurn.getTurn().getSecondDice().rollDice();
		boolean actual = backgammon.isHasMoreMoves(withTurn);
		assertTrue("Is has more moves, player with turn & player has more moves, failed.", actual);
	}
	
	@Test
	public void enterNextMovePlayerIsNullTest(){
		Move actual = backgammon.enterNextMove(null, new Scanner(System.in));
		assertNull("Enter next move, player is null, failed.", actual);
	}
	
//	@Test
//	public void enterNextMoveTest(){
//		Scanner readerMock = mock(Scanner.class);
//		
//		when(readerMock.next()).thenReturn("23").thenReturn("20");
//		Move actual = backgammon.enterNextMove(whitePawnPlayer, readerMock);
//		assertEquals("Enter next move test failed. move from.", 23,actual.getFrom());
//		assertEquals("Enter next move test failed. move to.", 20,actual.getTo());
//	}
	
	@Test(expected=NullPointerException.class)
	public void rollDicesPlayerIsNullTest(){
		backgammon.rollDices(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void rollDicesPlayerWithoutTurnTest(){
		Player withouTurn = backgammon.howIsNextInTurn();
		backgammon.rollDices(withouTurn);
	}
	
	@Test
	public void rollDicesPlayerWithTurnTest(){
		Player withTurn = backgammon.howHasTurn();
		withTurn.getTurn().getFirstDice().initDiceValue();
		assertEquals("First dice init failed.", 0, withTurn.getTurn().getFirstDice().getValue());
		withTurn.getTurn().getSecondDice().initDiceValue();
		assertEquals("Second dice init failed.", 0, withTurn.getTurn().getSecondDice().getValue());
		backgammon.rollDices(withTurn);
		assertNotEquals("First dice roll failed.", 0, withTurn.getTurn().getFirstDice().getValue());
		assertNotEquals("Second dice roll failed.", 0, withTurn.getTurn().getSecondDice().getValue());
	}
	
	@Test
	public void makeMovePlayerIsNullTest(){
		boolean actual = backgammon.makeMove(null, new Move(), board);
		assertFalse("Make move, player is null failed.", actual);
	}
	
	@Test
	public void makeMoveMoveIsNullTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, null, board);
		assertFalse("Make move, move is null failed.", actual);
	}
	
	@Test
	public void makeMoveBoardIsNullTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(), null);
		assertFalse("Make move, board is null failed.", actual);
	}
	
	@Test
	public void makeMoveEmptyBoardTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), board);
		assertFalse("Make move, empty board is failed.", actual);
	}
	
	@Test
	public void makeMoveInvalidTest(){
		boolean pawnSet = board.setPawn(blackPawn, 1);
		assertTrue(pawnSet);
		pawnSet = board.setPawn(whitePawn, 4);
		assertTrue(pawnSet);
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), board);
		assertFalse("Make move, invalid is failed.", actual);
	}
	
	@Test
	public void makeMoveValidNonEmptyTest(){
		boolean pawnSet = board.setPawn(blackPawn, 1);
		assertTrue(pawnSet);
		pawnSet = board.setPawn(new Pawn(Color.black.getInnerValue()), 4);
		assertTrue(pawnSet);
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), board);
		assertTrue("Make move, valid test non empty failed.", actual);
	}
	
	@Test
	public void makeMoveValidEmptyTest(){
		boolean pawnSet = board.setPawn(blackPawn, 1);
		assertTrue(pawnSet);
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), board);
		assertTrue("Make move, valid test empty failed.", actual);
	}
	
	@Test
	public void makeMoveRemoveWhiteCleanUpTest(){
		assertTrue(board.setPawn(whitePawn, 4));
		boolean actual = backgammon.makeMove(whitePawnPlayer, new Move(4, -1), board);
		assertTrue("make Move Remove White CleanUp Test", actual);
		assertTrue(board.isEmptyColumn(4));
	}
	
	@Test
	public void makeMoveRemoveWhiteCleanUpEmptyBoardTest(){
		boolean actual = backgammon.makeMove(whitePawnPlayer, new Move(4, -1), board);
		assertFalse("make Move Remove White CleanUp Empty Board Test", actual);
	}
	
	@Test
	public void makeMoveRemoveBlackCleanUpTest(){
		assertTrue(board.setPawn(blackPawn, 20));
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(20, 24), board);
		assertTrue("make Move Remove black CleanUp Test", actual);
		assertTrue(board.isEmptyColumn(20));
	}
	
	@Test
	public void makeMoveRemoveBlackCleanUpEmptyBoardTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(20, 24), board);
		assertFalse("make Move Remove black CleanUp Empty Board Test", actual);
	}
	
	@Test
	public void validMovePlayerIsNullTest(){
		boolean actual = backgammon.validMove(null, new Move(), board);
		assertFalse("valid Move, player is null failed.", actual);
	}
	
	@Test
	public void validMoveMoveIsNullTest(){
		boolean actual = backgammon.validMove(blackPawnPlayer, null, board);
		assertFalse("valid Move, move is null failed.", actual);
	}
	
	@Test
	public void validMoveBoardIsNullTest(){
		boolean actual = backgammon.validMove(blackPawnPlayer, new Move(), null);
		assertFalse("valid Move, board is null failed.", actual);
	}
	
	@Test
	public void validMoveWhitePawnToMinusFromNegativeTest(){
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), board);
		assertFalse("valid Move, white pawn to minus from negative failed.", actual);
	}
	
	@Test
	public void validMoveBlackPawnFromMinusToNegativeTest(){
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), board);
		assertFalse("valid Move, black pawn from minus to negative failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnEmptyBoardTest(){
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), board);
		assertFalse("valid Move, white pawn empty board failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnEmptyBoardTest(){
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), board);
		assertFalse("valid Move, black pawn empty pawn failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnFromDifferentColorBoardTest(){
		assertTrue(board.setPawn(blackPawn, 1));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(whitePawnPlayer, new Move(1,4), board);
		assertFalse("valid Move, white pawn from different color failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnFromDifferentColorTest(){
		assertTrue(board.setPawn(whitePawn, 1));
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		Turn turnMock = mock(Turn.class);
		Player playerMock = mock(Player.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(6);
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), board);
		assertFalse("valid Move, black pawn from different color failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnToDifferentColorBoardTest(){
		assertTrue(board.setPawn(blackPawn, 4));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), board);
		assertFalse("valid Move, white pawn to different color failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnToDifferentColorTest(){
		assertTrue(board.setPawn(whitePawn, 1));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), board);
		assertFalse("valid Move, black pawn to different color failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnToIsEmptyTest(){
		assertTrue(board.setPawn(whitePawn, 4));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), board);
		assertTrue("valid Move, white pawn to is empty failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnToIsEmptyTest(){
		assertTrue(board.setPawn(blackPawn, 1));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), board);
		assertTrue("valid Move, black pawn To Is Empty failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnToIsWhiteTest(){
		assertTrue(board.setPawn(whitePawn, 1));
		assertTrue(board.setPawn(whitePawn, 4));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), board);
		assertTrue("valid Move, white pawn to is white failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnToIsBlackTest(){
		assertTrue(board.setPawn(blackPawn, 4));
		assertTrue(board.setPawn(blackPawn, 1));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), board);
		assertTrue("valid Move, black pawn To Is black failed.", actual);
	}
	
	@Test
	public void validMoveDoCleanUpWhiteCanNotCleanUpTest(){
		assertTrue(board.setPawn(whitePawn, 4));
		assertTrue(board.setPawn(new Pawn(Color.white.getInnerValue()), 6));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(4, -1), board);
		assertFalse("valid Move Do CleanUp White Can Not CleanUp Test", actual);
	}
	
	@Test
	public void validMoveDoCleanUpWhiteCanCleanUpTest(){
		assertTrue(board.setPawn(whitePawn, 4));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(3);
		boolean actual = backgammon.validMove(playerMock, new Move(4, -1), board);
		assertTrue("valid Move Do CleanUp White Can CleanUp Test", actual);
	}
	
	@Test
	public void validMoveDoCleanUpBlackCanNotCleanUpTest(){
		assertTrue(board.setPawn(blackPawn, 20));
		assertTrue(board.setPawn(new Pawn(Color.black.getInnerValue()), 10));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(4);
		boolean actual = backgammon.validMove(playerMock, new Move(20, 24), board);
		assertFalse("valid Move Do CleanUp black Can Not CleanUp Test", actual);
	}
	
	@Test
	public void validMoveDoCleanUpBlackCanCleanUpTest(){
		assertTrue(board.setPawn(blackPawn, 20));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = backgammon.validMove(playerMock, new Move(20, 24), board);
		assertTrue("valid Move Do CleanUp black Can CleanUp Test", actual);
	}
	
	@Test
	public void validMoveBlackEatWhiteTest(){
		assertTrue(board.setPawn(whitePawn, 23));
		assertTrue(board.setPawn(blackPawn, 20));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.black);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(1);
		boolean actual = backgammon.validMove(playerMock, new Move(20, 23), board);
		assertTrue("valid Move black eat white Test failed.", actual);
	}
	
	@Test
	public void validMoveWhiteEatBlackTest(){
		assertTrue(board.setPawn(blackPawn, 20));
		assertTrue(board.setPawn(whitePawn, 23));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(1);
		boolean actual = backgammon.validMove(playerMock, new Move(23, 20), board);
		assertTrue("valid Move white eat black Test failed.", actual);
	}
	
	@Test
	public void initDicesPlayerIsNullTest(){
		boolean actual = backgammon.initDices(null, new Move());
		assertFalse("Init dices, player is null test, failed", actual);
	}
	
	@Test
	public void initDicesTurnIsNullTest(){
		boolean actual = backgammon.initDices(backgammon.howIsNextInTurn(), new Move());
		assertFalse("Init dices, turn is null test, failed", actual);
	}
	
	@Test
	public void initDicesFirstDiceIsNullTest(){
		Player playerMock = mock(Player.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		Turn turnMock = mock(Turn.class);
		when(playerMock.getTurn()).thenReturn(turnMock);
		playerMock.setTurn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(null);
		boolean actual = backgammon.initDices(playerMock, new Move());
		assertFalse("Init dices, first dice is null, test failed.", actual);
	}
	
	@Test
	public void initDicesSecondDiceIsNullTest(){
		Player playerMock = mock(Player.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		Turn turnMock = mock(Turn.class);
		when(playerMock.getTurn()).thenReturn(turnMock);
		playerMock.setTurn(turnMock);
		Dice firstDiceMock = mock(Dice.class);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(null);
		boolean actual = backgammon.initDices(playerMock, new Move());
		assertFalse("Init dices, second dice is null, test failed." , actual);
	}
	
	@Test
	public void initDicesMoveStepDoesNotInDicesTest(){
		Player playerMock = mock(Player.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		Turn turnMock = mock(Turn.class);
		when(playerMock.getTurn()).thenReturn(turnMock);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		playerMock.setTurn(turnMock);
		Move move = new Move(23, 19);
		boolean actual = backgammon.initDices(playerMock, move);
		assertFalse("Init dices, move step does not in dices value, failed.", actual);
	}
	
	@Test
	public void initDicesInitFirstDiceTest(){
		Player playerMock = mock(Player.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(1);
		playerMock.setTurn(turnMock);
		when(playerMock.getTurn()).thenReturn(turnMock);
		Move move = new Move(23, 20);
		boolean actual = backgammon.initDices(playerMock, move);
		verify(firstDiceMock).initDiceValue();
		assertTrue("Init dices, init first dice, failed.", actual);
	}
	
	@Test(expected=NullPointerException.class)
	public void isCanStartCleanUpPlayerIsNullTest(){
		backgammon.isCanStartCleanUp(null, board);
	}
	
	@Test(expected=NullPointerException.class)
	public void isCanStartCleanUpBoardIsNullTest(){
		backgammon.isCanStartCleanUp(blackPawnPlayer, null);
	}
	
	@Test
	public void isCanStartCleanUpWhiteCanNotCleanUpTest(){
		assertTrue(board.setPawn(whitePawn, 6));
		assertFalse("is Can Start CleanUp White Can Not CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(whitePawnPlayer, board));
	}
	
	@Test
	public void isCanStartCleanUpWhiteCanCleanUpTest(){
		assertTrue(board.setPawn(whitePawn, 5));
		assertTrue("is Can Start CleanUp White Can CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(whitePawnPlayer, board));
	}
	
	@Test
	public void isCanStartCleanUpBlackCanNotCleanUpTest(){
		assertTrue(board.setPawn(blackPawn, 17));
		assertFalse("is Can Start CleanUp black Can Not CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(blackPawnPlayer, board));
	}
	
	@Test
	public void isCanStartCleanUpBlackCanCleanUpTest(){
		assertTrue(board.setPawn(blackPawn, 18));
		assertTrue("is Can Start CleanUp black Can CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(blackPawnPlayer, board));
	}
}
