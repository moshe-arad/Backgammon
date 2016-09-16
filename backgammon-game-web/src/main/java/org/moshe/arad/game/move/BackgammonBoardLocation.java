package org.moshe.arad.game.move;

public class BackgammonBoardLocation implements BoardLocation{

	private int index;

	public BackgammonBoardLocation(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
