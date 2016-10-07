package org.moshe.arad.repositories.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.moshe.arad.game.BasicGame;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author moshe-arad
 * TODO hibernate/jap/spring-data entity
 *  
 *  
 */
@Entity
@Table(name ="game_users")
public class GameRoom {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "game_room_id")
	private Long gameRoomId;
	
	@Column(name = "name")
	@NotBlank
	@Pattern(regexp = "^([a-z|A-Z])[a-z|A-Z| ]+[a-z|A-Z|0-9]+$")
	private String gameRoomName;
	
	@Column(name = "private")
	@NotNull
	private Boolean isPrivateRoom;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_in_game_room", 
		joinColumns = @JoinColumn(name = "game_room_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<GameUser> users = new HashSet<>(1000);
	
	@Column(name = "opened_by")
	@NotNull
	private GameUser openedBy;
	
	@Column
	private GameUser white;
	
	@Column
	private GameUser black;
	
	@Column
	@Range(min=0, max=2)
	private Integer speed;
	
	@Autowired
	@Transient
	private BasicGame game;
	
	@Column(name="last_updated_date")
	@NotNull
	private Date lastUpdatedDate;
	
	@Column(name="last_updated_by")
	@NotNull
	private Long lastUpdatedBy;
	
	@Column(name="created_date", updatable=false)
	@NotNull
	private Date createdDate;
	
	@Column(name="created_by", updatable=false)
	@NotNull
	private Long createdBy;
	
	public GameRoom() {
	}
	
	public GameRoom(String gameRoomName, Boolean isPrivateRoom, GameUser openedBy,
			GameUser white, GameUser black, Integer speed) {
		this.gameRoomName = gameRoomName;
		this.isPrivateRoom = isPrivateRoom;
		this.users  = new HashSet<>(1000);
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

	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "GameRoom [gameRoomId=" + gameRoomId + ", gameRoomName=" + gameRoomName + ", isPrivateRoom="
				+ isPrivateRoom + ", users=" + users + ", openedBy=" + openedBy + ", white=" + white + ", black="
				+ black + ", speed=" + speed + ", game=" + game + "]";
	}
}
