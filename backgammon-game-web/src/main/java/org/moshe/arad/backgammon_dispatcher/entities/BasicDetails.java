package org.moshe.arad.backgammon_dispatcher.entities;

import java.util.UUID;

public class BasicDetails implements DispatchableEntity {

	private UUID uuid;
	private int messageToken;
	private String color;
	private Boolean isYourTurn;
	
	public BasicDetails(int messageToken, String color, Boolean isYourTurn) {
		this.uuid = UUID.randomUUID();
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
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "BasicDetails [uuid=" + uuid + ", messageToken=" + messageToken + ", color=" + color + ", isYourTurn="
				+ isYourTurn + "]";
	}
}
