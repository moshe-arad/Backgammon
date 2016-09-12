package org.moshe.arad.game.instrument;

public interface InstrumentGameable {

	public void rollDices(Dice[] dices);
	
	public boolean makeMove(Board board);
	
	public boolean validMove(Board board);
}
