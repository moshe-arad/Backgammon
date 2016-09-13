package org.moshe.arad.game.classic_board.backgammon;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.moshe.arad.game.classic_board.ClassicBoardGame;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Color;
import org.moshe.arad.game.instrument.Pawn;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.Turn;

public class Backgammon extends ClassicBoardGame {

	@Override
	public boolean isHasWinner() {
		Player first = super.howHasTurn();
		Player second = super.howIsNextInTurn();
		return (!board.isHasColor(first.getColor()) && board.isHasColor(second.getColor())) || 
				(board.isHasColor(first.getColor()) && !board.isHasColor(second.getColor()));
	}

	@Override
	public boolean isWinner(Player player, Board board) {
		if((player == null) || (board == null)) return false;
		Color playerColor = player.getColor();
		return !board.isHasColor(playerColor) && board.isHasColor(Color.getOpposite(playerColor));
	}

	@Override
	public boolean isHasMoreMoves(Player player) {
		if(player == null) return false;
		Turn turn = player.getTurn();
		if(turn == null) return false;
		if((turn.getFirstDice().getValue() == 0) && (turn.getSecondDice().getValue() == 0)) return false;
		else return true;
	}

	/**
	 * TODO write test
	 */
	@Override
	public Move enterNextMove(Player player) {
		if(player == null) return null;
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
		while(!StringUtils.isNumeric(input) || Integer.parseInt(input) < 0 || Integer.parseInt(input) > 23){
			System.out.println("Your input is invalid. try again.");
			input = reader.next();
		}
		return input;
	}

	@Override
	public void notifyOnInvalidMove(Player player, Move move) {
		if(player == null) System.out.println("Player is null.");
		else{
			String name = player.getFirstName() + " " + player.getLastName();
			System.out.println(name + ": you made invalid move. try again.");
		}
	}

	@Override
	public void rollDices(Player player) {
		if(player == null) throw new NullPointerException("Player is null.");
		Turn turn  = player.getTurn();
		if(turn == null) throw new NullPointerException("turn is null.");
		turn.getFirstDice().rollDice();
		turn.getSecondDice().rollDice();
	}

	@Override
	public boolean makeMove(Player player, Move move, Board board) {
		if(player == null || move == null || board == null) return false;
		Pawn pawn = board.popAtColumn(move.getFrom());
		if(pawn != null){
			if(board.setPawn(pawn, move.getTo())) return true;
			else return false;
		}
		return false;
	}

	@Override
	public boolean validMove(Player player, Move move, Board board) {
		if(player == null || move == null || board == null) return false;
		Color playerColor = player.getColor();
		Pawn fromPawn;
		Pawn toPawn;
		
		if(playerColor.equals(Color.white) && ((move.getTo() - move.getFrom()) > 0)) 
		{
			toPawn = board.peekAtColumn(move.getTo());
			fromPawn = board.peekAtColumn(move.getFrom());
			if(fromPawn == null) return false;
			else if(fromPawn.getColor().equals(Color.white))
			{
				if((toPawn == null) || toPawn.getColor().equals(Color.white)) return true;
				else return true;
			}
			else return false;
			
		}
			
		if(playerColor.equals(Color.black) && ((move.getFrom() - move.getTo()) > 0)) 
		{
			toPawn = board.peekAtColumn(move.getTo());
			fromPawn = board.peekAtColumn(move.getFrom());
			if(fromPawn == null) return false;
			else if(fromPawn.getColor().equals(Color.black))
			{
				if((toPawn == null) || toPawn.getColor().equals(Color.black)) return true;
				else return true;
			}
			else return false;
		}
		else return false;
	}

	@Override
	public void playGameTurn(Player player) {
		String name = player.getFirstName() + " " + player.getLastName() + ": ";
		System.out.println(name + "it's your turn. roll the dices.");
		rollDices(player);
		System.out.println(name + "you rolled - " + player.getTurn().getFirstDice().getValue() + ": " + player.getTurn().getSecondDice().getValue());
		board.print();
		while(isHasMoreMoves(player)){
			Move move = enterNextMove(player);
			if(validMove(player, move, super.board)) makeMove(player, move, super.board);
			else notifyOnInvalidMove(player, move);
		}
		System.out.println("After move:");
		board.print();
		System.out.println("*************************************");
	}

	@Override
	public void initGame() {
		board.initBoard();
	}
}
