package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.Move;

public abstract class BackgammonPawn implements Pawn {

	@Override
	public abstract boolean isAbleToDo(Move move);

	public static boolean isWhite(BackgammonPawn pawn){
		return pawn instanceof WhiteBackgammonPawn;
	}
	
}
