package org.moshe.arad.game.player;

import java.util.Scanner;

import org.moshe.arad.game.instrument.BackgammonBoard;
import org.moshe.arad.game.move.Move;

/**
 * 
 * @author moshe-arad
 *
 * The Game will "tell":
 * 
 *  1.to the player play a game turn.
 *  2.if this player is a winner.
 *  3.if this player has more moves to play.
 *  4.to a player: "enter your move".
 *  5.to a player about invalid move he made.
 *  
 *  TODO need to remove Scanner object from method signature.
 */
public interface PlayerGameable {
	
	public void makePlayed(Move move);
}
