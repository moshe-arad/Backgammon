package org.moshe.arad.backgammon;

import java.util.Random;

public class Dice {

	public static final int SEED = 6;
	private int value;
	private Random random = new Random(SEED);
	
	public void rollDice(){
		value = random.nextInt()+1;
	}

	public int getValue() {
		return value;
	}
}
