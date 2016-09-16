package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;

public class WhiteBackgammonPawn extends BackgammonPawn{

	@Override
	public boolean isAbleToDo(Move move) {
		return (((BackgammonBoardLocation)move.getFrom()).getIndex() - ((BackgammonBoardLocation)move.getFrom()).getIndex() > 0);
	}

	@Override
	public boolean equals(BackgammonPawn other) {
		return other instanceof WhiteBackgammonPawn; 
	}
}
