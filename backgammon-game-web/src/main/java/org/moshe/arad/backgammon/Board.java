package org.moshe.arad.backgammon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntBiFunction;

public class Board {

	private static final int LENGTH = 24;
	private static final int DIVIDE_FACTOR = 4;
	private static final int MAX_COLUMN = 15;
	private List<Deque<Pawn>> board = new ArrayList<Deque<Pawn>>(LENGTH);
	
	public Board() {
		for(int i=0; i<LENGTH; i++)
			board.add(new ArrayDeque<Pawn>(MAX_COLUMN));
	}
	
	public Board(Board b){
		for(int i=0; i<LENGTH; i++)
			board.add(new ArrayDeque<Pawn>(MAX_COLUMN));
		
		for(int i=0; i<LENGTH; i++){
			Pawn pawn = b.getBoard().get(i).peek();
			for(int j=0; j<b.getBoard().get(i).size(); j++)
				board.get(i).push(pawn);
		}
	}
	
	public void initBoard(){
		for(int i=0; i<2; i++)
			board.get(0).push(Pawn.black);
		
		for(int i=0; i<5; i++)
			board.get(5).push(Pawn.white);
		
		for(int i=0; i<3; i++)
			board.get(7).push(Pawn.white);
		
		for(int i=0; i<5; i++)
			board.get(11).push(Pawn.black);
		
		for(int i=0; i<5; i++)
			board.get(12).push(Pawn.white);
		
		for(int i=0; i<3; i++)
			board.get(16).push(Pawn.black);
		
		for(int i=0; i<5; i++)
			board.get(18).push(Pawn.black);
		
		for(int i=0; i<2; i++)
			board.get(23).push(Pawn.white);
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

	private void printUpperBoard(Board boardCopy, StringBuilder sb) {
		for(int i=0; i<7; i++){
			sb.append("  #");
			for(int j=11; j>-1; j--){
				if(i == 0){
					int pawnCount = boardCopy.getBoard().get(j).size();
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
					if(boardCopy.getBoard().get(j).isEmpty()){
						sb.append("|*");
					}
					else{
						Pawn p = boardCopy.getBoard().get(j).pop();
						if(p.equals(Pawn.black)){
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
					int pawnCount = boardCopy.getBoard().get(j).size();
					String pawnCountStr = Integer.toHexString(pawnCount).toUpperCase();
					sbPawns.append(" ").append(pawnCountStr);
					if(j == 17) sbPawns.append("   ");
				}
				
				if(i+1+boardCopy.getBoard().get(j).size() <= 5){
					sb.append("|*");
				}
				else{
					Pawn p = boardCopy.getBoard().get(j).pop();
					if(p.equals(Pawn.black)){
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
			if (board.get(i).size() != other.getBoard().get(i).size()) return false;
			else if(board.get(i).size() > 0)
			{
				Pawn pawn = board.get(i).peek();
				Pawn otherPawn = other.getBoard().get(i).peek();
				if(!pawn.equals(otherPawn)) return false;
			}
		}
		return true;
	}

	public List<Deque<Pawn>> getBoard() {
		return board;
	}
}
