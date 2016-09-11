package org.moshe.arad.game.instrument;

import java.util.Random;

public class Dice {

	public static final int MAX = 6;
	private int value;
	private Random random = new Random();
	
	public void rollDice(){
		value = random.nextInt(MAX)+1;
	}

	public int getValue() {
		return value;
	}
}
