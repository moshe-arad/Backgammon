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

	public BoardLocation getTo() {
		return to;
	}

	/**
	 * 
	 * TODO remove setters
	 */
	public void setFrom(BoardLocation from) {
		this.from = from;
	}

	public void setTo(BoardLocation to) {
		this.to = to;
	}
	
	
}
