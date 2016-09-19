package org.moshe.arad.game.classic_board.backgammon;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.moshe.arad.game.classic_board.ClassicBoardGame;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.Player;

public class Backgammon extends ClassicBoardGame {

	@Override
	public void playGameTurn(Player player) {
		BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player; 
		Scanner reader = new Scanner(System.in);
		String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName() + ": ";
		System.out.println(name + "it's your turn. roll the dices.");
		player.rollDices();
		System.out.println(name + "you rolled - " + backgammonPlayer.getTurn().getFirstDice().getValue() + ": " + backgammonPlayer.getTurn().getSecondDice().getValue());
		board.display();
		
		try {
			while(board.isHasMoreMoves(player)){
				Move move = enterNextMove(player, reader);
				if(board.isValidMove(player, move)){
					board.executeMove(player, move);
					player.makePlayed(move);
					printOutputAfterMove();
				}
				else notifyOnInvalidMove(player, move);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		System.out.println(name+": enter your next move.");
		String msg = "from where to move? (index -1:24).";
		String input = getMoveInput(name, reader, msg);
		move.setFrom(new BackgammonBoardLocation(Integer.parseInt(input)));
		msg = "where move to? (index -1:24).";
		input = getMoveInput(name, reader, msg);
		move.setTo(new BackgammonBoardLocation(Integer.parseInt(input)));
		return move;
	}

	private String getMoveInput(String name, Scanner reader, String msg) {
		System.out.println(name+": " + msg);
		String input = reader.next();
		while(!StringUtils.isNumeric(input) || Integer.parseInt(input) < -1 || Integer.parseInt(input) > 24){
			System.out.println("Your input is invalid. try again.");
			input = reader.next();
		}
		return input;
	}


	public void notifyOnInvalidMove(Player player, Move move) {		
		if(player == null) System.out.println("Player is null.");
		else{
			BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player; 
			String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName();
			System.out.println(name + ": you made invalid move. try again.");
		}
	}
	
	private void printOutputAfterMove() {
		System.out.println("After move:");
		board.display();
		System.out.println("*************************************");
	}
}
