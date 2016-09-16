package org.moshe.arad.game.classic_board;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.moshe.arad.game.BasicGameable;
import org.moshe.arad.game.instrument.BackgammonBoard;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.TurnOrderGameable;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClassicBoardGame implements BasicGameable, TurnOrderGameable{

	protected BackgammonBoard board;
	@Resource
	private Player firstPlayer;
	@Resource
	private Player secondPlayer;
	@Resource
	private LinkedList<Player> order;
	
	private boolean isPlaying = true;
	
	
	public ClassicBoardGame(BackgammonBoard board) {
		this.board = board;
	}

	/**
	 * first call initGame
	 */
	public void play(){
		
		while(isPlaying){
			Player playerWithTurn = howHasTurn();
			playGameTurn(playerWithTurn);
			if(!board.isWinner(playerWithTurn)) passTurn();
			else{
				doWinnerActions();
				isPlaying = false;
			}
		}
	}

	public void doWinnerActions() {
		System.out.println("we have a winner");
	}
	
	@Override
	public Player howHasTurn() {
		return (order.peek().getTurn() != null) ? order.peek() : null;  
	}

	@Override
	public boolean passTurn() {
		if(order.peek().getTurn() != null){
			Player played = order.pop();
			order.peek().setTurn(played.getTurn());
			played.setTurn(null);
			order.addLast(played);
			return true;
		}
		else return false;
	}

	@Override
	public Player howIsNextInTurn() {
		return (order.peek().getTurn() != null) ? order.peekLast() : null;
	}
	
	public BackgammonBoard getBoard() {
		return board;
	}
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	public Player getSecondPlayer() {
		return secondPlayer;
	}
}
