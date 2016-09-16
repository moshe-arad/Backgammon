package org.moshe.arad.game.turn;

import javax.annotation.Resource;

import org.moshe.arad.game.instrument.BackgammonDice;

public class Turn {

	private static Turn instance;
	@Resource
	private BackgammonDice firstDice;
	@Resource
	private BackgammonDice secondDice;
	
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

	public BackgammonDice getFirstDice() {
		return firstDice;
	}

	public BackgammonDice getSecondDice() {
		return secondDice;
	}
}
