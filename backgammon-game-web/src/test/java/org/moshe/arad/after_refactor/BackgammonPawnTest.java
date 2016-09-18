package org.moshe.arad.after_refactor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.game.instrument.BackgammonPawn;
import org.moshe.arad.game.instrument.BlackBackgammonPawn;
import org.moshe.arad.game.instrument.WhiteBackgammonPawn;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test2.xml")
public class BackgammonPawnTest {

	@Autowired
	ApplicationContext testContext;
	
	
	@Test(expected=Exception.class)
	public void isWhiteNullPawn() throws Exception{
		BackgammonPawn.isWhite(null);
	}
	
	@Test
	public void isWhiteWithWhitePawn() throws Exception{
		WhiteBackgammonPawn whitePawn = testContext.getBean(WhiteBackgammonPawn.class);
		assertTrue(BackgammonPawn.isWhite(whitePawn));
	}
	
	@Test
	public void isWhiteWithBlackPawn() throws Exception{
		BlackBackgammonPawn blackPawn = testContext.getBean(BlackBackgammonPawn.class);
		assertFalse(BackgammonPawn.isWhite(blackPawn));
	}
	
	@Test(expected=Exception.class)
	public void isAbleToDoNullMove() throws Exception{
		WhiteBackgammonPawn whitePawn = testContext.getBean(WhiteBackgammonPawn.class);
		whitePawn.isAbleToDo(null);
	}
	
	@Test
	public void isAbleToDoWhitePawnInvalidMoveDirection() throws Exception{
		WhiteBackgammonPawn whitePawn = testContext.getBean(WhiteBackgammonPawn.class);
		BackgammonBoardLocation from = new BackgammonBoardLocation(10);
		BackgammonBoardLocation to = new BackgammonBoardLocation(15);
		assertFalse(whitePawn.isAbleToDo(new Move(from, to)));
	}
	
	@Test
	public void isAbleToDoWhitePawnValidMoveDirection() throws Exception{
		WhiteBackgammonPawn whitePawn = testContext.getBean(WhiteBackgammonPawn.class);
		BackgammonBoardLocation from = new BackgammonBoardLocation(15);
		BackgammonBoardLocation to = new BackgammonBoardLocation(10);
		assertTrue(whitePawn.isAbleToDo(new Move(from, to)));
	}
	
	@Test
	public void isAbleToDoBlackPawnInvalidMoveDirection() throws Exception{
		BlackBackgammonPawn blackPawn = testContext.getBean(BlackBackgammonPawn.class);
		BackgammonBoardLocation from = new BackgammonBoardLocation(15);
		BackgammonBoardLocation to = new BackgammonBoardLocation(10);
		assertFalse(blackPawn.isAbleToDo(new Move(from, to)));
	}
	
	@Test
	public void isAbleToDoBlackPawnValidMoveDirection() throws Exception{
		BlackBackgammonPawn blackPawn = testContext.getBean(BlackBackgammonPawn.class);
		BackgammonBoardLocation from = new BackgammonBoardLocation(10);
		BackgammonBoardLocation to = new BackgammonBoardLocation(15);
		assertTrue(blackPawn.isAbleToDo(new Move(from, to)));
	}
	
	@Test
	public void equalsTwoWhites(){
		WhiteBackgammonPawn whitePawn1 = testContext.getBean(WhiteBackgammonPawn.class);
		WhiteBackgammonPawn whitePawn2 = testContext.getBean(WhiteBackgammonPawn.class);
		
		assertTrue(whitePawn1.equals(whitePawn2));
	}
	
	@Test
	public void equalsWhiteAndBlack(){
		WhiteBackgammonPawn whitePawn = testContext.getBean(WhiteBackgammonPawn.class);
		BlackBackgammonPawn blackPawn = testContext.getBean(BlackBackgammonPawn.class);
		
		assertFalse(whitePawn.equals(blackPawn));
	}
	
	@Test
	public void equalsTwoBlack(){
		BlackBackgammonPawn blackPawn1 = testContext.getBean(BlackBackgammonPawn.class);
		BlackBackgammonPawn blackPawn2 = testContext.getBean(BlackBackgammonPawn.class);
		
		assertTrue(blackPawn1.equals(blackPawn2));
	}
}
