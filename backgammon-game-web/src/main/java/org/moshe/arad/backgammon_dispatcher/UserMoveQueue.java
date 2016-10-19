package org.moshe.arad.backgammon_dispatcher;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.moshe.arad.game.move.Move;

public class UserMoveQueue {
	
	private PriorityBlockingQueue<UserMove> userQueue; 
	
	public UserMoveQueue() {
		Comparator<UserMove> movesByCreateDate = (m1, m2) -> { return m1.getMove().getCreateDate().compareTo(m2.getMove().getCreateDate());};
		userQueue = new PriorityBlockingQueue<>(100, movesByCreateDate);
	}
	
	public UserMove takeMoveFromQueue(){
		UserMove moveFromQueue = null;
		try {
			moveFromQueue = userQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return moveFromQueue;
	}
	
	public void putMoveIntoQueue(UserMove userMove){
		if(userMove == null) return;
		userQueue.put(userMove);
	}
	
	public boolean isHasMovesOnQueue(){
		return !userQueue.isEmpty();
	}
}
