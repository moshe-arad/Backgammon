package org.moshe.arad.game.move;

public class Move {

	private int from;
	private int to;
	
	public Move() {
	}
	
	public Move(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	public void setFrom(int from) {
		this.from = from;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public int getFrom() {
		return from;
	}
	public int getTo() {
		return to;
	}
}
