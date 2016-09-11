package org.moshe.arad.backgammon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.backgammon.player.Player;
import org.moshe.arad.backgammon.turn.BackgammonTurnOrder;
import org.moshe.arad.backgammon.turn.Turn;
import org.moshe.arad.backgammon.turn.TurnOrderable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test.xml")
public class BackgammonTurnOrderTest {

	TurnOrderable backgammonTurnOrder;
	@Resource
	Player firstPlayer;
	@Resource
	Player secondPlayer;
	@Autowired
	Turn turn;
	
	@Before
	public void init(){
		backgammonTurnOrder = new BackgammonTurnOrder(firstPlayer, secondPlayer, turn);
	}
	
	@Test(expected=NullPointerException.class)
	public void constructorWithNullFirstPlayerTest(){
		TurnOrderable actual = new BackgammonTurnOrder(null, secondPlayer, turn);
	}
	
	@Test(expected=NullPointerException.class)
	public void constructorWithNullSecondPlayerTest(){
		TurnOrderable actual = new BackgammonTurnOrder(firstPlayer, null, turn);
	}
	
	@Test(expected=NullPointerException.class)
	public void constructorWithNullTurnTest(){
		TurnOrderable actual = new BackgammonTurnOrder(firstPlayer, secondPlayer, null);
	}
	
	@Test
	public void constructorNotThrowExceptionTest(){
		TurnOrderable actual = new BackgammonTurnOrder(firstPlayer, secondPlayer, turn);
		
		assertNotNull(actual);
	}
	
	@Test
	public void howHasTurnFirstPlayerValidTest(){
		Player actual = backgammonTurnOrder.howHasTurn();
		
		assertEquals("How has turn test failed.", firstPlayer, actual);
	}
	
	@Test
	public void howHasTurnSecondPlayerInvalidTest(){
		Player actual = backgammonTurnOrder.howHasTurn();
		
		assertNotEquals("How has turn test failed.", secondPlayer, actual);
	}
	
	@Test
	public void passTurnTest(){
		boolean isTurnPassed = backgammonTurnOrder.passTurn();
		
		assertTrue("Failed to pass turn. pass turn test failed.", isTurnPassed);
		
		Player hasTurn = backgammonTurnOrder.howHasTurn();
		
		assertEquals("Didn't pass turn correctly. pass turn test failed" ,secondPlayer, hasTurn);
		
		isTurnPassed = backgammonTurnOrder.passTurn();
		
		assertTrue("Failed to pass turn second attempt. pass turn test failed.", isTurnPassed);
		
		hasTurn = backgammonTurnOrder.howHasTurn();
		
		assertEquals("Didn't pass turn correctly, second attempt. pass turn test failed" ,firstPlayer, hasTurn);
	}
	
	@Test
	public void howIsNextInTurnTest(){
		Player next = backgammonTurnOrder.howIsNextInTurn();
		
		assertEquals("Second player is not next in turn. How is next in turn test failed." ,secondPlayer, next);
		
		boolean isPassTurn = backgammonTurnOrder.passTurn();
		
		assertTrue("Failed to pass turn. How is next in turn test failed.", isPassTurn);
		
		next = backgammonTurnOrder.howIsNextInTurn();
		
		assertEquals("First player is not next in turn. How is next in turn test failed." ,firstPlayer, next);
	}
}
