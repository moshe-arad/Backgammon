package org.moshe.arad.backgammon;

import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.moshe.arad.backgammon.turn.Turn;

public class TurnTest {

	@Test
	public void isTheSameTurnObject(){
		Turn actualFirst = Turn.getInstance();
		Turn actualSecond = Turn.getInstance();
		
		assertSame("This is not the same object.", actualFirst, actualSecond);
	}
}
