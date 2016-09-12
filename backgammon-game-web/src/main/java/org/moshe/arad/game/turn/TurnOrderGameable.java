package org.moshe.arad.game.turn;

import org.moshe.arad.game.player.Player;

public interface TurnOrderGameable {

	public Player howHasTurn();
	
	public boolean passTurn();
	
	public Player howIsNextInTurn();
}
