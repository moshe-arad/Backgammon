package org.moshe.arad.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hamcrest.collection.IsIn;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.game.instrument.Dice;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:backgammon-context-test.xml")
public class BackgammonDiceTest {

	@Resource
	Dice firstDice;
	
	@Before
	public void setup(){
		firstDice.initDice();
	}
	
	@Test
	public void diceRoles(){
		for(int i=0; i<100; i++){
			firstDice.rollDice();
			assertThat("Dice value is not within valid range.", Integer.valueOf(firstDice.getValue()), IsIn.isIn(new Integer[]{1,2,3,4,5,6}));
		}
	}
	
	@Test
	public void diceRolesNotTheSameValue(){
		List<Integer> actualResults = new ArrayList<Integer>();
		
		for(int i=0; i<100; i++){
			firstDice.rollDice();
			actualResults.add(firstDice.getValue());
		}
		
		boolean anyActualMatch = actualResults.stream().anyMatch(item->(!item.equals(actualResults.get(0))));
		assertTrue(anyActualMatch);
	}
	
	@Test
	public void diceRolesAllPossibleValuesWereRoled(){
		List<Integer> actualResults = new ArrayList<Integer>();
		
		for(int i=0; i<100; i++){
			firstDice.rollDice();
			actualResults.add(firstDice.getValue());
		}
		
		boolean anyActualMatch = actualResults.stream().anyMatch(item->(item<1)||(item>6));
		assertFalse(anyActualMatch);
	}
	
	@Test
	public void initDiceValue(){
		firstDice.rollDice();
		
		assertThat("Dice rolling produced invalid value.", Integer.valueOf(firstDice.getValue()), IsIn.isIn(new Integer[]{1,2,3,4,5,6}));
		
		firstDice.initDice();
		
		assertEquals("Dice initialization failed." ,0, firstDice.getValue());
	}
}
