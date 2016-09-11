package org.moshe.arad.backgammon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	
//	Turn turn;
	
	@Before
	public void init(){
		backgammonTurnOrder = new BackgammonTurnOrder(firstPlayer, secondPlayer);
	}
	
	@Test(expected=NullPointerException.class)
	public void constructorWithNullFirstPlayerTest(){
		TurnOrderable actual = new BackgammonTurnOrder(null, secondPlayer);
	}
	
	@Test(expected=NullPointerException.class)
	public void constructorWithNullSecondPlayerTest(){
		TurnOrderable actual = new BackgammonTurnOrder(firstPlayer, null);
	}
	
	@Test
	public void constructorNotThrowExceptionTest(){
		TurnOrderable actual = new BackgammonTurnOrder(firstPlayer, secondPlayer);
		
		assertNotNull(actual);
	}
	
	@Test
	public void howHasTurnTest(){
		Player actual = backgammonTurnOrder.howHasTurn();
		
		assertEquals("How has turn test failed.", firstPlayer, actual);
	}
}
