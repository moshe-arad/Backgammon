package org.moshe.arad.backgammon_dispatcher.confirm;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.moshe.arad.backgammon_dispatcher.entities.BasicDetails;
import org.moshe.arad.backgammon_dispatcher.entities.DispatchableEntity;
import org.springframework.stereotype.Component;

@Component
public class ConfirmArriveQueueManager {

	private Map<UUID, DispatchableEntity> unconfirmedMessages = new ConcurrentHashMap<>(1000, 0.75F, 1000);
//	private Map<UUID, Thread> messageArrivedThreads = new ConcurrentHashMap<>(1000, 0.75F, 1000);
//	private ThreadPoolExecutor messageArrivedPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
	
	public ConfirmArriveQueueManager() {
	
	}
	
	public void putMessageToBeConfirmed(DispatchableEntity message){
		unconfirmedMessages.put(((BasicDetails)message).getUuid(), message);
//		IsArrivedMessage task = new IsArrivedMessage(((BasicDetails)message).getUuid());
//		messageArrivedThreads.put(((BasicDetails)message).getUuid(), task);
//		messageArrivedPool.submit(task);
	}
	
	public boolean isContainsMessageWithUUID(UUID uuid){
		return unconfirmedMessages.containsKey(uuid);
	}
	
	public void markConfirmedMessageWithUUID(UUID uuid){
		unconfirmedMessages.remove(uuid);
//		messageArrivedThreads.get(uuid).interrupt();
//		messageArrivedThreads.remove(uuid);
	}
}
