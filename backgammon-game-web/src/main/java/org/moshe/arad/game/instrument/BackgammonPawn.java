package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.Move;

public abstract class BackgammonPawn implements Pawn {

	@Override
	public abstract boolean isAbleToDo(Move move);

	public abstract boolean equals(BackgammonPawn other);
	
}
