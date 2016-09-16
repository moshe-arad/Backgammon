package org.moshe.arad.game.move;

public class Move {

	private BoardLocation from;
	private BoardLocation to;
	
	public Move() {
	}
	
	public Move(BoardLocation from, BoardLocation to) {
		this.from = from;
		this.to = to;
	}

	public BoardLocation getFrom() {
		return from;
	}

	public void setFrom(BoardLocation from) {
		this.from = from;
	}

	public BoardLocation getTo() {
		return to;
	}

	public void setTo(BoardLocation to) {
		this.to = to;
	}
	
	/*
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
	*/
}
