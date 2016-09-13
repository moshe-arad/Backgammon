package org.moshe.arad.game.instrument;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {

	public static final int LENGTH = 24;
	public static final int MAX_COLUMN = 15;
	private List<Deque<Pawn>> board = new ArrayList<Deque<Pawn>>(LENGTH);
	
	public Board() {
		for(int i=0; i<LENGTH; i++)
			board.add(new ArrayDeque<Pawn>(MAX_COLUMN));
	}
	
	public Board(Board b){
		if(b == null) throw new NullPointerException("Board parameter is null.");
		
		for(int i=0; i<LENGTH; i++)
			board.add(new ArrayDeque<Pawn>(MAX_COLUMN));
		
		for(int i=0; i<LENGTH; i++){
			Pawn pawn = b.peekAtColumn(i);
			for(int j=0; j<b.getSizeOfColumn(i); j++)
				board.get(i).push(pawn);
		}
	}
	
	public void clearBoard(){
		for(Deque<Pawn> column:board){
			column.clear();
		}
	}
	
	public void initBoard(){
		for(Deque<Pawn> column:board){
			column.clear();
		}
				
		for(int i=0; i<2; i++)
			board.get(0).push(new Pawn(Color.black.getInnerValue()));
		
		for(int i=0; i<5; i++)
			board.get(5).push(new Pawn(Color.white.getInnerValue()));
		
		for(int i=0; i<3; i++)
			board.get(7).push(new Pawn(Color.white.getInnerValue()));
		
		for(int i=0; i<5; i++)
			board.get(11).push(new Pawn(Color.black.getInnerValue()));
		
		for(int i=0; i<5; i++)
			board.get(12).push(new Pawn(Color.white.getInnerValue()));
		
		for(int i=0; i<3; i++)
			board.get(16).push(new Pawn(Color.black.getInnerValue()));
		
		for(int i=0; i<5; i++)
			board.get(18).push(new Pawn(Color.black.getInnerValue()));
		
		for(int i=0; i<2; i++)
			board.get(23).push(new Pawn(Color.white.getInnerValue()));
	}
	
	public boolean setPawn(Pawn pawn, int index){
		if(pawn == null) return false;
		if((index < 0) || (index > LENGTH-1)){
			System.out.println("Index value out of bounds.");
			return false;
		}
		else{
			if((board.get(index).size() > 0) && (board.get(index).peek() != null) && (!board.get(index).peek().getColor().equals(pawn.getColor()))){
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
	public Pawn peekAtColumn(int index){
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
	
	public Pawn popAtColumn(int index){
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
		Board other = (Board) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!this.checkBoardEquality(other))
			return false;
		return true;
	}

	public void print(){
		Board boardCopy = new Board(this);
		StringBuilder sb = new StringBuilder();
		sb.append("       ** The Board **").append("\n");
		
		sb.append("  ##############################").append("\n");
		printUpperBoard(boardCopy, sb);
		
		sb.append("  #                            #\n");
		
		printBottomBoard(boardCopy, sb);
		sb.append("  ##############################").append("\n");
		System.out.println(sb.toString());
	}

	public boolean isHasColor(Color color){
		for(Deque<Pawn> column:board){
			Pawn pawn = column.peek();
			if(pawn != null && pawn.getColor().equals(color)) return true;
		}
		return false;
	}
	
	private void printUpperBoard(Board boardCopy, StringBuilder sb) {
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
						Pawn p = boardCopy.popAtColumn(j);
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

	private void printBottomBoard(Board boardCopy, StringBuilder sb) {
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
					Pawn p = boardCopy.popAtColumn(j);
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

	private boolean checkBoardEquality(Board other){
		for(int i=0; i<board.size(); i++){
			if (board.get(i).size() != other.getSizeOfColumn(i)) return false;
			else if(board.get(i).size() > 0)
			{
				Pawn pawn = board.get(i).peek();
				Pawn otherPawn = other.peekAtColumn(i);
				if(!pawn.getColor().equals(otherPawn.getColor())) return false;
			}
		}
		return true;
	}
}
