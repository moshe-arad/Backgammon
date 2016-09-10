package org.moshe.arad.backgammon;

import java.util.LinkedList;
import java.util.List;

public abstract class TurnOrderManager implements TurnOrderable{

	protected LinkedList<Player> order = new LinkedList<Player>();

	public TurnOrderManager() {
	}
	
	public TurnOrderManager(LinkedList<Player> order) {
		this.order = order;
	}

	protected void setOrder(LinkedList<Player> order) {
		this.order = order;
	}
}
