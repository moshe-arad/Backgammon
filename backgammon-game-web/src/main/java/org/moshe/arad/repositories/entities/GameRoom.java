package org.moshe.arad.repositories.entities;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.moshe.arad.game.BasicGame;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author moshe-arad
 * TODO add a table in DB for this entity
 *  and to make it hibernate/jap/spring-data entity
 *  
 *  "createdDate","lastUpdatedBy","createdBy","lastUpdatedDate"
 */
//@Entity
public class GameRoom {

	private Long gameRoomId;
	
	private String gameRoomName;
	
	private Boolean isPrivateRoom;
	
	private Set<GameUser> users;
	
	private GameUser openedBy;
	
	private GameUser white;
	
	private GameUser black;
	
	private Integer speed;
	
	@Autowired
	private BasicGame game;

	public GameRoom() {
	}
	
	public GameRoom(String gameRoomName, Boolean isPrivateRoom, GameUser openedBy,
			GameUser white, GameUser black, Integer speed) {
		this.gameRoomName = gameRoomName;
		this.isPrivateRoom = isPrivateRoom;
		this.users  = new CopyOnWriteArraySet<>();
		this.openedBy = openedBy;
		this.white = white;
		this.black = black;
		this.speed = speed;
	}

	public Long getGameRoomId() {
		return gameRoomId;
	}

	public void setGameRoomId(Long gameRoomId) {
		this.gameRoomId = gameRoomId;
	}

	public String getGameRoomName() {
		return gameRoomName;
	}

	public void setGameRoomName(String gameRoomName) {
		this.gameRoomName = gameRoomName;
	}

	public Boolean getIsPrivateRoom() {
		return isPrivateRoom;
	}

	public void setIsPrivateRoom(Boolean isPrivateRoom) {
		this.isPrivateRoom = isPrivateRoom;
	}

	public Set<GameUser> getUsers() {
		return users;
	}

	public void setUsers(Set<GameUser> users) {
		this.users = users;
	}

	public GameUser getOpenedBy() {
		return openedBy;
	}

	public void setOpenedBy(GameUser openedBy) {
		this.openedBy = openedBy;
	}

	public GameUser getWhite() {
		return white;
	}

	public void setWhite(GameUser white) {
		this.white = white;
	}

	public GameUser getBlack() {
		return black;
	}

	public void setBlack(GameUser black) {
		this.black = black;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public BasicGame getGame() {
		return game;
	}

	public void setGame(BasicGame game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "GameRoom [gameRoomId=" + gameRoomId + ", gameRoomName=" + gameRoomName + ", isPrivateRoom="
				+ isPrivateRoom + ", users=" + users + ", openedBy=" + openedBy + ", white=" + white + ", black="
				+ black + ", speed=" + speed + ", game=" + game + "]";
	}
}
