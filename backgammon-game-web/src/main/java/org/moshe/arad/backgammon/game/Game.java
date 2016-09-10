package org.moshe.arad.backgammon.game;

import javax.annotation.Resource;

import org.moshe.arad.backgammon.instrument.Board;
import org.moshe.arad.backgammon.instrument.Dice;
import org.moshe.arad.backgammon.player.Player;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Game implements Gameable{

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
	
	private boolean isPlaying = true;
	
	public void play(){
		
		while(isPlaying){
			
		}
	}
	
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
