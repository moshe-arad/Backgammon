package org.moshe.arad.game.classic_board.backgammon;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.moshe.arad.game.classic_board.ClassicBoardGame;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Color;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.Turn;

public class Backgammon extends ClassicBoardGame {

	@Override
	public boolean isHasWinner() {
		Player first = super.howHasTurn();
		Player second = super.howIsNextInTurn();
		return (!board.isHasColor(first.getColor()) || !board.isHasColor(second.getColor()));
	}

	@Override
	public boolean isWinner(Player player, Board board) {
		Color playerColor = player.getColor();
		return !board.isHasColor(playerColor);
	}

	@Override
	public boolean isHasMoreMoves(Player player) {
		Turn turn = player.getTurn();
		if((turn.getFirstDice().getValue() == 0) && (turn.getSecondDice().getValue() == 0)) return false;
		else return true;
	}

	@Override
	public Move enterNextMove(Player player) {
		String name = player.getFirstName() + " " + player.getLastName();
		Scanner reader = new Scanner(System.in);
		Move move = new Move();
		
		System.out.println(name+": enter your next move.");
		String msg = "from where to move? (index 0-23).";
		String input = getMoveInput(name, reader, msg);
		move.setFrom(Integer.parseInt(input));
		msg = "where move to? (index 0-23).";
		input = getMoveInput(name, reader, msg);
		move.setTo(Integer.parseInt(input));
		return move;
	}

	private String getMoveInput(String name, Scanner reader, String msg) {
		System.out.println(name+": " + msg);
		String input = reader.next();
		while(!StringUtils.isNumeric(input) && (Integer.parseInt(input) < 0 || Integer.parseInt(input) > 23)){
			System.out.println("Your input is invalid. try again.");
		}
		return input;
	}

	@Override
	public void notifyOnInvalidMove(Player player, Move move) {
		String name = player.getFirstName() + " " + player.getLastName();
		System.out.println(name + ": you made invalid move. try again.");
	}

	@Override
	public void rollDices(Player player) {
		Turn turn  = player.getTurn();
		turn.getFirstDice().rollDice();
		turn.getSecondDice().rollDice();
	}

	@Override
	public boolean makeMove(Player player, Move move, Board board) {
		Color pawn = board.popAtColumn(move.getFrom());
		if(pawn != null){
			if(board.setPawn(pawn, move.getTo())) return true;
			else return false;
		}
		return false;
	}

	@Override
	public boolean validMove(Player player, Move move, Board board) {
		Color playerColor = player.getColor();
		Color fromColor;
		Color toColor;
		
		if(playerColor.equals(Color.white) && ((move.getTo() - move.getFrom()) > 0)) 
		{
			toColor = board.peekAtColumn(move.getTo());
			fromColor = board.peekAtColumn(move.getFrom());
			if(toColor.equals(Color.white) && fromColor.equals(Color.white)) return true;
			else return false;
		}
			
		if(playerColor.equals(Color.black) && ((move.getFrom() - move.getTo()) > 0)) 
		{
			toColor = board.peekAtColumn(move.getTo());
			fromColor = board.peekAtColumn(move.getFrom());
			if(toColor.equals(Color.black) && fromColor.equals(Color.black)) return true;
			else return false;
		}
		else return false;
	}

	@Override
	public void playGameTurn(Player player) {
		rollDices(player);
		while(isHasMoreMoves(player)){
			Move move = enterNextMove(player);
			if(validMove(player, move, super.board)) makeMove(player, move, super.board);
			else notifyOnInvalidMove(player, move);
		}
	}
}
