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
	private List<Deque<Pawn>> board = new ArrayList(LENGTH);
	
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

	public void print(){
//		int maxPawnsOnColumn = board.stream().max((col1, col2) -> Integer.compare(col1.size(), col2.size())).get().size();
		
		Board boardCopy = new Board(this);
		StringBuilder sb = new StringBuilder();
		sb.append(" ** The Board ** ").append("\n");
		
		for(int i=0; i<7; i++){
			sb.append("   ");
			for(int j=11; j>-1; j--){
				if(i == 0){
					int pawnCount = boardCopy.getBoard().get(j).size();
					String pawnCountStr = Integer.toHexString(pawnCount).toUpperCase();
					sb.append(" ").append(pawnCountStr);
					if(j == 6) sb.append("  ");
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
					
					if(j == 6) sb.append("  ");
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

	public List<Deque<Pawn>> getBoard() {
		return board;
	}
}
