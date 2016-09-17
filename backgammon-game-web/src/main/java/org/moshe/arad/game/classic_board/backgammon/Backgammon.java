package org.moshe.arad.game.classic_board.backgammon;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.moshe.arad.game.classic_board.ClassicBoardGame;
import org.moshe.arad.game.instrument.BackgammonBoard;
import org.moshe.arad.game.instrument.Color;
import org.moshe.arad.game.instrument.BackgammonDice;
import org.moshe.arad.game.instrument.BackgammonPawn;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.BackgammonTurn;

public class Backgammon extends ClassicBoardGame {

	public Backgammon(BackgammonBoard board) {
		super(board);
	}

	@Override
	public boolean isHasWinner() {
		Player first = super.howHasTurn();
		Player second = super.howIsNextInTurn();
		if(board.getHowManyBlacksOutsideGame() > 0 || board.getHowManyWhitesOutsideGame() > 0) return false;
		return (!board.isHasColor(first.getColor()) && board.isHasColor(second.getColor())) || 
				(board.isHasColor(first.getColor()) && !board.isHasColor(second.getColor()));
	}

	@Override
	public boolean isWinner(Player player) {
		if((player == null) || (board == null)) return false;
		if(board.getHowManyBlacksOutsideGame() > 0 || board.getHowManyWhitesOutsideGame() > 0) return false;
		Color playerColor = player.getColor();
		return !board.isHasColor(playerColor) && board.isHasColor(Color.getOpposite(playerColor));
	}

	@Override
	public boolean isHasMoreMoves(Player player, BackgammonBoard board) {
		if(player == null) return false;
		BackgammonTurn turn = player.getTurn();
		if(turn == null) return false;
		BackgammonDice firstDice = turn.getFirstDice();
		BackgammonDice secondDice = turn.getSecondDice();
		
		Color playerColor = player.getColor();
		
		if((firstDice.getValue() == 0) && (secondDice.getValue() == 0)) return false;
		if(playerColor.equals(Color.white)){
			if(board.getHowManyWhitesOutsideGame() > 0){
				if(board.isEmptyColumn(24 - firstDice.getValue()) || board.isEmptyColumn(24 - secondDice.getValue())) return true;
				else if(board.peekAtColumn(24 - firstDice.getValue()).getColor().equals(Color.white)) return true; 
				else if(board.peekAtColumn(24 - secondDice.getValue()).getColor().equals(Color.white)) return true;
				else if(board.peekAtColumn(24 - firstDice.getValue()).getColor().equals(Color.black) && board.getSizeOfColumn(24 - firstDice.getValue()) == 1) return true;
				else if(board.peekAtColumn(24 - secondDice.getValue()).getColor().equals(Color.black) && board.getSizeOfColumn(24 - secondDice.getValue()) == 1) return true;
			}
			else{
				for(int i=BackgammonBoard.LENGTH -1; i>-1; i--){
					if(!board.isEmptyColumn(i) && board.peekAtColumn(i).getColor().equals(Color.white)){
						if(board.isEmptyColumn(i - firstDice.getValue())) return true;
						else if(board.peekAtColumn(i - firstDice.getValue()).getColor().equals(Color.white)) return true;
						else if(board.peekAtColumn(i - firstDice.getValue()).getColor().equals(Color.black) && board.getSizeOfColumn(i - firstDice.getValue()) == 1) return true;
						/*************************/
						else if(board.isEmptyColumn(i - secondDice.getValue())) return true;
						else if(board.peekAtColumn(i - secondDice.getValue()).getColor().equals(Color.white)) return true;
						else if(board.peekAtColumn(i - secondDice.getValue()).getColor().equals(Color.black) && board.getSizeOfColumn(i - secondDice.getValue()) == 1) return true;
					}
				}
			}
		}
		/**************************************************/
		else if(playerColor.equals(Color.black)){
			if(board.getHowManyBlacksOutsideGame() > 0){
				if(board.isEmptyColumn(-1 + firstDice.getValue()) || board.isEmptyColumn(-1 + secondDice.getValue())) return true;
				else if(board.peekAtColumn(-1 + firstDice.getValue()).getColor().equals(Color.black)) return true; 
				else if(board.peekAtColumn(-1 + secondDice.getValue()).getColor().equals(Color.black)) return true;
				else if(board.peekAtColumn(-1 + firstDice.getValue()).getColor().equals(Color.white) && board.getSizeOfColumn(-1 + firstDice.getValue()) == 1) return true;
				else if(board.peekAtColumn(-1 + secondDice.getValue()).getColor().equals(Color.white) && board.getSizeOfColumn(-1 + secondDice.getValue()) == 1) return true;
			}
			else{
				for(int i=0; i<BackgammonBoard.LENGTH; i++){
					if(!board.isEmptyColumn(i) && board.peekAtColumn(i).getColor().equals(Color.black)){
						if(board.isEmptyColumn(i + firstDice.getValue())) return true;
						else if(board.peekAtColumn(i + firstDice.getValue()).getColor().equals(Color.black)) return true;
						else if(board.peekAtColumn(i + firstDice.getValue()).getColor().equals(Color.white) && board.getSizeOfColumn(i + firstDice.getValue()) == 1) return true;
						/*************************/
						else if(board.isEmptyColumn(i + secondDice.getValue())) return true;
						else if(board.peekAtColumn(i + secondDice.getValue()).getColor().equals(Color.black)) return true;
						else if(board.peekAtColumn(i + secondDice.getValue()).getColor().equals(Color.white) && board.getSizeOfColumn(i + secondDice.getValue()) == 1) return true;
					}
				}
			}
		}
		return false;
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
		BackgammonTurn turn  = player.getTurn();
		if(turn == null) throw new NullPointerException("turn is null.");
		turn.getFirstDice().rollDice();
		turn.getSecondDice().rollDice();
	}

	@Override
	public boolean makeMove(Player player, Move move, BackgammonBoard board) {
		if(player == null || move == null || board == null) return false;
		BackgammonPawn pawn;
		
		if(move.getFrom() != -1 && move.getFrom() != 24) pawn = board.popAtColumn(move.getFrom());
		else if(move.getFrom() == -1){
			pawn = board.peekBlackOutsideGame();
		}
		else if(move.getFrom() == 24){
			pawn = board.peekWhiteOutsideGame();
		}
		else return false;
		
		BackgammonPawn toPawn = (move.getTo() != -1 && move.getTo() != 24) ? board.peekAtColumn(move.getTo()) : null;
		if(pawn != null){
			if(move.getTo() == -1 || move.getTo() == 24) return true;
			else if(toPawn != null){
				if(toPawn.getColor().equals(Color.white) && (board.getSizeOfColumn(move.getTo()) == 1) && pawn.getColor().equals(Color.black)) {
					board.addWhiteOutsideGame(toPawn);
					board.popAtColumn(move.getTo());
					board.setPawn(pawn, move.getTo());
					if(move.getFrom() == -1) board.popBlackOutsideGame();
					else if(move.getFrom() == 24) board.popWhiteOutsideGame();
					return true;
				}
				else if (toPawn.getColor().equals(Color.black) && (board.getSizeOfColumn(move.getTo()) == 1) && pawn.getColor().equals(Color.white)) {
					board.addBlackOutsideGame(toPawn);
					board.popAtColumn(move.getTo());
					board.setPawn(pawn, move.getTo());
					if(move.getFrom() == -1) board.popBlackOutsideGame();
					else if(move.getFrom() == 24) board.popWhiteOutsideGame();
					return true;
				}
				else if(toPawn.getColor().equals(pawn.getColor())){
					board.setPawn(pawn, move.getTo());
					if(move.getFrom() == -1) board.popBlackOutsideGame();
					else if(move.getFrom() == 24) board.popWhiteOutsideGame();
					return true;
				}
				else return false;
			}
			else if(board.setPawn(pawn, move.getTo())){
				if(move.getFrom() == -1) board.popBlackOutsideGame();
				else if(move.getFrom() == 24) board.popWhiteOutsideGame();
				return true;
			}
			else return false;
		}
		return false;
	}

	@Override
	public boolean validMove(Player player, Move move, BackgammonBoard board) {
		if(player == null || move == null || board == null) return false;
		Color playerColor = player.getColor();
		BackgammonPawn fromPawn;
		BackgammonPawn toPawn = null;
		boolean doCleanUp = false;
		BackgammonDice firstDice = player.getTurn().getFirstDice();
		BackgammonDice secondDice = player.getTurn().getSecondDice();
		int moveStep = move.getTo() - move.getFrom();
		
		if(move.getFrom() == move.getTo()) return false;
		if(moveStep<0) moveStep*=-1;
		if(move.getFrom() == 24 || move.getFrom() == -1){
			if(move.getFrom() == 24){
				if(playerColor.equals(Color.white) && board.getHowManyWhitesOutsideGame() > 0){
					if((firstDice.getValue() == moveStep) || (secondDice.getValue() == moveStep) || (firstDice.getValue() + secondDice.getValue() == moveStep)){
						if(board.peekAtColumn(move.getTo()) == null || board.peekAtColumn(move.getTo()).getColor().equals(Color.white)) return true;
						else if(board.peekAtColumn(move.getTo()).getColor().equals(Color.black)){
							if((board.getSizeOfColumn(move.getTo()) > 1)) return false;
							else if((board.getSizeOfColumn(move.getTo()) == 1)) return true;
						}						
						else return false;
					}
					else return false;
				}
				else return false;
			}
			else if(move.getFrom() == -1){
				if(playerColor.equals(Color.black) && board.getHowManyBlacksOutsideGame() > 0){
					if((firstDice.getValue() == moveStep) || (secondDice.getValue() == moveStep) || (firstDice.getValue() + secondDice.getValue() == moveStep)){
						if(board.peekAtColumn(move.getTo()) == null || board.peekAtColumn(move.getTo()).getColor().equals(Color.black)) return true;
						else if(board.peekAtColumn(move.getTo()).getColor().equals(Color.white)){
							if((board.getSizeOfColumn(move.getTo()) > 1)) return false;
							else if((board.getSizeOfColumn(move.getTo()) == 1)) return true;
						}
						else return false;
					}
					else return false;
				}
				else return false;
			}
			else return false;
		}
		else{
			if(playerColor.equals(Color.white) && board.getHowManyWhitesOutsideGame() > 0) return false;
			if(playerColor.equals(Color.black) && board.getHowManyBlacksOutsideGame() > 0) return false;
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
		return false;
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
		board.printHowManyPawnsOutside();
		while(isHasMoreMoves(player, board)){
			Move move = enterNextMove(player, reader);
			if(validMove(player, move, super.board)){
				if(!makeMove(player, move, super.board)) throw new RuntimeException();
				else{
					// insteand of what's below ------> player.makePlayed(move);
					initDices(player, move);
					printOutputAfterMove();
				}
			}
			else notifyOnInvalidMove(player, move);
		}
	}


	private void printOutputAfterMove() {
		System.out.println("After move:");
		board.print();
		board.printHowManyPawnsOutside();
		System.out.println("*************************************");
	}

	@Override
	public void initGame() {
		board.initBoard();
	}

	@Override
	public boolean initDices(Player player, Move move) {
		if((player == null) || (move == null) || (player.getTurn() == null)) return false;
		BackgammonDice first = player.getTurn().getFirstDice();
		if(first == null) return false;
		BackgammonDice second = player.getTurn().getSecondDice();
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
	public boolean isCanStartCleanUp(Player player, BackgammonBoard borad) {
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

	@Override
	public void printHowManyPawnsAreOutside() {
		board.printHowManyPawnsOutside();
	}
	
	/**
	 * all of these are to be private to backgammon 
	 */
	
	/**
	 * 
	 * TODO implement this.
	 */
	public boolean isCanStartCleanUp(){
		
	}
	
	/**
	 * 
	 * TODO implement this.
	 */
	public void printHowManyPawnsAreOutside(){
		
	}
	
	/**
	 * 
	 * TODO implement this. and to be removed.
	 */
	public Move enterNextMove(Player player, Scanner reader){
		
	}
	
	/**
	 * 
	 * TODO implement this.
	 */
	public void notifyOnInvalidMove(Player player, Move move);
}
