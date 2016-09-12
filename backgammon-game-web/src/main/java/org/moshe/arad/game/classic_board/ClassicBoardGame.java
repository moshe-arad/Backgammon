package org.moshe.arad.game.classic_board;

import java.util.LinkedList;

import javax.annotation.Resource;

import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.player.Player;
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
	@Resource
	private LinkedList<Player> order;
	
	private boolean isPlaying = true;
	
	public void play(){
		
		while(isPlaying){
			Player playerWithTurn = howHasTurn();
			playGameTurn(playerWithTurn);
			if(!isWinner(playerWithTurn)) passTurn();
			else{
				doWinnerActions();
				isPlaying = false;
			}
		}
	}

	public void playGameTurn(Player player) {
		rollDices(new Dice[]{firstDice, secondDice});
	}

	public void doWinnerActions() {
		// TODO Auto-generated method stub
		
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
