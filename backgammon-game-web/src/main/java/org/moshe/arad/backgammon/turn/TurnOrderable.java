package org.moshe.arad.backgammon.turn;

import org.moshe.arad.backgammon.player.Player;

public interface TurnOrderable {

	public Player howHasTurn();
	
	public boolean passTurn();
	
	public Player howIsNextInTurn();
}
