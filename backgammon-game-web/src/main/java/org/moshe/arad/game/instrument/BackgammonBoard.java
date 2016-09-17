package org.moshe.arad.game.instrument;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.BoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;

public class BackgammonBoard implements Board {

	public static final int LENGTH = 24;
	public static final int MAX_COLUMN = 15;
	public static final int EATEN_WHITE = 24;
	public static final int EATEN_BLACK = -1;
	public static final int OUT_WHITE = -1;
	public static final int OUT_BLACK = 24;
	private List<Deque<BackgammonPawn>> board = new ArrayList<Deque<BackgammonPawn>>(LENGTH);

	private LinkedList<BackgammonPawn> eatenBlacks = new LinkedList<BackgammonPawn>();
	private LinkedList<BackgammonPawn> eatenWhites = new LinkedList<BackgammonPawn>();

	/**
	 * TODO Check usage of constructors.
	 */
	public BackgammonBoard() {
		for (int i = 0; i < LENGTH; i++)
			board.add(new ArrayDeque<BackgammonPawn>(MAX_COLUMN));
	}

	public BackgammonBoard(BackgammonBoard b) {
		if (b == null)
			throw new NullPointerException("Board parameter is null.");

		for (int i = 0; i < LENGTH; i++)
			board.add(new ArrayDeque<BackgammonPawn>(MAX_COLUMN));

		for (int i = 0; i < LENGTH; i++) {
			BackgammonPawn pawn = b.peekAtColumn(i);
			for (int j = 0; j < b.getSizeOfColumn(i); j++)
				board.get(i).push(pawn);
		}
	}

	public BackgammonBoard(LinkedList<BackgammonPawn> blacksOutsideGame, LinkedList<BackgammonPawn> whitesOutsideGame) {
		this();
		this.eatenBlacks = blacksOutsideGame;
		this.eatenWhites = whitesOutsideGame;
	}

	@Override
	public void clearBoard() {
		for (Deque<BackgammonPawn> column : board) {
			column.clear();
		}
	}

	@Override
	public void initBoard() {
		clearBoard();

		for (int i = 0; i < 2; i++)
			board.get(0).push(new BlackBackgammonPawn());

		for (int i = 0; i < 5; i++)
			board.get(5).push(new WhiteBackgammonPawn());

		for (int i = 0; i < 3; i++)
			board.get(7).push(new WhiteBackgammonPawn());
		;

		for (int i = 0; i < 5; i++)
			board.get(11).push(new BlackBackgammonPawn());

		for (int i = 0; i < 5; i++)
			board.get(12).push(new WhiteBackgammonPawn());

		for (int i = 0; i < 3; i++)
			board.get(16).push(new BlackBackgammonPawn());

		for (int i = 0; i < 5; i++)
			board.get(18).push(new BlackBackgammonPawn());

		for (int i = 0; i < 2; i++)
			board.get(23).push(new WhiteBackgammonPawn());
	}

	@Deprecated
	public boolean setPawn(BackgammonPawn pawn, int index) {
		if (pawn == null)
			return false;
		if ((index < 0) || (index > LENGTH - 1)) {
			System.out.println("Index value out of bounds.");
			return false;
		} else {
			if ((board.get(index).size() > 0) && (board.get(index).peek() != null)
					&& (!board.get(index).peek().equals(pawn))) {
				System.out.println("Can't place different kind of pawns on the same column.");
				return false;
			} else if (board.get(index).size() == MAX_COLUMN) {
				System.out.println("This column is full.");
				return false;
			} else {
				board.get(index).push(pawn);
				return true;
			}
		}
	}

	@Override
	public boolean setPawn(Pawn pawn, BoardLocation location) {
		if ((pawn == null) || (location == null)) return false;
		else {
			int locationIndex = ((BackgammonBoardLocation) location).getIndex();
			BackgammonPawn backgammonPawn = (BackgammonPawn) pawn;

			if (locationIndex < 0 || locationIndex > LENGTH - 1) {
				return false;
			} else {
				if ((board.get(locationIndex).size() > 0) && (board.get(locationIndex).peek() != null) && (!board.get(locationIndex).peek().equals(backgammonPawn))) {
					System.out.println("Can't place different kind of pawns on the same column.");
					return false;
				} else if (board.get(locationIndex).size() == MAX_COLUMN) {
					System.out.println("This column is full.");
					return false;
				} else {
					board.get(locationIndex).push(backgammonPawn);
					return true;
				}
			}
		}
	}

	/**
	 * 
	 * @param index
	 * @return return null if empty.
	 */
	@Deprecated
	public BackgammonPawn peekAtColumn(int index) {
		if ((index < 0) || (index > LENGTH - 1)) {
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}
		return board.get(index).peek();
	}

	@Deprecated
	public int getSizeOfColumn(int index) {
		if ((index < 0) || (index > LENGTH - 1)) {
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}
		return board.get(index).size();
	}

	@Deprecated
	public boolean isEmptyColumn(int index) {
		if ((index < 0) || (index > LENGTH - 1)) {
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}
		return board.get(index).isEmpty();
	}

	@Deprecated
	public BackgammonPawn popAtColumn(int index) {
		if ((index < 0) || (index > LENGTH - 1)) {
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}

		try {
			return board.get(index).pop();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * 
	 * @param index
	 * @return return null if empty.
	 */
	public BackgammonPawn peekAtColumn(BackgammonBoardLocation location) {
		if(location == null) throw new NullPointerException("location is null.");
		else{
			int locationIndex = location.getIndex();
			
			if (locationIndex < 0 || locationIndex > LENGTH - 1) {
				throw new IndexOutOfBoundsException("Index value out of bounds.");
			}
			return board.get(locationIndex).peek();
		}
	}

	public int getSizeOfColumn(BackgammonBoardLocation location) {
		if(location == null) return -1;
		else{
			int locationIndex = location.getIndex();
			
			if (locationIndex < 0 || locationIndex > (LENGTH - 1)) {
				return -1;
			}
			return board.get(locationIndex).size();
		}		
	}

	public boolean isEmptyColumn(BackgammonBoardLocation location) {
		if(location == null) return false;
		else{
			int locationIndex = location.getIndex();
			
			if (locationIndex < 0 || locationIndex > (LENGTH - 1)) {
				return false;
			}
			return board.get(locationIndex).isEmpty();
		}
	}

	public BackgammonPawn popAtColumn(BackgammonBoardLocation location) {
		if(location == null) throw new NullPointerException("location is null.");
		else{
			int locationIndex = location.getIndex();
			
			if (locationIndex < 0 || locationIndex > (LENGTH - 1)) {
				throw new IndexOutOfBoundsException("Index value out of bounds.");
			}

			try {
				return board.get(locationIndex).pop();
			} catch (NoSuchElementException ex) {
				return null;
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BackgammonBoard other = (BackgammonBoard) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!this.checkBoardEquality(other))
			return false;
		return true;
	}

	@Override
	public String toString() {
		BackgammonBoard boardCopy = new BackgammonBoard(this);
		StringBuilder sb = new StringBuilder();
		sb.append("       ** The Board **").append("\n");

		sb.append("  ##############################").append("\n");
		sb.append("  # 1 1 0 0 0 0    0 0 0 0 0 0 #").append("\n");
		sb.append("  # 1 0 9 8 7 6    5 4 3 2 1 0 #").append("\n");
		sb.append("  #-------------  -------------#").append("\n");
		printUpperBoard(boardCopy, sb);

		sb.append("  #                            #\n");

		printBottomBoard(boardCopy, sb);
		sb.append("  #-------------  -------------#").append("\n");
		sb.append("  # 1 1 1 1 1 1    1 1 2 2 2 2 #").append("\n");
		sb.append("  # 2 3 4 5 6 7    8 9 0 1 2 3 #").append("\n");
		sb.append("  ##############################").append("\n");

		printHowManyPawnsOutside();

		return sb.toString();
	}

	@Override
	public void display(){
		System.out.println(this);
	}
	
	
	@Deprecated
	public void print() {
		BackgammonBoard boardCopy = new BackgammonBoard(this);
		StringBuilder sb = new StringBuilder();
		sb.append("       ** The Board **").append("\n");

		sb.append("  ##############################").append("\n");
		sb.append("  # 1 1 0 0 0 0    0 0 0 0 0 0 #").append("\n");
		sb.append("  # 1 0 9 8 7 6    5 4 3 2 1 0 #").append("\n");
		sb.append("  #-------------  -------------#").append("\n");
		printUpperBoard(boardCopy, sb);

		sb.append("  #                            #\n");

		printBottomBoard(boardCopy, sb);
		sb.append("  #-------------  -------------#").append("\n");
		sb.append("  # 1 1 1 1 1 1    1 1 2 2 2 2 #").append("\n");
		sb.append("  # 2 3 4 5 6 7    8 9 0 1 2 3 #").append("\n");
		sb.append("  ##############################").append("\n");

		printHowManyPawnsOutside();

		System.out.println(sb.toString());
	}

	/*
	public void printHowManyPawnsOutside() {
		printHowManyPawnsOutside(new StringBuilder());
	}

	private void printHowManyPawnsOutside(StringBuilder sb) {
		if (blacksOutsideGame.size() > 0)
			sb.append("  There are " + blacksOutsideGame.size() + " black pawns outside the game.").append("\n");
		if (whitesOutsideGame.size() > 0)
			sb.append("  There are " + whitesOutsideGame.size() + " white pawns outside the game.").append("\n");
	}
	*/
	
	private String printHowManyPawnsOutside() {
		StringBuilder sb = new StringBuilder();
		
		if (eatenBlacks.size() > 0)
			sb.append("  There are " + eatenBlacks.size() + " black pawns outside the game.").append("\n");
		if (eatenWhites.size() > 0)
			sb.append("  There are " + eatenWhites.size() + " white pawns outside the game.").append("\n");
		return sb.toString();
	}

	/**
	 * 
	 * TODO pawn.equals(color)
	 */
	@Deprecated
	public boolean isHasColor(Color color) {
		for (Deque<BackgammonPawn> column : board) {
			BackgammonPawn pawn = column.peek();
			if (pawn != null && pawn.equals(color))
				return true;
		}
		return false;
	}
	
	public boolean isHasBackgammonPawnType(BackgammonPawn other) {
		for (Deque<BackgammonPawn> column : board) {
			BackgammonPawn pawn = column.peek();
			if (pawn != null && pawn.equals(other))
				return true;
		}
		return false;
	}

	/**
	 * TODO test
	 */
	public void clearPawnsOutsideGame() {
		eatenBlacks.clear();
		eatenWhites.clear();
	}

	private void printUpperBoard(BackgammonBoard boardCopy, StringBuilder sb) {
		BackgammonBoardLocation backgammonLocation;
		
		for (int i = 0; i < 7; i++) {
			sb.append("  #");
			for (int j = 11; j > -1; j--) {
				backgammonLocation = new BackgammonBoardLocation(j);
				if (i == 0) {					
					int pawnCount = boardCopy.getSizeOfColumn(backgammonLocation);
					String pawnCountStr = Integer.toHexString(pawnCount).toUpperCase();
					sb.append(" ").append(pawnCountStr);
					if (j == 6)
						sb.append("   ");
					if (j == 0)
						sb.append(" ");
				} else if (i == 1) {
					sb.append("-------------  -------------");
					break;
				} else {
					if (boardCopy.isEmptyColumn(backgammonLocation)) {
						sb.append("|*");
					} else {
						BackgammonPawn p = boardCopy.popAtColumn(backgammonLocation);
						if (p instanceof BlackBackgammonPawn) {
							sb.append("|B");
						} else {
							sb.append("|W");
						}
					}

					if (j == 6)
						sb.append("|  ");
					if (j == 0)
						sb.append("|");
				}
			}
			sb.append("#\n");
		}
	}

	private void printBottomBoard(BackgammonBoard boardCopy, StringBuilder sb) {
		BackgammonBoardLocation backgammonLocation;
		StringBuilder sbPawns = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			sb.append("  #");

			if (i == 6) {
				sb.append(sbPawns.toString()).append(" #\n");
				continue;
			} else if (i == 5) {
				sb.append("-------------  -------------#").append("\n");
				continue;
			}

			for (int j = 12; j < 24; j++) {
				backgammonLocation = new BackgammonBoardLocation(j);
				if (i == 0) {
					int pawnCount = boardCopy.getSizeOfColumn(backgammonLocation);
					String pawnCountStr = Integer.toHexString(pawnCount).toUpperCase();
					sbPawns.append(" ").append(pawnCountStr);
					if (j == 17)
						sbPawns.append("   ");
				}

				if (i + 1 + boardCopy.getSizeOfColumn(backgammonLocation) <= 5) {
					sb.append("|*");
				} else {
					BackgammonPawn p = boardCopy.popAtColumn(backgammonLocation);
					if (p instanceof BlackBackgammonPawn) {
						sb.append("|B");
					} else {
						sb.append("|W");
					}
				}

				if (j == 17)
					sb.append("|  ");
				if (j == 23)
					sb.append("|");
			}
			sb.append("#\n");
		}
	}

	private boolean checkBoardEquality(BackgammonBoard other) {
		BackgammonBoardLocation backgammonLOcation;
		
		for (int i = 0; i < board.size(); i++) {
			backgammonLOcation = new BackgammonBoardLocation(i);
			if (board.get(i).size() != other.getSizeOfColumn(backgammonLOcation))
				return false;
			else if (board.get(i).size() > 0) {
				BackgammonPawn pawn = board.get(i).peek();
				BackgammonPawn otherPawn = other.peekAtColumn(backgammonLOcation);
				if(!pawn.equals(otherPawn)) return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * TODO test all these.
	 */
	public int getHowManyBlacksOutsideGame() {
		return eatenBlacks.size();
	}

	public int getHowManyWhitesOutsideGame() {
		return eatenWhites.size();
	}

	public boolean addBlackOutsideGame(BackgammonPawn black) {
		return eatenBlacks.add(black);
	}

	public boolean addWhiteOutsideGame(BackgammonPawn white) {
		return eatenWhites.add(white);
	}

	public BackgammonPawn popBlackOutsideGame() {
		return eatenBlacks.pop();
	}

	public BackgammonPawn popWhiteOutsideGame() {
		return eatenWhites.pop();
	}

	public BackgammonPawn peekBlackOutsideGame() {
		return eatenBlacks.peek();
	}

	public BackgammonPawn peekWhiteOutsideGame() {
		return eatenWhites.peek();
	}

	@Override
	public boolean executeMove(Player player, Move move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidMove(Player player, Move move) {
		int fromIndex, toIndex, step;
		
		if(player == null || move == null) return false;
		else{
			fromIndex = ((BackgammonBoardLocation)move.getFrom()).getIndex();
			toIndex = ((BackgammonBoardLocation)move.getTo()).getIndex();
			BackgammonBoardLocation toLocation = ((BackgammonBoardLocation)move.getTo());
			step = fromIndex - toIndex < 0 ? (fromIndex - toIndex)*(-1) : fromIndex - toIndex;
			BackgammonPawn pawnFrom = board.get(fromIndex).peek();
			Dice firstDice = player.getTurn().getFirstDice();
			Dice secondDice = player.getTurn().getSecondDice();
	
			if(!pawnFrom.isAbleToDo(move)) return false;
			if(firstDice.getValue() != step || secondDice.getValue() != step) return false;
			if(!player.isCanPlayWith(pawnFrom)) return false;
			if(!eatenPawnsValidation(fromIndex, pawnFrom)) return false;
			if(!isPawnCanBeSetIn(pawnFrom, toLocation)) return false;
			if(!isEatenPawnCanComeBack(fromIndex, pawnFrom, toLocation)) return false; 
			if(!isCanTakePawnOutside(toIndex)) return false;
		}
		return true;
	}

	private boolean eatenPawnsValidation(int fromIndex, BackgammonPawn pawnFrom){
		if(fromIndex != EATEN_WHITE && eatenWhites.size() > 0 && pawnFrom.equals(eatenWhites.peek())) return false;
		if(fromIndex != EATEN_BLACK && eatenBlacks.size() > 0 && pawnFrom.equals(eatenBlacks.peek())) return false;
		return true;
	}
	
	private boolean isPawnCanBeSetIn(BackgammonPawn pawn, BackgammonBoardLocation to){
		BackgammonPawn other = board.get(to.getIndex()).peek();
		int sizeAtTo = board.get(to.getIndex()).size();
		
		if(!other.equals(pawn) && sizeAtTo > 1) return false;
		else return true;
	}
	
	private boolean isEatenPawnCanComeBack(int fromIndex, BackgammonPawn pawnFrom, BackgammonBoardLocation toLocation){
		if(fromIndex == EATEN_WHITE && !isEatenWhiteCanComeBack(pawnFrom, toLocation)) return false;
		if(fromIndex == EATEN_BLACK && !isEatenBlackCanComeBack(pawnFrom, toLocation)) return false;
		return true;
	}
	
	private boolean isEatenWhiteCanComeBack(BackgammonPawn pawn, BackgammonBoardLocation to){
		if(eatenWhites.size() == 0) return false;
		return isPawnCanBeSetIn(pawn, to);
	}
	
	private boolean isEatenBlackCanComeBack(BackgammonPawn pawn, BackgammonBoardLocation to){
		if(eatenBlacks.size() == 0) return false;
		return isPawnCanBeSetIn(pawn, to);
	}
	
	private boolean isCanTakePawnOutside(int toIndex){
		if(toIndex == OUT_WHITE && !isWhiteCanTakePawnOutside()) return false;
		if(toIndex == OUT_BLACK && !isBlackCanTakePawnOutside()) return false;
		return true;
	}
	
	private boolean isWhiteCanTakePawnOutside(){
		for(int i=6; i<24; i++){
			if(board.get(i).peek() instanceof WhiteBackgammonPawn) return false;
		}
		return true;
	}
	
	private boolean isBlackCanTakePawnOutside(){
		for(int i=0; i<18; i++){
			if(board.get(i).peek() instanceof BlackBackgammonPawn) return false;
		}
		return true;
	}
	
	@Override
	public boolean isWinner(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHasMoreMoves(Player player) {
		// TODO Auto-generated method stub
		return false;
	}
}
