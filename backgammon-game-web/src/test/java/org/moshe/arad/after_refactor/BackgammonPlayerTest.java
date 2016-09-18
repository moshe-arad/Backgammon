package org.moshe.arad.after_refactor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import org.hamcrest.collection.IsIn;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.game.instrument.BackgammonDice;
import org.moshe.arad.game.instrument.BlackBackgammonPawn;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.instrument.WhiteBackgammonPawn;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.moshe.arad.game.turn.Turn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test2.xml")
public class BackgammonPlayerTest {

	@Resource
	Player firstPlayer;
	@Resource
	Player secondPlayer;
	@Autowired
	BackgammonTurn turn;
	@Resource
	Dice firstDice;
	@Resource
	Dice secondDice;
	@Autowired
	ApplicationContext testContext;
	
	@After
	public void cleanup(){
		firstPlayer.setTurn(turn);
		firstDice.initDice();
		secondDice.initDice();
	}
	
	@Test(expected=Exception.class)
	public void makePlayedMoveIsNull() throws Exception{
		firstPlayer.makePlayed(null);
	}
	
	@Test
	public void makePlayedTwoMatchingDicesTest() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		firstPlayer.setTurn(turnMock);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		when(((BackgammonTurn)turnMock).getFirstDice()).thenReturn(firstDiceMock);
		when(((BackgammonTurn)turnMock).getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		firstPlayer.makePlayed(new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(18)));
		verify(firstDiceMock).initDice();
	}
	
	@Test
	public void makePlayedMatchFirstDiceTest() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		firstPlayer.setTurn(turnMock);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		when(((BackgammonTurn)turnMock).getFirstDice()).thenReturn(firstDiceMock);
		when(((BackgammonTurn)turnMock).getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(5);
		when(secondDiceMock.getValue()).thenReturn(3);
		
		firstPlayer.makePlayed(new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(18)));
		verify(firstDiceMock).initDice();
	}
	
	@Test
	public void makePlayedMatchSecondDiceTest() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		firstPlayer.setTurn(turnMock);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		when(((BackgammonTurn)turnMock).getFirstDice()).thenReturn(firstDiceMock);
		when(((BackgammonTurn)turnMock).getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(3);
		when(secondDiceMock.getValue()).thenReturn(5);
		
		firstPlayer.makePlayed(new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(18)));
		verify(secondDiceMock).initDice();
	}
	
	@Test
	public void makePlayedStepDoesNotMatchFirstDiceTest() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		firstPlayer.setTurn(turnMock);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		when(((BackgammonTurn)turnMock).getFirstDice()).thenReturn(firstDiceMock);
		when(((BackgammonTurn)turnMock).getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(6);
		when(secondDiceMock.getValue()).thenReturn(4);
		
		firstPlayer.makePlayed(new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(18)));
		verify(firstDiceMock).initDice();
	}
	
	@Test
	public void makePlayedStepDoesNotMatchSecondDiceTest() throws Exception{
		BackgammonTurn turnMock = mock(BackgammonTurn.class);
		firstPlayer.setTurn(turnMock);
		BackgammonDice firstDiceMock = mock(BackgammonDice.class);
		BackgammonDice secondDiceMock = mock(BackgammonDice.class);
		
		when(((BackgammonTurn)turnMock).getFirstDice()).thenReturn(firstDiceMock);
		when(((BackgammonTurn)turnMock).getSecondDice()).thenReturn(secondDiceMock);
		when(firstDiceMock.getValue()).thenReturn(2);
		when(secondDiceMock.getValue()).thenReturn(4);
		
		firstPlayer.makePlayed(new Move(new BackgammonBoardLocation(23), new BackgammonBoardLocation(18)));
		verify(secondDiceMock).initDice();
	}
	
	@Test
	public void rollDicesTest(){
		assertEquals(BackgammonDice.NONE,firstDice.getValue());
		assertEquals(BackgammonDice.NONE,secondDice.getValue());
		firstPlayer.rollDices();
		assertNotEquals(BackgammonDice.NONE,firstDice.getValue());
		assertNotEquals(BackgammonDice.NONE,secondDice.getValue());
		assertThat(firstDice.getValue(),IsIn.isIn(new Integer[]{1,2,3,4,5,6}));
		assertThat(secondDice.getValue(), IsIn.isIn(new Integer[]{1,2,3,4,5,6}));
	}
	
	@Test(expected=Exception.class)
	public void isCanPlayWithPawnIsNull() throws Exception{
		firstPlayer.isCanPlayWith(null);
	}
	
	@Test
	public void isCanPlayWithPlayerWhitePawnWhite() throws Exception{
		WhiteBackgammonPawn whitePawn = testContext.getBean(WhiteBackgammonPawn.class);
		assertTrue(firstPlayer.isCanPlayWith(whitePawn));
	}
	
	@Test
	public void isCanPlayWithWhitePlayerBlackPawn() throws Exception{
		BlackBackgammonPawn blackPawn = testContext.getBean(BlackBackgammonPawn.class);
		assertFalse(firstPlayer.isCanPlayWith(blackPawn));
	}
	
	@Test
	public void isCanPlayWithBlackPlayerWhitePawn() throws Exception{
		WhiteBackgammonPawn whitePawn = testContext.getBean(WhiteBackgammonPawn.class);
		assertFalse(secondPlayer.isCanPlayWith(whitePawn));
	}
	
	@Test
	public void isCanPlayWithBlackPlayerBlackPawn() throws Exception{
		BlackBackgammonPawn blackPawn = testContext.getBean(BlackBackgammonPawn.class);
		assertTrue(secondPlayer.isCanPlayWith(blackPawn));
	}
}
