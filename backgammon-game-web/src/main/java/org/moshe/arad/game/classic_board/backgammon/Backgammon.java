package org.moshe.arad.game.classic_board.backgammon;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.moshe.arad.game.classic_board.ClassicBoardGame;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Color;
import org.moshe.arad.game.instrument.Dice;
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
	 * TODO to write test, after get rid of final class Scanner 
	 * need to remove Scanner object from method signature.
	 */
	@Override
	public Move enterNextMove(Player player, Scanner reader) {
		if(player == null) return null;
		String name = player.getFirstName() + " " + player.getLastName();
		Move move = new Move();
		
		System.out.println(name+": enter your next move.");
		String msg = "from where to move? (index -1:24).";
		String input = getMoveInput(name, reader, msg);
		move.setFrom(Integer.parseInt(input));
		msg = "where move to? (index -1:24).";
		input = getMoveInput(name, reader, msg);
		move.setTo(Integer.parseInt(input));
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
			if(move.getTo() == -1 || move.getTo() == 24) return true;
			else if(board.setPawn(pawn, move.getTo())) return true;
			else return false;
		}
		return false;
	}

	@Override
	public boolean validMove(Player player, Move move, Board board) {
		if(player == null || move == null || board == null) return false;
		Color playerColor = player.getColor();
		Pawn fromPawn;
		Pawn toPawn = null;
		boolean doCleanUp = false;
		Dice firstDice = player.getTurn().getFirstDice();
		Dice secondDice = player.getTurn().getSecondDice();
		int moveStep = move.getTo() - move.getFrom();
		
		if(move.getFrom() == 24 || move.getFrom() == -1) return false;
		if(move.getFrom() == move.getTo()) return false;
		if(moveStep<0) moveStep*=-1;
		if((firstDice.getValue() == moveStep) || (secondDice.getValue() == moveStep) || ((firstDice.getValue() + secondDice.getValue() == moveStep))){
			if(move.getTo() == -1 || move.getTo() == 24){
				boolean isCanCleanUp = isCanStartCleanUp(player, board);
				if(isCanCleanUp){
					doCleanUp = true;
				}
				else return false;
			}
		
			if(playerColor.equals(Color.white) && ((move.getFrom() - move.getTo()) > 0)) 
			{
				if(!doCleanUp) toPawn = board.peekAtColumn(move.getTo());
				fromPawn = board.peekAtColumn(move.getFrom());
				if(fromPawn == null) return false;
				else if(fromPawn.getColor().equals(Color.white))
				{
					if(doCleanUp) return true;
					else if((toPawn == null) || toPawn.getColor().equals(Color.white) || (toPawn.getColor().equals(Color.black) && board.getSizeOfColumn(move.getTo()) == 1)) return true;
					else return false;
				}
				else return false;
				
			}
				
			if(playerColor.equals(Color.black) && ((move.getTo() - move.getFrom()) > 0)) 
			{
				if(!doCleanUp) toPawn = board.peekAtColumn(move.getTo());
				fromPawn = board.peekAtColumn(move.getFrom());
				if(fromPawn == null) return false;
				else if(fromPawn.getColor().equals(Color.black))
				{
					if(doCleanUp) return true;
					else if((toPawn == null) || toPawn.getColor().equals(Color.black) || (toPawn.getColor().equals(Color.white) && board.getSizeOfColumn(move.getTo()) == 1)) return true;
					else return false;
				}
				else return false;
			}
			else return false;

		}
		else return false;
	}

	/**
	 * TODO write test
	 */
	@Override
	public void playGameTurn(Player player) {
		Scanner reader = new Scanner(System.in);
		String name = player.getFirstName() + " " + player.getLastName() + ": ";
		System.out.println(name + "it's your turn. roll the dices.");
		rollDices(player);
		System.out.println(name + "you rolled - " + player.getTurn().getFirstDice().getValue() + ": " + player.getTurn().getSecondDice().getValue());
		board.print();
		while(isHasMoreMoves(player)){
			Move move = enterNextMove(player, reader);
			if(validMove(player, move, super.board)){
				if(!makeMove(player, move, super.board)) throw new RuntimeException();
				else{
					initDices(player, move);
					System.out.println("After move:");
					board.print();
					System.out.println("*************************************");
				}
			}
			else notifyOnInvalidMove(player, move);
		}
	}

	@Override
	public void initGame() {
		board.initBoard();
	}

	@Override
	public boolean initDices(Player player, Move move) {
		if((player == null) || (move == null) || (player.getTurn() == null)) return false;
		Dice first = player.getTurn().getFirstDice();
		if(first == null) return false;
		Dice second = player.getTurn().getSecondDice();
		if(second == null) return false;
		int moveStep = player.getColor().equals(Color.white) ? (move.getFrom() - move.getTo()) : (move.getTo() - move.getFrom()); 
		if(moveStep == first.getValue()) {
			first.initDiceValue();
			return true;
		}
		else if(moveStep == second.getValue()){
			second.initDiceValue();
			return true;
		}
		else return false;
	}

	@Override
	public boolean isCanStartCleanUp(Player player, Board borad) {
		if((player == null) || (borad == null)) throw new NullPointerException();
		Color color = player.getColor();
		if(color.equals(Color.white)){
			for(int i=23; i>5; i--)
				if(!borad.isEmptyColumn(i)) return false;
		}
		if(color.equals(Color.black)){
			for(int i=0; i<18; i++)
				if(!borad.isEmptyColumn(i)) return false;
		}
		return true;
	}
}
