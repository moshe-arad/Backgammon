package org.moshe.arad.game.classic_board;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.moshe.arad.game.BasicGameable;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.ClassicGamePlayer;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.TurnOrderGameable;

public abstract class ClassicBoardGame implements BasicGameable, TurnOrderGameable{

	protected Board board;
	@Resource
	private Player firstPlayer;
	@Resource
	private Player secondPlayer;
	@Resource
	private LinkedList<ClassicGamePlayer> order;
	private boolean isPlaying = true;

	@Override
	public void initGame() {
		board.initBoard();
	}
	
	@Override
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

	@Override
	public void doWinnerActions() {
		System.out.println("we have a winner");
	}
	
	@Override
	public ClassicGamePlayer howHasTurn() {
		return (order.peek().getTurn() != null) ? order.peek() : null;  
	}

	@Override
	public boolean passTurn() {
		if(order.peek().getTurn() != null){
			ClassicGamePlayer played = order.pop();
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
	
	public Board getBoard() {
		return board;
	}
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	public Player getSecondPlayer() {
		return secondPlayer;
	}
}
