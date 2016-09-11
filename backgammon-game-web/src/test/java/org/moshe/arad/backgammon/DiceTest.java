package org.moshe.arad.backgammon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.collection.IsIn;
import org.junit.Test;
import org.moshe.arad.game.instrument.Dice;


public class DiceTest {
	
	@Test
	public void diceRolesTest(){
		Dice dice = new Dice();
		
		for(int i=0; i<100; i++){
			dice.rollDice();
			assertThat("Dice value is not within valid range.", Integer.valueOf(dice.getValue()), IsIn.isIn(new Integer[]{1,2,3,4,5,6}));
		}
	}
	
	@Test
	public void diceRolesNotTheSameValueTest(){
		Dice dice = new Dice();
		List<Integer> actualResults = new ArrayList<Integer>();
		
		for(int i=0; i<100; i++){
			dice.rollDice();
			actualResults.add(dice.getValue());
		}
		
		boolean anyActualMatch = actualResults.stream().anyMatch(item->(!item.equals(actualResults.get(0))));
		assertTrue(anyActualMatch);
	}
	
	@Test
	public void diceRolesAllPossibleValuesWereRoledTest(){
		Dice dice = new Dice();
		List<Integer> actualResults = new ArrayList<Integer>();
		
		for(int i=0; i<100; i++){
			dice.rollDice();
			actualResults.add(dice.getValue());
		}
		
		boolean anyActualMatch = actualResults.stream().anyMatch(item->(item<1)||(item>6));
		assertFalse(anyActualMatch);
	}
}
