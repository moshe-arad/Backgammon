package org.moshe.arad.game.player;

import org.moshe.arad.game.instrument.BackgammonPawn;
import org.moshe.arad.game.instrument.BlackBackgammonPawn;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.instrument.WhiteBackgammonPawn;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.moshe.arad.game.turn.Turn;

public class BackgammonPlayer extends ClassicGamePlayer {

	private String id;
	private String firstName;
	private String lastName;
	private int age;
	private BackgammonTurn turn;
	private boolean isWhite;

	@Override
	public void makePlayed(Move move) { 
		Dice first = turn.getFirstDice();
		Dice second = turn.getSecondDice();
		int fromIndex = ((BackgammonBoardLocation)move.getFrom()).getIndex();
		int toIndex = ((BackgammonBoardLocation)move.getTo()).getIndex();
		int step = fromIndex - toIndex < 0 ? (fromIndex - toIndex)*(-1) : fromIndex - toIndex;
		
		if(first.getValue() == step) first.initDice();
		else if(second.getValue() == step) second.initDice();
		else if(first.getValue() > second.getValue()) first.initDice();
		else second.initDice();
	}

	@Override
	public void rollDices() { 
		turn.getFirstDice().rollDice();
		turn.getSecondDice().rollDice();
	}

	@Override
	public boolean isCanPlayWith(BackgammonPawn pawn) {
		return (pawn != null) && ((isWhite && pawn instanceof WhiteBackgammonPawn) ||
				(!isWhite && pawn instanceof BlackBackgammonPawn));
	}

	public boolean isWhite() {
		return isWhite;
	}

	@Override
	public BackgammonTurn getTurn() {
		return turn;
	}

	@Override
	public void setTurn(Turn turn) {
		this.turn = (BackgammonTurn)turn;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
