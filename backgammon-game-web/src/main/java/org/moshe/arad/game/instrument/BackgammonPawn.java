package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.Move;

public abstract class BackgammonPawn implements Pawn {

//	private Color color;
//
//	public BackgammonPawn(int color) {
//		this.color = Color.getColorByInt(color);
//	}
//
//	public Color getColor() {
//		return color;
//	}

	@Override
	public abstract boolean isAbleToDo(Move move);

	public abstract boolean equals(BackgammonPawn other);
	
//	public abstract Color getColor();
}
