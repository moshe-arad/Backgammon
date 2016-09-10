package org.moshe.arad.backgammon;

public interface Gameable {
	
	public boolean isHasWinner();
	
	public Player howHasTurn();
	
	public boolean isWinner(Player player);
	
	public void rollDices(Dice[] dices);
	
	@Deprecated
	public boolean makeMove(int fromIndex, int toIndex);
	
	public boolean makeMove(Board board);
	
	@Deprecated
	public boolean validateMove(int fromIndex, int toIndex);
	
	public boolean validateMove(Board board);
	
	@Deprecated
	public boolean isHasMoreMoves();
	
	public boolean isHasMoreMoves(Player player);
	
	@Deprecated
	public void passTurn();
	
	public void passTurnTo(Player player);
}
