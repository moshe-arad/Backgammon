package org.moshe.arad.backgammon_dispatcher.entities;

public class DiceRolling extends BasicDetails {

	private int firstDice;
	private int secondDice;
	
	public DiceRolling() {
	}
	
	public DiceRolling(int messageToken, String color, Boolean isYourTurn, int firstDice, int secondDice) {
		super(messageToken, color, isYourTurn);
		this.firstDice = firstDice;
		this.secondDice = secondDice;
	}
	
	public int getFirstDice() {
		return firstDice;
	}
	public void setFirstDice(int firstDice) {
		this.firstDice = firstDice;
	}
	public int getSecondDice() {
		return secondDice;
	}
	public void setSecondDice(int secondDice) {
		this.secondDice = secondDice;
	}
	
	
}
