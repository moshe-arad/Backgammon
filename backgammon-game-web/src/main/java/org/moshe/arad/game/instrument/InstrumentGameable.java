package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;

/**
 * 
 * @author moshe-arad
 *
 * The Game will "tell":
 * 
 *  1.to a player: roll dices. 
 *  2.to a player: make your move on board.
 *  3.if a move is valid to do on board.
 */
public interface InstrumentGameable {

	public void rollDices(Player player);
	
	public boolean makeMove(Player player, Move move, Board board);
	
	public boolean validMove(Player player, Move move, Board board);
	
	public boolean initDices(Player player, Move move);
}
