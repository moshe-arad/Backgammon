package org.moshe.arad.backgammon_dispatcher.entities;

import org.moshe.arad.game.instrument.Dice;

public class PairBackgammonDices {

	private Dice first;
	private Dice second;
	
	
	public PairBackgammonDices(Dice first, Dice second) {
		this.first = first;
		this.second = second;
	}
	
	public Dice getFirst() {
		return first;
	}
	public void setFirst(Dice first) {
		this.first = first;
	}
	public Dice getSecond() {
		return second;
	}
	public void setSecond(Dice second) {
		this.second = second;
	}
	
	
}
