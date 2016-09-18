package org.moshe.arad.game.instrument;

import java.util.Random;

public class BackgammonDice implements Dice {

	public static final int NONE = 0;
	public static final int MAX = 6;
	private int value = NONE;
	private Random random = new Random();
	
	@Override
	public void rollDice(){
		value = random.nextInt(MAX)+1;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void initDice() {
		value = NONE;
	}
}
