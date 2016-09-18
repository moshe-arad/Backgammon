package org.moshe.arad.backgammon;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.collection.IsIn;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.moshe.arad.game.instrument.BackgammonDice;

/*
public class DiceTest {
	
	BackgammonDice dice;
	
	@Before
	public void setup(){
		dice = new BackgammonDice();
	}
	
	@Test
	public void diceRolesTest(){
		for(int i=0; i<100; i++){
			dice.rollDice();
			assertThat("Dice value is not within valid range.", Integer.valueOf(dice.getValue()), IsIn.isIn(new Integer[]{1,2,3,4,5,6}));
		}
	}
	
	@Test
	public void diceRolesNotTheSameValueTest(){
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
		List<Integer> actualResults = new ArrayList<Integer>();
		
		for(int i=0; i<100; i++){
			dice.rollDice();
			actualResults.add(dice.getValue());
		}
		
		boolean anyActualMatch = actualResults.stream().anyMatch(item->(item<1)||(item>6));
		assertFalse(anyActualMatch);
	}
	
	@Test
	public void initDiceValueTest(){
		dice.rollDice();
		
		assertThat("Dice rolling produced invalid value.", Integer.valueOf(dice.getValue()), IsIn.isIn(new Integer[]{1,2,3,4,5,6}));
		
		dice.initDiceValue();
		
		assertEquals("Dice initialization failed." ,0, dice.getValue());
	}
}
*/