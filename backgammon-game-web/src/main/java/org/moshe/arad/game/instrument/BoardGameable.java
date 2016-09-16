package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;

public interface BoardGameable {

	/**
	 * 
	 * old name makeMove
	 */
	public boolean executeMove(Player player, Move move);
	
	public boolean isValidMove(Player player, Move move);
	
	public boolean isWinner(Player player);
	
	public boolean isHasMoreMoves(Player player);
}
