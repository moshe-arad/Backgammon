package org.moshe.arad.backgammon_dispatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.entities.UserMove;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.stereotype.Component;

@Component
public class UserMoveQueuesManager {
	
	private final Logger logger = LogManager.getLogger(UserMoveQueuesManager.class);
	private Map<String, UserMoveQueue> usersQueues = new ConcurrentHashMap<>(1000, 0.75F, 1000);
	
	public UserMoveQueue createNewQueueForUser(BasicUser basicUser){
		UserMoveQueue newQueue = new UserMoveQueue();
		if(basicUser == null) return null;
		usersQueues.putIfAbsent(basicUser.getUserName(), newQueue);
		return newQueue;
	}
	
	public UserMove takeMoveFromQueue(BasicUser basicUser){
		if(basicUser == null) return null;
		if(!usersQueues.containsKey(basicUser.getUserName())) return null;
		return usersQueues.get(basicUser.getUserName()).takeMoveFromQueue();
	}
	
	public void putMoveIntoQueue(BasicUser basicUser, UserMove move){
		if(basicUser == null || move == null){
			logger.error("Move obj or basicUser obj is null.");
			return;
		}
		if(!usersQueues.containsKey(basicUser.getUserName())) {
			logger.error("Queue doen't exists for this user - " + basicUser.getUserName());
			return;
		}
		usersQueues.get(basicUser.getUserName()).putMoveIntoQueue(move);
		logger.error("Message was put on queue for user: " + basicUser.getUserName());
	}
	
	public UserMoveQueue getUserMoveQueue(BasicUser basicUser){
		return usersQueues.get(basicUser.getUserName());
	}
}
