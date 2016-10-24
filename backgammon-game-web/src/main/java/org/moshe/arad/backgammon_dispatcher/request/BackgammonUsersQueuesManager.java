package org.moshe.arad.backgammon_dispatcher.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.entities.DispatchableEntity;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.stereotype.Component;

@Component
public class BackgammonUsersQueuesManager {
	
	private final Logger logger = LogManager.getLogger(BackgammonUsersQueuesManager.class);
	private Map<String, BackgammonUserQueue> usersQueues = new ConcurrentHashMap<>(1000, 0.75F, 1000);
	
	public BackgammonUserQueue createNewQueueForUser(BasicUser basicUser){
		BackgammonUserQueue newQueue = new BackgammonUserQueue();
		if(basicUser == null) return null;
		usersQueues.putIfAbsent(basicUser.getUserName(), newQueue);
		return newQueue;
	}
	
	public DispatchableEntity takeMoveFromQueue(BasicUser basicUser){
		if(basicUser == null) return null;
		if(!usersQueues.containsKey(basicUser.getUserName())) return null;
		return usersQueues.get(basicUser.getUserName()).takeMoveFromQueue();
	}
	
	public void putMoveIntoQueue(BasicUser basicUser, DispatchableEntity entity){
		if(basicUser == null || entity == null){
			logger.error("Move obj or basicUser obj is null.");
			return;
		}
		if(!usersQueues.containsKey(basicUser.getUserName())) {
			logger.error("Queue doen't exists for this user - " + basicUser.getUserName());
			return;
		}
		usersQueues.get(basicUser.getUserName()).putMoveIntoQueue(entity);
		logger.info("Message was put on queue for user: " + basicUser.getUserName());
	}
	
	public BackgammonUserQueue getUserMoveQueue(BasicUser basicUser){
		return usersQueues.get(basicUser.getUserName());
	}
}
