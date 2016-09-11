package org.moshe.arad.game.player;

public interface PlayerGameable {

	public void playGameTurn(Player player);
	
	public boolean isWinner(Player player);
	
	public boolean isHasMoreMoves(Player player);
}
