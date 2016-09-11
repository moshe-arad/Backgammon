package org.moshe.arad.backgammon.game;

import org.moshe.arad.backgammon.instrument.Board;
import org.moshe.arad.backgammon.instrument.Dice;

public interface InstrumentGameable {

	public void rollDices(Dice[] dices);
	
	public boolean makeMove(Board board);
	
	public boolean validateMove(Board board);
}
