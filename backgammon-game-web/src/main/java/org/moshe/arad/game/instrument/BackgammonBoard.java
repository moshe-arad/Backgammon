package org.moshe.arad.game.instrument;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;

public class BackgammonBoard implements Board {

	public static final int LENGTH = 24;
	public static final int MAX_COLUMN = 15;
	private List<Deque<BackgammonPawn>> board = new ArrayList<Deque<BackgammonPawn>>(LENGTH);
	
	private LinkedList<BackgammonPawn> blacksOutsideGame = new LinkedList<BackgammonPawn>();
	private LinkedList<BackgammonPawn> whitesOutsideGame = new LinkedList<BackgammonPawn>();
	
	public BackgammonBoard() {
		for(int i=0; i<LENGTH; i++)
			board.add(new ArrayDeque<BackgammonPawn>(MAX_COLUMN));
	}
	
	public BackgammonBoard(BackgammonBoard b){
		if(b == null) throw new NullPointerException("Board parameter is null.");
		
		for(int i=0; i<LENGTH; i++)
			board.add(new ArrayDeque<BackgammonPawn>(MAX_COLUMN));
		
		for(int i=0; i<LENGTH; i++){
			BackgammonPawn pawn = b.peekAtColumn(i);
			for(int j=0; j<b.getSizeOfColumn(i); j++)
				board.get(i).push(pawn);
		}
	}
	
	
	public BackgammonBoard(LinkedList<BackgammonPawn> blacksOutsideGame, LinkedList<BackgammonPawn> whitesOutsideGame) {
		this();
		this.blacksOutsideGame = blacksOutsideGame;
		this.whitesOutsideGame = whitesOutsideGame;
	}

	public void clearBoard(){
		for(Deque<BackgammonPawn> column:board){
			column.clear();
		}
	}
	
	public void initBoard(){
		for(Deque<BackgammonPawn> column:board){
			column.clear();
		}
				
		for(int i=0; i<2; i++)
			board.get(0).push(new BlackBackgammonPawn());
		
		for(int i=0; i<5; i++)
			board.get(5).push(new WhiteBackgammonPawn());
		
		for(int i=0; i<3; i++)
			board.get(7).push(new WhiteBackgammonPawn());;
		
		for(int i=0; i<5; i++)
			board.get(11).push(new BlackBackgammonPawn());
		
		for(int i=0; i<5; i++)
			board.get(12).push(new WhiteBackgammonPawn());
		
		for(int i=0; i<3; i++)
			board.get(16).push(new BlackBackgammonPawn());
		
		for(int i=0; i<5; i++)
			board.get(18).push(new BlackBackgammonPawn());
		
		for(int i=0; i<2; i++)
			board.get(23).push(new WhiteBackgammonPawn());
	}
	
	public boolean setPawn(BackgammonPawn pawn, int index){
		if(pawn == null) return false;
		if((index < 0) || (index > LENGTH-1)){
			System.out.println("Index value out of bounds.");
			return false;
		}
		else{
			if((board.get(index).size() > 0) && (board.get(index).peek() != null) && (!board.get(index).peek().equals(pawn))){
				System.out.println("Can't place different kind of pawns on the same column.");
				return false;
			}
			else if(board.get(index).size() == MAX_COLUMN){
				System.out.println("This column is full.");
				return false;
			}
			else{
				board.get(index).push(pawn);
				return true;
			}
		}
	}
	
	/**
	 * 
	 * @param index
	 * @return will return null if empty.
	 */
	public BackgammonPawn peekAtColumn(int index){
		if((index < 0) || (index > LENGTH-1)){
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}
		return board.get(index).peek();
	}
	
	public int getSizeOfColumn(int index){
		if((index < 0) || (index > LENGTH-1)){
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}
		return board.get(index).size();
	}
	
	public boolean isEmptyColumn(int index){
		if((index < 0) || (index > LENGTH-1)){
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}
		return board.get(index).isEmpty();
	}
	
	public BackgammonPawn popAtColumn(int index){
		if((index < 0) || (index > LENGTH-1)){
			IndexOutOfBoundsException ex = new IndexOutOfBoundsException("Index value out of bounds.");
			throw ex;
		}
		
		try{
			return board.get(index).pop();
		}
		catch(NoSuchElementException ex){
			return null;
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

	public void print(){
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
		
		printHowManyPawnsOutside(sb);
		
		System.out.println(sb.toString());
	}

	public void printHowManyPawnsOutside() {
		printHowManyPawnsOutside(new StringBuilder());
	}
	
	private void printHowManyPawnsOutside(StringBuilder sb) {
		if(blacksOutsideGame.size() > 0) sb.append("  There are " + blacksOutsideGame.size() + " black pawns outside the game.").append("\n");
		if(whitesOutsideGame.size() > 0) sb.append("  There are " + whitesOutsideGame.size() + " white pawns outside the game.").append("\n");
	}

	public boolean isHasColor(Color color){
		for(Deque<BackgammonPawn> column:board){
			BackgammonPawn pawn = column.peek();
			if(pawn != null && pawn.equals(color)) return true;
		}
		return false;
	}
	/**
	 * TODO test
	 */
	public void clearPawnsOutsideGame(){
		blacksOutsideGame.clear();
		whitesOutsideGame.clear();
	}
	
	private void printUpperBoard(BackgammonBoard boardCopy, StringBuilder sb) {
		for(int i=0; i<7; i++){
			sb.append("  #");
			for(int j=11; j>-1; j--){
				if(i == 0){
					int pawnCount = boardCopy.getSizeOfColumn(j);
					String pawnCountStr = Integer.toHexString(pawnCount).toUpperCase();
					sb.append(" ").append(pawnCountStr);
					if(j == 6) sb.append("   ");
					if(j == 0) sb.append(" ");
				}
				else if(i == 1){
					sb.append("-------------  -------------");
					break;
				}
				else{
					if(boardCopy.isEmptyColumn(j)){
						sb.append("|*");
					}
					else{
						BackgammonPawn p = boardCopy.popAtColumn(j);
						if(p.getColor().equals(Color.black)){
							sb.append("|B");
						}
						else{
							sb.append("|W");
						}
					}
					
					if(j == 6) sb.append("|  ");
					if(j == 0) sb.append("|");
				}
			}
			sb.append("#\n");
		}
	}

	private void printBottomBoard(BackgammonBoard boardCopy, StringBuilder sb) {
		StringBuilder sbPawns = new StringBuilder();
		for(int i=0; i<7; i++){
			sb.append("  #");
			
			if(i == 6){
				sb.append(sbPawns.toString()).append(" #\n");
				continue;
			}
			else if(i == 5){
				sb.append("-------------  -------------#").append("\n");
				continue;
			}
			
			for(int j=12; j<24; j++){
				if(i == 0){
					int pawnCount = boardCopy.getSizeOfColumn(j);
					String pawnCountStr = Integer.toHexString(pawnCount).toUpperCase();
					sbPawns.append(" ").append(pawnCountStr);
					if(j == 17) sbPawns.append("   ");
				}
				
				if(i+1+boardCopy.getSizeOfColumn(j) <= 5){
					sb.append("|*");
				}
				else{
					BackgammonPawn p = boardCopy.popAtColumn(j);
					if(p.getColor().equals(Color.black)){
						sb.append("|B");
					}
					else{
						sb.append("|W");
					}
				}
				
				if(j == 17) sb.append("|  ");
				if(j == 23) sb.append("|");
			}
			sb.append("#\n");
		}
	}

	private boolean checkBoardEquality(BackgammonBoard other){
		for(int i=0; i<board.size(); i++){
			if (board.get(i).size() != other.getSizeOfColumn(i)) return false;
			else if(board.get(i).size() > 0)
			{
				BackgammonPawn pawn = board.get(i).peek();
				BackgammonPawn otherPawn = other.peekAtColumn(i);
				if(!pawn.getColor().equals(otherPawn.getColor())) return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * TODO test all these.
	 */
	public int getHowManyBlacksOutsideGame(){
		return blacksOutsideGame.size();
	}
	
	public int getHowManyWhitesOutsideGame(){
		return whitesOutsideGame.size();
	}
	
	public boolean addBlackOutsideGame(BackgammonPawn black){
		return blacksOutsideGame.add(black);
	}
	
	public boolean addWhiteOutsideGame(BackgammonPawn white){
		return whitesOutsideGame.add(white);
	}
	
	public BackgammonPawn popBlackOutsideGame(){
		return blacksOutsideGame.pop();
	}
	
	public BackgammonPawn popWhiteOutsideGame(){
		return whitesOutsideGame.pop();
	}

	public BackgammonPawn peekBlackOutsideGame() {
		return blacksOutsideGame.peek();
	}

	public BackgammonPawn peekWhiteOutsideGame() {
		return whitesOutsideGame.peek();
	}

	@Override
	public boolean executeMove(Player player, Move move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidMove(Player player, Move move) {
		// TODO Auto-generated method stub
		return false;
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
