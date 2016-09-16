package org.moshe.arad.backgammon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test.xml")
public class BackgammonTest {

	@Resource
	Backgammon backgammon;
	@Resource
	Board simpleBoard;
	@Resource
	Pawn blackPawn;
	@Resource
	Pawn whitePawn;
	@Resource
	Player whitePawnPlayer;
	@Resource
	Player blackPawnPlayer;
	@Autowired
	ApplicationContext backgammonContextTest;
	
	@Before
	public void setup(){
		simpleBoard.clearBoard();
		simpleBoard.clearPawnsOutsideGame();
		assertNotNull("Board object is null. can't run tests.", simpleBoard);
		assertNotNull("Pawn object is null. can't run tests.", blackPawn);
	}
	
	@Test
	public void isHasWinnerOnlyBlackOnBoardWhiteIsWinnerTest(){
		boolean pawnSet = simpleBoard.setPawn(blackPawn, 0);
		assertTrue("Pawn set failed. is has winner with only black pawns failed." ,pawnSet);
		boolean actual = backgammon.isHasWinner();
		assertTrue("Is Has Winner Only Black On Board White Is Winner test is failed.", actual);
	}
	
	@Test
	public void isHasWinnerOnlyWhiteOnBoardBlackIsWinnerTest(){
		boolean pawnSet = simpleBoard.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is has winner with only white pawns failed." ,pawnSet);
		boolean actual = backgammon.isHasWinner();
		assertTrue("Is Has Winner Only White On Board Black Is Winner test is failed.", actual);
	}
	
	@Test
	public void isHasWinnerDoesNotHaveWinnerBothColorsTest(){
		boolean pawnSet = simpleBoard.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is has winner Does Not Have Winner Both Colors failed first." ,pawnSet);
		pawnSet = simpleBoard.setPawn(blackPawn, 1);
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
	public void isHasWinnerBlacksOutsideGameTest(){
		Backgammon nonEmptyBlackGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		boolean actual = nonEmptyBlackGame.isHasWinner();
		assertFalse("Is has winner blacks outside game test failed.", actual);
	}
	
	@Test
	public void isHasWinnerWhitesOutsideGameWithBlackOnBoardTest(){
		Backgammon nonEmptyWhitesGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		nonEmptyWhitesGame.getBoard().setPawn(new Pawn(Color.black.getInnerValue()), 0);
		boolean actual = nonEmptyWhitesGame.isHasWinner();
		assertFalse("Is has winner whites outside game test failed.", actual);
	}
	
	@Test
	public void isHasWinnerWhitesOutsideGameTest(){
		Backgammon nonEmptyWhitesGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		boolean actual = nonEmptyWhitesGame.isHasWinner();
		assertFalse("Is has winner whites outside game test failed.", actual);
	}
	
	@Test
	public void isHasWinnerWhitesAndBlacksOutsideGameTest(){
		Backgammon nonEmptyWhitesGame = backgammonContextTest.getBean("nonEmptyBlackAndWhitesBackgammon", Backgammon.class);
		boolean actual = nonEmptyWhitesGame.isHasWinner();
		assertFalse("Is has winner whites and blacks outside game test failed.", actual);
	}
	
	@Test
	public void isHasWinnerBlacksOutsideGameWithBlackOnBoardTest(){
		Backgammon nonEmptyBlackGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		nonEmptyBlackGame.getBoard().setPawn(new Pawn(Color.black.getInnerValue()), 0);
		boolean actual = nonEmptyBlackGame.isHasWinner();
		assertFalse("Is has winner blacks outside game test failed.", actual);
	}
	
	@Test
	public void isWinnerWhitePawnBlackIsWinnerTest(){
		boolean pawnSet = simpleBoard.setPawn(whitePawn, 0);
		assertTrue("Is winner test, black is winner, white pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(blackPawnPlayer);
		assertTrue("Is winner test, black is winner failed.", actual);
	}
	
	@Test
	public void isWinnerWhitePawnWhiteIsNotWinnerTest(){
		boolean pawnSet = simpleBoard.setPawn(whitePawn, 0);
		assertTrue("Is winner test, white pawn, white is not winner, white pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(whitePawnPlayer);
		assertFalse("Is winner test, white pawn, white is not winner failed.", actual);
	}
	
	@Test
	public void isWinnerBlackPawnWhiteIsWinnerTest(){
		boolean pawnSet = simpleBoard.setPawn(blackPawn, 0);
		assertTrue("Is winner test, black pawn, black is winner, black pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(whitePawnPlayer);
		assertTrue("Is winner test, black pawn, white is winner failed.", actual);
	}
	
	@Test
	public void isWinnerBlackPawnBlackIsNotWinnerTest(){
		boolean pawnSet = simpleBoard.setPawn(blackPawn, 0);
		assertTrue("Is winner test, black pawn, black is not winner, black pawn set failed." ,pawnSet);
		boolean actual = backgammon.isWinner(blackPawnPlayer);
		assertFalse("Is winner test, black pawn, black is not winner failed.", actual);
	}
	
	@Test
	public void isWinnerEmptyBoardBlackPawn(){
		boolean actual = backgammon.isWinner(blackPawnPlayer);
		assertFalse("Is winner test, empty board check black pawn winner failed.", actual);
	}
	
	@Test
	public void isWinnerEmptyBoardWhitePawn(){
		boolean actual = backgammon.isWinner(whitePawnPlayer);
		assertFalse("Is winner test, empty board check white pawn winner failed.", actual);
	}
	
	@Test
	public void isWinnerBlackPawnDoesNotHaveWinnerBothColorsTest(){
		boolean pawnSet = simpleBoard.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is Winner Black Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		pawnSet = simpleBoard.setPawn(blackPawn, 1);
		assertTrue("Pawn set failed. is Winner Black Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		boolean actual = backgammon.isWinner(blackPawnPlayer);
		assertFalse("is Winner Black Pawn Does Not Have Winner Both Colors Test.", actual);
	}
	
	@Test
	public void isWinnerWhitePawnDoesNotHaveWinnerBothColorsTest(){
		boolean pawnSet = simpleBoard.setPawn(whitePawn, 0);
		assertTrue("Pawn set failed. is Winner White Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		pawnSet = simpleBoard.setPawn(blackPawn, 1);
		assertTrue("Pawn set failed. is Winner White Pawn Does Not Have Winner Both Colors Test." ,pawnSet);
		boolean actual = backgammon.isWinner(whitePawnPlayer);
		assertFalse("is Winner White Pawn Does Not Have Winner Both Colors Test.", actual);
	}
	
	@Test
	public void isWinnerPlayerIsNullTest(){
		boolean actual = backgammon.isWinner(null);
		assertFalse("Is Winner, player is null, failed. ", actual);
	}
	
	@Test
	public void isWinnerBlacksOutsideGameTest(){
		Backgammon nonEmptyBlacksGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		boolean actual = nonEmptyBlacksGame.isWinner(blackPawnPlayer);
		assertFalse("Is winner blacks outside game test failed.", actual);
	}
	
	@Test
	public void isWinnerWhitesOutsideGameTest(){
		Backgammon nonEmptyWhitesGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		boolean actual = nonEmptyWhitesGame.isWinner(whitePawnPlayer);
		assertFalse("Is winner whites outside game test failed.", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerIsNullTest(){
		boolean actual = backgammon.isHasMoreMoves(null, simpleBoard);
		assertFalse("Is has more moves, player is null, failed.", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerWithoutTurnTest(){
		Player withoutTurn = backgammon.howIsNextInTurn();
		boolean actual = backgammon.isHasMoreMoves(withoutTurn, simpleBoard);
		assertFalse("Is has more moves, player without turn, failed.", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerWithTurnDoesNotHaveMoreMovesTest(){
		Player withTurn = backgammon.howHasTurn();
		withTurn.getTurn().getFirstDice().initDiceValue();
		withTurn.getTurn().getSecondDice().initDiceValue();
		boolean actual = backgammon.isHasMoreMoves(withTurn, simpleBoard);
		assertFalse("Is has more moves, player with turn & player does not have more moves, failed.", actual);
	}
	
	@Test
	public void isHasMoreMovesPlayerWithTurnHasMoreMovesTest(){
		Player withTurn = backgammon.howHasTurn();
		simpleBoard.setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 20);
		withTurn.getTurn().getFirstDice().rollDice();
		withTurn.getTurn().getSecondDice().rollDice();
		boolean actual = backgammon.isHasMoreMoves(withTurn, simpleBoard);
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
		boolean actual = backgammon.makeMove(null, new Move(), simpleBoard);
		assertFalse("Make move, player is null failed.", actual);
	}
	
	@Test
	public void makeMoveMoveIsNullTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, null, simpleBoard);
		assertFalse("Make move, move is null failed.", actual);
	}
	
	@Test
	public void makeMoveBoardIsNullTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(), null);
		assertFalse("Make move, board is null failed.", actual);
	}
	
	@Test
	public void makeMoveEmptyBoardTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), simpleBoard);
		assertFalse("Make move, empty board is failed.", actual);
	}
	
	@Test
	public void makeMoveInvalidTest(){
		boolean pawnSet = simpleBoard.setPawn(blackPawn, 1);
		assertTrue(pawnSet);
		pawnSet = simpleBoard.setPawn(whitePawn, 4);
		pawnSet = simpleBoard.setPawn(new Pawn(Color.white.getInnerValue()), 4);
		assertTrue(pawnSet);
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), simpleBoard);
		assertFalse("Make move, invalid is failed.", actual);
	}
	
	@Test
	public void makeMoveValidNonEmptyTest(){
		boolean pawnSet = simpleBoard.setPawn(blackPawn, 1);
		assertTrue(pawnSet);
		pawnSet = simpleBoard.setPawn(new Pawn(Color.black.getInnerValue()), 4);
		assertTrue(pawnSet);
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), simpleBoard);
		assertTrue("Make move, valid test non empty failed.", actual);
	}
	
	@Test
	public void makeMoveValidEmptyTest(){
		boolean pawnSet = simpleBoard.setPawn(blackPawn, 1);
		assertTrue(pawnSet);
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(1,4), simpleBoard);
		assertTrue("Make move, valid test empty failed.", actual);
	}
	
	@Test
	public void makeMoveRemoveWhiteCleanUpTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 4));
		boolean actual = backgammon.makeMove(whitePawnPlayer, new Move(4, -1), simpleBoard);
		assertTrue("make Move Remove White CleanUp Test", actual);
		assertTrue(simpleBoard.isEmptyColumn(4));
	}
	
	@Test
	public void makeMoveRemoveWhiteCleanUpEmptyBoardTest(){
		boolean actual = backgammon.makeMove(whitePawnPlayer, new Move(4, -1), simpleBoard);
		assertFalse("make Move Remove White CleanUp Empty Board Test", actual);
	}
	
	@Test
	public void makeMoveRemoveBlackCleanUpTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(20, 24), simpleBoard);
		assertTrue("make Move Remove black CleanUp Test", actual);
		assertTrue(simpleBoard.isEmptyColumn(20));
	}
	
	@Test
	public void makeMoveRemoveBlackCleanUpEmptyBoardTest(){
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(20, 24), simpleBoard);
		assertFalse("make Move Remove black CleanUp Empty Board Test", actual);
	}
	
	@Test
	public void makeMoveWhiteEatBlackTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 23));
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
		boolean actual = backgammon.makeMove(whitePawnPlayer, new Move(23, 20), simpleBoard);
		assertTrue("Make move white eat black test, failed.", actual);
		assertEquals("Make move white eat black test, failed.", 0, simpleBoard.getSizeOfColumn(23));
		assertEquals("Make move white eat black test, failed.", Color.white, simpleBoard.peekAtColumn(20).getColor());
	}
	
	@Test
	public void makeMoveBlackEatWhiteTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
		assertTrue(simpleBoard.setPawn(whitePawn, 23));
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(20, 23), simpleBoard);
		assertTrue("Make move black eat white test, failed.", actual);
		assertEquals("Make move black eat white test, failed.", 0, simpleBoard.getSizeOfColumn(20));
		assertEquals("Make move black eat white test, failed.", Color.black, simpleBoard.peekAtColumn(23).getColor());
	}
	
	@Test
	public void makeMoveBlackDoNotEatBlackTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
		assertTrue(simpleBoard.setPawn(new Pawn(Color.black.getInnerValue()), 23));
		boolean actual = backgammon.makeMove(blackPawnPlayer, new Move(20, 23), simpleBoard);
		assertTrue("Make move black eat white test, failed.", actual);
		assertEquals("Make move black do not eat black test, failed.", 0, simpleBoard.getSizeOfColumn(20));
		assertEquals("Make move black do not eat black test, failed.", 2, simpleBoard.getSizeOfColumn(23));
		assertEquals("Make move black do not eat black test, failed.", Color.black, simpleBoard.peekAtColumn(23).getColor());
	}
	
	@Test
	public void makeMoveWhiteDoNotEatWhiteTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 23));
		assertTrue(simpleBoard.setPawn(new Pawn(Color.white.getInnerValue()), 20));
		boolean actual = backgammon.makeMove(whitePawnPlayer, new Move(23, 20), simpleBoard);
		assertTrue("Make move white do not eat white test, failed.", actual);
		assertEquals("Make move white do not eat white test, failed.", 0, simpleBoard.getSizeOfColumn(23));
		assertEquals("Make move white do not eat white test, failed.", 2, simpleBoard.getSizeOfColumn(20));
		assertEquals("Make move white do not eat white test, failed.", Color.white, simpleBoard.peekAtColumn(20).getColor());
	}
	
	@Test
	public void makeMoveBlackOutsideGoBackToEmptyColumnTest(){
		Backgammon blackOutSide = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		boolean actual = blackOutSide.makeMove(blackPawnPlayer, new Move(-1, 4), blackOutSide.getBoard());
		assertTrue("make Move, Black Outside Go Back To Empty Column", actual);
		assertEquals(0, blackOutSide.getBoard().getHowManyBlacksOutsideGame());
		assertEquals(1, blackOutSide.getBoard().getSizeOfColumn(4));
	}
	
	@Test
	public void makeMoveBlackOutsideGoBackToOneBlackTest(){
		Backgammon blackOutSide = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(blackOutSide.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 4));
		boolean actual = blackOutSide.makeMove(blackPawnPlayer, new Move(-1, 4), blackOutSide.getBoard());
		assertTrue("make Move, Black Outside Go Back To Empty Column", actual);
		assertEquals(0, blackOutSide.getBoard().getHowManyBlacksOutsideGame());
		assertEquals(2, blackOutSide.getBoard().getSizeOfColumn(4));
	}
	
	@Test
	public void makeMoveBlackOutsideGoBackToTwoBlackTest(){
		Backgammon blackOutSide = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(blackOutSide.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 4));
		assertTrue(blackOutSide.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 4));
		boolean actual = blackOutSide.makeMove(blackPawnPlayer, new Move(-1, 4), blackOutSide.getBoard());
		assertTrue("make Move, Black Outside Go Back To Empty Column", actual);
		assertEquals(0, blackOutSide.getBoard().getHowManyBlacksOutsideGame());
		assertEquals(3, blackOutSide.getBoard().getSizeOfColumn(4));
	}
	
	@Test
	public void makeMoveBlackOutsideGoBackToOneWhiteTest(){
		Backgammon blackOutSide = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(blackOutSide.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 4));
		boolean actual = blackOutSide.makeMove(blackPawnPlayer, new Move(-1, 4), blackOutSide.getBoard());
		assertTrue("make Move, Black Outside Go Back To Empty Column", actual);
		assertEquals(0, blackOutSide.getBoard().getHowManyBlacksOutsideGame());
		assertEquals(1, blackOutSide.getBoard().getHowManyWhitesOutsideGame());
		assertEquals(1, blackOutSide.getBoard().getSizeOfColumn(4));
	}
	
	@Test
	public void makeMoveWhiteOutsideGoBackToEmptyColumnTest(){
		Backgammon whiteOutSide = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		boolean actual = whiteOutSide.makeMove(whitePawnPlayer, new Move(24, 20), whiteOutSide.getBoard());
		assertTrue("make Move, white Outside Go Back To Empty Column", actual);
		assertEquals(0, whiteOutSide.getBoard().getHowManyWhitesOutsideGame());
		assertEquals(1, whiteOutSide.getBoard().getSizeOfColumn(20));
	}
	
	@Test
	public void makeMoveWhiteOutsideGoBackToOneWhiteTest(){
		Backgammon whiteOutSide = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whiteOutSide.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 20));
		boolean actual = whiteOutSide.makeMove(whitePawnPlayer, new Move(24, 20), whiteOutSide.getBoard());
		assertTrue("make Move, white Outside Go Back To one white Column", actual);
		assertEquals(0, whiteOutSide.getBoard().getHowManyWhitesOutsideGame());
		assertEquals(2, whiteOutSide.getBoard().getSizeOfColumn(20));
	}
	
	@Test
	public void makeMoveWhiteOutsideGoBackToTwoWhitesTest(){
		Backgammon whiteOutSide = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whiteOutSide.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 20));
		assertTrue(whiteOutSide.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 20));
		boolean actual = whiteOutSide.makeMove(whitePawnPlayer, new Move(24, 20), whiteOutSide.getBoard());
		assertTrue("make Move, Black Outside Go Back To Empty Column", actual);
		assertEquals(0, whiteOutSide.getBoard().getHowManyWhitesOutsideGame());
		assertEquals(3, whiteOutSide.getBoard().getSizeOfColumn(20));
	}
	
	@Test
	public void makeMoveWhiteOutsideGoBackToOneBlackTest(){
		Backgammon whiteOutSide = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whiteOutSide.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 20));
		boolean actual = whiteOutSide.makeMove(whitePawnPlayer, new Move(24, 20), whiteOutSide.getBoard());
		assertTrue("make Move, white Outside Go Back one black Column", actual);
		assertEquals(0, whiteOutSide.getBoard().getHowManyWhitesOutsideGame());
		assertEquals(1, whiteOutSide.getBoard().getHowManyBlacksOutsideGame());
		assertEquals(1, whiteOutSide.getBoard().getSizeOfColumn(20));
	}
	
	@Test
	public void validMovePlayerIsNullTest(){
		boolean actual = backgammon.validMove(null, new Move(), simpleBoard);
		assertFalse("valid Move, player is null failed.", actual);
	}
	
	@Test
	public void validMoveMoveIsNullTest(){
		boolean actual = backgammon.validMove(blackPawnPlayer, null, simpleBoard);
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
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), simpleBoard);
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
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), simpleBoard);
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
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), simpleBoard);
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
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), simpleBoard);
		assertFalse("valid Move, black pawn empty pawn failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnFromDifferentColorBoardTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 1));
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
		boolean actual = backgammon.validMove(whitePawnPlayer, new Move(1,4), simpleBoard);
		assertFalse("valid Move, white pawn from different color failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnFromDifferentColorTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 1));
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
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), simpleBoard);
		assertFalse("valid Move, black pawn from different color failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnToDifferentColorBoardTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 4));
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
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), simpleBoard);
		assertFalse("valid Move, white pawn to different color failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnToDifferentColorTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 1));
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
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), simpleBoard);
		assertFalse("valid Move, black pawn to different color failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnToIsEmptyTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 4));
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
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), simpleBoard);
		assertTrue("valid Move, white pawn to is empty failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnToIsEmptyTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 1));
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
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), simpleBoard);
		assertTrue("valid Move, black pawn To Is Empty failed.", actual);
	}
	
	@Test
	public void vaildMoveWhitePawnToIsWhiteTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 1));
		assertTrue(simpleBoard.setPawn(whitePawn, 4));
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
		boolean actual = backgammon.validMove(playerMock, new Move(4,1), simpleBoard);
		assertTrue("valid Move, white pawn to is white failed.", actual);
	}
	
	@Test
	public void vaildMoveBlackPawnToIsBlackTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 4));
		assertTrue(simpleBoard.setPawn(blackPawn, 1));
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
		boolean actual = backgammon.validMove(playerMock, new Move(1,4), simpleBoard);
		assertTrue("valid Move, black pawn To Is black failed.", actual);
	}
	
	@Test
	public void validMoveDoCleanUpWhiteCanNotCleanUpTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 4));
		assertTrue(simpleBoard.setPawn(new Pawn(Color.white.getInnerValue()), 6));
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
		boolean actual = backgammon.validMove(playerMock, new Move(4, -1), simpleBoard);
		assertFalse("valid Move Do CleanUp White Can Not CleanUp Test", actual);
	}
	
	@Test
	public void validMoveDoCleanUpWhiteCanCleanUpTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 4));
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
		boolean actual = backgammon.validMove(playerMock, new Move(4, -1), simpleBoard);
		assertTrue("valid Move Do CleanUp White Can CleanUp Test", actual);
	}
	
	@Test
	public void validMoveDoCleanUpBlackCanNotCleanUpTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
		assertTrue(simpleBoard.setPawn(new Pawn(Color.black.getInnerValue()), 10));
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
		boolean actual = backgammon.validMove(playerMock, new Move(20, 24), simpleBoard);
		assertFalse("valid Move Do CleanUp black Can Not CleanUp Test", actual);
	}
	
	@Test
	public void validMoveDoCleanUpBlackCanCleanUpTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
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
		boolean actual = backgammon.validMove(playerMock, new Move(20, 24), simpleBoard);
		assertTrue("valid Move Do CleanUp black Can CleanUp Test", actual);
	}
	
	@Test
	public void validMoveBlackEatWhiteTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 23));
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
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
		boolean actual = backgammon.validMove(playerMock, new Move(20, 23), simpleBoard);
		assertTrue("valid Move black eat white Test failed.", actual);
	}
	
	@Test
	public void validMoveWhiteEatBlackTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 20));
		assertTrue(simpleBoard.setPawn(whitePawn, 23));
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
		boolean actual = backgammon.validMove(playerMock, new Move(23, 20), simpleBoard);
		assertTrue("valid Move white eat black Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsInsideBoardButHaveWhiteOutsideWithWhiteTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 5));
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
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(5, 0), whitesOutsideGame.getBoard());
		assertFalse("valid Move, From Is Inside Board, But Have White Outside, With White. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsInsideBoardButHaveBlackOutsideWithWhiteTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 5));
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
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(5, 0), whitesOutsideGame.getBoard());
		assertTrue("valid Move, From Is Inside Board, But Have Blacks Outside, With White. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsInsideBoardButHaveBlackOutsideWithBlackTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(blacksOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 0));
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
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(0, 5), blacksOutsideGame.getBoard());
		assertFalse("valid Move, From Is Inside Board, But Have Black Outside, WithBlack. Test failed.", actual);		
	}
	
	@Test
	public void validMoveFromIsInsideBoardButHaveWhiteOutsideWithBlackTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 0));
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
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(0, 5), whitesOutsideGame.getBoard());
		assertTrue("valid Move, From Is Inside Board, But Have whites Outside, with black. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardWhitePlayerBlackOutsideTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(24, 20), blacksOutsideGame.getBoard());
		assertFalse("valid Move, From Is Outside Board, White Player, Black Outside. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardBlackPlayerWhiteOutsideTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
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
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(-1, 3), whitesOutsideGame.getBoard());
		assertFalse("valid Move, From Is Outside Board, black Player, white Outside. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardWhitePlayerBlackOutsideWithBlackIndexesTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(-1, 3), blacksOutsideGame.getBoard());
		assertFalse("valid Move, From Is Outside Board, White Player, Black Outside, With Black Indexes. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardBlackPlayerWhiteOutsideWithWhiteIndexesTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
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
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(24,20), whitesOutsideGame.getBoard());
		assertFalse("valid Move, From Is Outside Board, black Player, white Outside, With White Indexes. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardBlackPlayerBlackOutsideTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
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
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(-1, 3), blacksOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board, Black Player, Black Outside. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardWhitePlayerWhiteOutsideTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(24, 20), whitesOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board, White Player, White Outside. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardBlackPlayerBlackOutsideWhiteIsBlockingTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(blacksOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 3));
		assertTrue(blacksOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 3));
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
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(-1, 3), blacksOutsideGame.getBoard());
		assertFalse("valid Move, From Is Outside Board. Black Player. Black Outside. White Is Blocking. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardWhitePlayerWhiteOutsideBlackIsBlockingTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 20));
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 20));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(24, 20), whitesOutsideGame.getBoard());
		assertFalse("valid Move, From Is Outside Board. White Player. White Outside. black Is Blocking. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardBlackPlayerBlackOutsideOneBlackTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(blacksOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 3));
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
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(-1, 3), blacksOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board. Black Player. Black Outside. one black. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardWhitePlayerWhiteOutsideOneWhiteTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 20));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(24, 20), whitesOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board. White Player. White Outside. one white. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardBlackPlayerBlackOutsideTwoBlackTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
		assertTrue(blacksOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 3));
		assertTrue(blacksOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("blackPawn", Pawn.class), 3));
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
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(-1, 3), blacksOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board. Black Player. Black Outside. two black. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardWhitePlayerWhiteOutsideTwoWhiteTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 20));
		assertTrue(whitesOutsideGame.getBoard().setPawn(backgammonContextTest.getBean("whitePawn", Pawn.class), 20));
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(24, 20), whitesOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board. White Player. White Outside. two white. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardBlackPlayerBlackOutsideEmptyTest(){
		Backgammon blacksOutsideGame = backgammonContextTest.getBean("nonEmptyBlacksBackgammon", Backgammon.class);
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
		boolean actual = blacksOutsideGame.validMove(playerMock, new Move(-1, 3), blacksOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board. Black Player. Black Outside. empty on destanation. Test failed.", actual);
	}
	
	@Test
	public void validMoveFromIsOutsideBoardWhitePlayerWhiteOutsideEmptyTest(){
		Backgammon whitesOutsideGame = backgammonContextTest.getBean("nonEmptyWhitesBackgammon", Backgammon.class);
		Player playerMock = mock(Player.class);
		Turn turnMock = mock(Turn.class);
		Dice firstDiceMock = mock(Dice.class);
		Dice secondDiceMock = mock(Dice.class);
		when(playerMock.getColor()).thenReturn(Color.white);
		when(playerMock.getTurn()).thenReturn(turnMock);
		when(turnMock.getFirstDice()).thenReturn(firstDiceMock);
		when(turnMock.getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(2);
		boolean actual = whitesOutsideGame.validMove(playerMock, new Move(24, 20), whitesOutsideGame.getBoard());
		assertTrue("valid Move, From Is Outside Board. White Player. White Outside. empty on destanation. Test failed.", actual);
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
		backgammon.isCanStartCleanUp(null, simpleBoard);
	}
	
	@Test(expected=NullPointerException.class)
	public void isCanStartCleanUpBoardIsNullTest(){
		backgammon.isCanStartCleanUp(blackPawnPlayer, null);
	}
	
	@Test
	public void isCanStartCleanUpWhiteCanNotCleanUpTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 6));
		assertFalse("is Can Start CleanUp White Can Not CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(whitePawnPlayer, simpleBoard));
	}
	
	@Test
	public void isCanStartCleanUpWhiteCanCleanUpTest(){
		assertTrue(simpleBoard.setPawn(whitePawn, 5));
		assertTrue("is Can Start CleanUp White Can CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(whitePawnPlayer, simpleBoard));
	}
	
	@Test
	public void isCanStartCleanUpBlackCanNotCleanUpTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 17));
		assertFalse("is Can Start CleanUp black Can Not CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(blackPawnPlayer, simpleBoard));
	}
	
	@Test
	public void isCanStartCleanUpBlackCanCleanUpTest(){
		assertTrue(simpleBoard.setPawn(blackPawn, 18));
		assertTrue("is Can Start CleanUp black Can CleanUp Test failed.", 
				backgammon.isCanStartCleanUp(blackPawnPlayer, simpleBoard));
	}
}
