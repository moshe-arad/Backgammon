package org.moshe.arad.game;

import static org.junit.Assert.assertSame;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.game.instrument.BackgammonDice;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test.xml")
public class BackgammonTurnTest {

	@Resource
	BackgammonDice firstDice;
	@Resource
	BackgammonDice secondDice;
	
	@Test
	public void isTheSameTurnObject(){
		BackgammonTurn actualFirst = BackgammonTurn.getInstance(firstDice, secondDice);
		BackgammonTurn actualSecond = BackgammonTurn.getInstance(firstDice, secondDice);
		
		assertSame("This is not the same object.", actualFirst, actualSecond);
	}
}
