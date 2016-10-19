package org.moshe.arad.backgammon_dispatcher;

import java.util.concurrent.Callable;

import org.moshe.arad.backgammon_dispatcher.entities.UserMove;
import org.moshe.arad.game.move.Move;

public class UserMoveTask implements Callable<UserMove>{

	private UserMoveQueue movesQueue;
	
	public UserMoveTask(UserMoveQueue movesQueue) {
		this.movesQueue = movesQueue;
	}
	
	@Override
	public UserMove call() throws Exception {
		return movesQueue.takeMoveFromQueue();		
	}

}
