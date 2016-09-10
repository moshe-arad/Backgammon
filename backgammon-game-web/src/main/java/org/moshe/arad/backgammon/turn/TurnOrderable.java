package org.moshe.arad.backgammon.turn;

import org.moshe.arad.backgammon.player.Player;

public interface TurnOrderable {

	public Player howHasTurn();
	
	public boolean passTurn(Player from, Player to);
	
	public Player howIsNextInTurn();
}
