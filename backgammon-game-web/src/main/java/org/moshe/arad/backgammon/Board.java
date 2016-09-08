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
			board.set(i, new ArrayDeque<Pawn>(MAX_COLUMN));
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
//		ToIntBiFunction<Deque<Pawn>, Deque<Pawn>> maxCompare = (col1, col2) -> Integer.compare(col1.size(), col2.size());
		int maxPawnsOnColumn = board.stream().max((col1, col2) -> Integer.compare(col1.size(), col2.size())).get().size();
	}
}
