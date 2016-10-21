package org.moshe.arad.backgammon_dispatcher.entities;

public class BasicDetails implements DispatchableEntity {

	private int messageToken;
	private String color;
	private Boolean isYourTurn;
	
	public BasicDetails() {
	}
	
	public BasicDetails(int messageToken, String color, Boolean isYourTurn) {
		this.messageToken = messageToken;
		this.color = color;
		this.isYourTurn = isYourTurn;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Boolean getIsYourTurn() {
		return isYourTurn;
	}
	public void setIsYourTurn(Boolean isYourTurn) {
		this.isYourTurn = isYourTurn;
	}

	public int getMessageToken() {
		return messageToken;
	}

	public void setMessageToken(int messageToken) {
		this.messageToken = messageToken;
	}
}
