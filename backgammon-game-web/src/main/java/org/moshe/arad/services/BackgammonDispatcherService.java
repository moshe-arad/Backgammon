package org.moshe.arad.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.UserMoveQueue;
import org.moshe.arad.backgammon_dispatcher.UserMoveQueuesManager;
import org.moshe.arad.backgammon_dispatcher.UserMoveTask;
import org.moshe.arad.backgammon_dispatcher.entities.UserMove;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BackgammonDispatcherService {

	private final Logger logger = LogManager.getLogger(BackgammonDispatcherService.class);
	@Autowired
	private UserMoveQueuesManager userMoveQueues;
	@Autowired
	private SecurityRepository securityRepository;
	private ExecutorService requestsPool = Executors.newCachedThreadPool();
	
	public Move respondToUser(){
		BasicUser loggedInBasicUser = securityRepository.getLoggedInBasicUser();
		Future<UserMove> userMoveResult = handleUserMoveRequest(loggedInBasicUser);
		return getMoveFromQueue(userMoveResult, loggedInBasicUser);
	}
	
	private Future<UserMove> handleUserMoveRequest(BasicUser basicUser){
		UserMoveQueue userMoveQueue = userMoveQueues.getUserMoveQueue(basicUser);
		if(userMoveQueue == null) userMoveQueue = userMoveQueues.createNewQueueForUser(basicUser);
		UserMoveTask userMoveTask = new UserMoveTask(userMoveQueue);
		return requestsPool.submit(userMoveTask);
	}
	
	private Move getMoveFromQueue(Future<UserMove> userMoveFromQueue, BasicUser loggedInBasicUser){
		int attempts = 0;
		Move move = null;
	
		while(attempts < 30){
			try {
				attempts++;
				move = userMoveFromQueue.get(2, TimeUnit.SECONDS).getMove();
				if(move != null) break;				
			}
			catch(TimeoutException ex){	
				logger.info("Tried to grab move from queue of - " + loggedInBasicUser.getUserName() + " - attempt #" + attempts);				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return move;
	}
	
	public void shutDownRequestsPool(){
		if(!requestsPool.isShutdown()){
			requestsPool.shutdown();
		}
	}
}
