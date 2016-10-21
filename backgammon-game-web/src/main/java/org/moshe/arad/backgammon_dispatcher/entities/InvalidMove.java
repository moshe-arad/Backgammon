package org.moshe.arad.backgammon_dispatcher.entities;

public class InvalidMove extends BasicDetails {

	private Boolean isInvalid;

	public InvalidMove() {
	}
	
	public InvalidMove(int messageToken, String color, Boolean isYourTurn, Boolean isInvalid) {
		super(messageToken, color, isYourTurn);
		this.isInvalid = isInvalid;
	}

	public Boolean getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(Boolean isInvalid) {
		this.isInvalid = isInvalid;
	}
}
