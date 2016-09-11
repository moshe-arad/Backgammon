package org.moshe.arad.game.classic_board;

import javax.annotation.Resource;

import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.TurnOrder;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClassicBoardGame implements ClassicBoardGameable{

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
	@Autowired
	protected TurnOrder turnOrder;
	
	private boolean isPlaying = true;
	
	public void play(){
		
		while(isPlaying){
			Player playerWithTurn = turnOrder.howHasTurn();
			playGameTurn(playerWithTurn);
			if(!isWinner(playerWithTurn)) turnOrder.passTurn();
			else{
				doWinnerActions();
				isPlaying = false;
			}
		}
	}
	
	public void playGameTurn(Player player) {
		// TODO Auto-generated method stub
		
	}

	public void doWinnerActions() {
		// TODO Auto-generated method stub
		
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
