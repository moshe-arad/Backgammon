package org.moshe.arad.game.instrument;

public enum Color {
	white(0),
	black(1);
	
	private int innerValue;
	
	private Color(int innerValue) {
		this.innerValue = innerValue;
	}

	public int getInnerValue() {
		return innerValue;
	}

	public void setInnerValue(int innerValue) {
		this.innerValue = innerValue;
	}
}
