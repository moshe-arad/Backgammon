package org.moshe.arad.game.turn;

import javax.annotation.Resource;

import org.moshe.arad.game.instrument.Dice;

public class Turn {

	private static Turn instance;
	@Resource
	private Dice firstDice;
	@Resource
	private Dice secondDice;
	
	private Turn(){
		
	}
	
	public static Turn getInstance(){
		
		if(instance == null){
			synchronized (Turn.class) {
				if(instance == null){
					instance = new Turn();
				}
			}
		}
		return instance;
	}

	public Dice getFirstDice() {
		return firstDice;
	}

	public Dice getSecondDice() {
		return secondDice;
	}
}
