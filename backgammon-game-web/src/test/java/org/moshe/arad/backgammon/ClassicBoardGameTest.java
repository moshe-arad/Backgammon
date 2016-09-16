package org.moshe.arad.backgammon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test.xml")
public class ClassicBoardGameTest {

	@Autowired
	Backgammon backgammon;
	@Resource
	Player firstPlayer;
	@Resource
	Player secondPlayer;
	@Autowired
	BackgammonTurn turn;
	
	@Test
	public void howHasTurnFirstPlayerValidTest(){
		Player actual = backgammon.howHasTurn();
		
		assertEquals("How has turn test failed.", firstPlayer, actual);
	}
	
	@Test
	public void howHasTurnSecondPlayerInvalidTest(){
		Player actual = backgammon.howHasTurn();
		
		assertNotEquals("How has turn test failed.", secondPlayer, actual);
	}
	
	@Test
	public void passTurnTest(){
		boolean isTurnPassed = backgammon.passTurn();
		
		assertTrue("Failed to pass turn. pass turn test failed.", isTurnPassed);
		
		Player hasTurn = backgammon.howHasTurn();
		
		assertEquals("Didn't pass turn correctly. pass turn test failed" ,secondPlayer, hasTurn);
		
		isTurnPassed = backgammon.passTurn();
		
		assertTrue("Failed to pass turn second attempt. pass turn test failed.", isTurnPassed);
		
		hasTurn = backgammon.howHasTurn();
		
		assertEquals("Didn't pass turn correctly, second attempt. pass turn test failed" ,firstPlayer, hasTurn);
	}
	
	@Test
	public void howIsNextInTurnTest(){
		Player next = backgammon.howIsNextInTurn();
		
		assertEquals("Second player is not next in turn. How is next in turn test failed." ,secondPlayer, next);
		
		boolean isPassTurn = backgammon.passTurn();
		
		assertTrue("Failed to pass turn. How is next in turn test failed.", isPassTurn);
		
		next = backgammon.howIsNextInTurn();
		
		assertEquals("First player is not next in turn. How is next in turn test failed." ,firstPlayer, next);
	}
}
