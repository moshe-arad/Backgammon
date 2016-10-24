package org.moshe.arad.backgammon_dispatcher.confirm;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

public class IsArrivedMessage extends Thread {

	@Autowired
	private ConfirmArriveQueueManager confirmArriveQueueManager;
	private UUID uuid;
	
	public IsArrivedMessage(UUID uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public void run() {
		
		while(!confirmArriveQueueManager.isContainsMessageWithUUID(uuid)){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(confirmArriveQueueManager.isContainsMessageWithUUID(uuid)){
			confirmArriveQueueManager.markConfirmedMessageWithUUID(uuid);
		}
	}

}
