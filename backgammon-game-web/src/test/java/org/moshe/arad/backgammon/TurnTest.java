package org.moshe.arad.backgammon;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.moshe.arad.game.turn.BackgammonTurn;


public class TurnTest {

	@Test
	public void isTheSameTurnObject(){
		BackgammonTurn actualFirst = BackgammonTurn.getInstance();
		BackgammonTurn actualSecond = BackgammonTurn.getInstance();
		
		assertSame("This is not the same object.", actualFirst, actualSecond);
	}
}
