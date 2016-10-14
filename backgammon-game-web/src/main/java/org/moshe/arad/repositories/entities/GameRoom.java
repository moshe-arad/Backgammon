package org.moshe.arad.repositories.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.moshe.arad.game.BasicGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name ="game_rooms")
@EntityListeners(AuditingEntityListener.class)
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
	
	@LastModifiedDate
	@Column(name="last_modified_date")
	@NotNull
	private Date lastModifiedDate;
	
	@LastModifiedBy
	@Column(name="last_modified_by")
	@NotNull
	private String lastModifiedBy;
	
	@CreatedDate
	@Column(name="created_date", updatable=false)
	@NotNull
	private Date createdDate;
	
	@CreatedBy
	@Column(name="created_by", updatable=false)
	@NotNull
	private String createdBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gameRooms")
	private Set<GameUser> users = new HashSet<>(1000);
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id")
	private Group group;
	
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "GameRoom [gameRoomId=" + gameRoomId + ", gameRoomName=" + gameRoomName + ", isPrivateRoom="
				+ isPrivateRoom + ", openedBy=" + openedBy + ", white=" + white + ", black=" + black + ", speed="
				+ speed + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + "]";
	}
}
