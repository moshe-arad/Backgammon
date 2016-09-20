package org.moshe.arad.game.turn;

import javax.annotation.Resource;

import org.moshe.arad.game.instrument.BackgammonDice;

/**
 * a backgammon turn has dices.
 */
public class BackgammonTurn implements Turn {

	private static BackgammonTurn instance;
	@Resource
	private BackgammonDice firstDice;
	@Resource
	private BackgammonDice secondDice;
	
	private BackgammonTurn(){
		
	}
	
	public static BackgammonTurn getInstance(){
		
		if(instance == null){
			synchronized (BackgammonTurn.class) {
				if(instance == null){
					instance = new BackgammonTurn();
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
