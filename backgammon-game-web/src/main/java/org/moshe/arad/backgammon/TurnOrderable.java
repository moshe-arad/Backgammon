package org.moshe.arad.backgammon;

public interface TurnOrderable {

	public Player howHasTurn();
	
	public boolean passTurn(Player from, Player to);
	
	public Player howIsNextInTurn();
}
