package org.moshe.arad.backgammon.game;

import org.moshe.arad.backgammon.instrument.Board;
import org.moshe.arad.backgammon.instrument.Dice;
import org.moshe.arad.backgammon.player.Player;

public interface PlayerGameable {

	public void playGameTurn(Player player);
	
	public boolean isWinner(Player player);
	
	public boolean isHasMoreMoves(Player player);
}
