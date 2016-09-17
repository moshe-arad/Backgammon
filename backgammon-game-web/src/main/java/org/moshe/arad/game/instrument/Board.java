package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.BoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;

public interface Board {

	public void executeMove(Player player, Move move);
	
	public boolean isValidMove(Player player, Move move);
	
	public boolean isWinner(Player player);
	
	public boolean isHasMoreMoves(Player player);
	
	public void clearBoard();

	public void initBoard();
	
	public boolean setPawn(Pawn pawn, BoardLocation location);
	
	public void display();
}
