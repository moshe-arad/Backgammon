package org.moshe.arad.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.BackgammonUserQueue;
import org.moshe.arad.backgammon_dispatcher.BackgammonUsersQueuesManager;
import org.moshe.arad.backgammon_dispatcher.entities.DispatchableEntity;
import org.moshe.arad.backgammon_dispatcher.entities.EmptyMessage;
import org.moshe.arad.backgammon_dispatcher.BackgammonUserTask;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BackgammonDispatcherService {

	private final Logger logger = LogManager.getLogger(BackgammonDispatcherService.class);
	@Autowired
	private BackgammonUsersQueuesManager userMoveQueues;
	@Autowired
	private SecurityRepository securityRepository;
	private ThreadPoolExecutor requestsPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
	
	public DispatchableEntity respondToUser(){
		BasicUser loggedInBasicUser = securityRepository.getLoggedInBasicUser();
		Future<DispatchableEntity> userMoveResult = handleUserMoveRequest(loggedInBasicUser);
		if(userMoveResult != null){
			DispatchableEntity response = getMoveFromQueue(userMoveResult, loggedInBasicUser, userMoveQueues.getUserMoveQueue(loggedInBasicUser));
			if(response == null) return new EmptyMessage(5);
			else return response;
		}
		else return new EmptyMessage(5);				
	}
	
	private Future<DispatchableEntity> handleUserMoveRequest(BasicUser basicUser){		
		BackgammonUserQueue userQueue = userMoveQueues.getUserMoveQueue(basicUser);
		if(userQueue == null) userQueue = userMoveQueues.createNewQueueForUser(basicUser);
		
		boolean gotLock = userQueue.getRegisterRequestLocker().tryLock();
		BackgammonUserTask task = new BackgammonUserTask(userQueue);
		return gotLock ? requestsPool.submit(task) : null;
	}
	
	private DispatchableEntity getMoveFromQueue(Future<DispatchableEntity> fromQueue, BasicUser loggedInBasicUser, BackgammonUserQueue backgammonUserQueue){
		int attempts = 0;
		DispatchableEntity entity = null;
	
		while(attempts < 30){
			try {
				attempts++;
				entity = fromQueue.get(2, TimeUnit.SECONDS);
				if(entity != null){
					backgammonUserQueue.getRegisterRequestLocker().unlock();
					break;				
				}
			}
			catch(TimeoutException ex){	
				logger.info("Tried to grab move from queue of - " + loggedInBasicUser.getUserName() + " - attempt #" + attempts);				
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		fromQueue.cancel(true);
		return entity;
	}
}
