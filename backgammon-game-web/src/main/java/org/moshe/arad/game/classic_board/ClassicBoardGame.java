package org.moshe.arad.game.classic_board;

import javax.annotation.Resource;

import org.moshe.arad.game.BasicGame;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.TurnOrderable;

public abstract class ClassicBoardGame extends BasicGame {

	protected Board board;
	@Resource
	private Player firstPlayer;
	@Resource
	private Player secondPlayer;
	
	private TurnOrderable turnOrderManager;
	
	private boolean isPlaying = true;

	@Override
	public void initGame() {
		board.initBoard();
	}
	
	@Override
	public void play(){
		try{
			while(isPlaying){
				Player playerWithTurn = turnOrderManager.howHasTurn();
				playGameTurn(playerWithTurn);
				if(!isHasWinner()) turnOrderManager.passTurn();
				else isPlaying = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doWinnerActions() {
		System.out.println("we have a winner");
	}

	@Override
	public boolean isHasWinner() {
		try{
			BackgammonPlayer first = (BackgammonPlayer)turnOrderManager.howHasTurn();
			BackgammonPlayer second = (BackgammonPlayer)turnOrderManager.howIsNextInTurn();
			return board.isWinner(first) || board.isWinner(second);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public abstract void playGameTurn(Player player);
	
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
