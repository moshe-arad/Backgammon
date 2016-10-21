package org.moshe.arad.backgammon_dispatcher.entities;

public class EmptyMessage implements DispatchableEntity {

	private boolean isEmpty = true;
	
	public EmptyMessage() {
		isEmpty = true;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
	
}
