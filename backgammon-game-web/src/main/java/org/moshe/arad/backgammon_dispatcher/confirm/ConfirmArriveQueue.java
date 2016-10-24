package org.moshe.arad.backgammon_dispatcher.confirm;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import org.moshe.arad.backgammon_dispatcher.entities.DispatchableEntity;

public class ConfirmArriveQueue {
	
	Comparator<DispatchableEntity> confirmsByCreateDate = (item1, item2) -> { return item2.createDate.compareTo(item1.createDate);};
	private PriorityBlockingQueue<DispatchableEntity> confirmQueue = new PriorityBlockingQueue<>(1000, confirmsByCreateDate);
	
	
	public void putConfirmOnQueue(DispatchableEntity entity){
		confirmQueue.put(entity);
	}
	
	public DispatchableEntity takeConfirmOnQueue(){
		try {
			return confirmQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
