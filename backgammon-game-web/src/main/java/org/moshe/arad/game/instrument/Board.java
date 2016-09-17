package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.BoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.Player;

public interface Board {

	public void initBoard();
	
	public void clearBoard();
	
	public void display();
	
	public boolean isHasMoreMoves(Player player);
	
	public boolean isValidMove(Player player, Move move);
	
	public void executeMove(Player player, Move move);
	
	public boolean setPawn(Pawn pawn, BoardLocation location);
	
	public boolean isWinner(Player player);
}
