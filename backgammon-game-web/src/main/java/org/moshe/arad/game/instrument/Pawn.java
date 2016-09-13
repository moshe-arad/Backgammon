package org.moshe.arad.game.instrument;

public class Pawn {

	private Color color;

	public Pawn(int color) {
		this.color = Color.getColorByInt(color);
	}

	public Color getColor() {
		return color;
	}
}
