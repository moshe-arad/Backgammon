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
@Table(name ="game_rooms")
public class GameRoom implements CreateUpdateable{

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
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "gameRooms")
	private Set<GameUser> users = new HashSet<>(1000);
	
	@Column(name = "opened_by")
	@NotNull
	private Long openedBy;
	
	@Column(name = "white")
	private Long white;
	
	@Column(name = "black")
	private Long black;
	
	@Column(name = "speed")
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
	
	@Column(name = "token", updatable = false, insertable = false)
	private String token;

	public GameRoom() {
	}
	
	public GameRoom(String gameRoomName, Boolean isPrivateRoom, Long openedBy,
			Long white, Long black, Integer speed) {
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

	public Long getOpenedBy() {
		return openedBy;
	}

	public void setOpenedBy(Long openedBy) {
		this.openedBy = openedBy;
	}

	public Long getWhite() {
		return white;
	}

	public void setWhite(Long white) {
		this.white = white;
	}

	public Long getBlack() {
		return black;
	}

	public void setBlack(Long black) {
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
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	@Override
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Override
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	@Override
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public Long getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "GameRoom [gameRoomId=" + gameRoomId + ", gameRoomName=" + gameRoomName + ", isPrivateRoom="
				+ isPrivateRoom + ", users=" + users + ", openedBy=" + openedBy + ", white=" + white + ", black="
				+ black + ", speed=" + speed + ", game=" + game + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", token=" + token + "]";
	}
}
