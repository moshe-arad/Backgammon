package org.moshe.arad.backgammon;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

public class Game {

	@Autowired
	private Board board;
	@Resource
	private Player firstPlayer;
	@Resource
	private Player secondPlayer;
	@Resource
	private Dice firstDice;
	@Resource
	private Dice secondDice;
	
	public Board getBoard() {
		return board;
	}
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	public Player getSecondPlayer() {
		return secondPlayer;
	}
	public Dice getFirstDice() {
		return firstDice;
	}
	public Dice getSecondDice() {
		return secondDice;
	}
}
