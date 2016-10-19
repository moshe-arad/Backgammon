package org.moshe.arad.game.move;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Move {

	private BoardLocation from;
	private BoardLocation to;
	private Date createDate;
	
	public Move() {
		this.createDate = new Date();
	}
	
	public Move(BoardLocation from, BoardLocation to) {
		this.from = from;
		this.to = to;
		this.createDate = new Date();
	}

	public BoardLocation getFrom() {
		return from;
	}

	public BoardLocation getTo() {
		return to;
	}

	public void setFrom(BoardLocation from) {
		this.from = from;
	}

	public void setTo(BoardLocation to) {
		this.to = to;
	}

	public Date getCreateDate() {
		return createDate;
	}	
}
