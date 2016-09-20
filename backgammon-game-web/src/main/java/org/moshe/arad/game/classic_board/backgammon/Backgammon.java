package org.moshe.arad.game.classic_board.backgammon;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.game.classic_board.ClassicBoardGame;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.Player;

public class Backgammon extends ClassicBoardGame {

	private final Logger logger = LogManager.getLogger("org.moshe.arad");
	
	@Override
	public void playGameTurn(Player player) {
		BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player; 
		Dice first = backgammonPlayer.getTurn().getFirstDice();
		Dice second = backgammonPlayer.getTurn().getSecondDice();
		Scanner reader = new Scanner(System.in);
		String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName() + ": ";
		
		logger.info(name + "it's your turn. roll the dices.");
		player.rollDices();
		logger.info(name + "you rolled - " + first.getValue() + ": " + second.getValue());
		
		try {
			while(isCanKeepPlay(player)){
				logger.info("The board as follows:");
				logger.info(board);
				Move move = enterNextMove(player, reader);
				if(board.isValidMove(player, move)){
					board.executeMove(player, move);
					player.makePlayed(move);
					logger.info("A move was made...");
					logger.info("************************************");
				}
				else notifyOnInvalidMove(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isCanKeepPlay(Player player) throws Exception {
		return !board.isWinner(player) && board.isHasMoreMoves(player);
	}
	
	/**
	 * TODO to write test, after get rid of final class Scanner 
	 * need to remove Scanner object from method signature.
	 */

	private Move enterNextMove(Player player, Scanner reader) {
		if(player == null) return null;
		BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player; 
		String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName();
		Move move = new Move();
		
		logger.info(name+": enter your next move.");
		String msg = "from where to move? (index -1:24).";
		String input = getMoveInput(name, reader, msg);
		move.setFrom(new BackgammonBoardLocation(Integer.parseInt(input)));
		msg = "where move to? (index -1:24).";
		input = getMoveInput(name, reader, msg);
		move.setTo(new BackgammonBoardLocation(Integer.parseInt(input)));
		return move;
	}

	private String getMoveInput(String name, Scanner reader, String msg) {
		logger.info(name+": " + msg);
		String input = reader.next();
		while(!NumberUtils.isNumber(input) || Integer.parseInt(input) <= -2 || Integer.parseInt(input) >= 25){
			logger.warn("Your input is invalid. try again.");
			input = reader.next();
		}
		return input;
	}


	public void notifyOnInvalidMove(Player player) {		
		if(player == null) logger.error("Player is null.");
		else{
			BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player; 
			String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName();
			logger.warn(name + ": you made invalid move. try again.");
		}
	}
}
