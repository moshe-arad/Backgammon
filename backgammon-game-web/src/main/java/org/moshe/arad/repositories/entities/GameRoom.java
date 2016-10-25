package org.moshe.arad.repositories.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.ClassicGamePlayer;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.moshe.arad.game.turn.ClassicGameTurnOrderManager;
import org.moshe.arad.general.CreateDateJsonSerializer;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name ="game_rooms")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	
	@Transient
	@JsonIgnore
	private Backgammon game;
	
	@Transient
	@JsonIgnore
	private Boolean isGameRoomReady;

	@JsonIgnore
	@LastModifiedDate
	@Column(name="last_modified_date")
	@NotNull
	private Date lastModifiedDate;
	
	@JsonIgnore
	@LastModifiedBy
	@Column(name="last_modified_by")
	@NotNull
	private String lastModifiedBy;
	
//	@JsonIgnore
	@JsonSerialize(using = CreateDateJsonSerializer.class)
	@CreatedDate
	@Column(name="created_date", updatable=false)
	@NotNull
	private Date createdDate;
	
	@JsonIgnore
	@CreatedBy
	@Column(name="created_by", updatable=false)
	@NotNull
	private String createdBy;

	@JsonIgnore
	@ManyToMany(mappedBy = "gameRooms")
	private List<GameUser> users = new ArrayList<>(1000);
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id")
	private Group group;
	
	public GameRoom() {
		this.isGameRoomReady = false;
	}
	
	public GameRoom(String gameRoomName, Boolean isPrivateRoom, Long openedBy,
			Long white, Long black, Integer speed) {
		this.gameRoomName = gameRoomName;
		this.isPrivateRoom = isPrivateRoom;
		this.users  = new ArrayList<>(1000);
		this.openedBy = openedBy;
		this.white = white;
		this.black = black;
		this.speed = speed;
		this.isGameRoomReady = false;
	}

	public void initGame(GameUser white, GameUser black, BackgammonTurn turn){
		
		Player playerWhite = new BackgammonPlayer(white.getFirstName(),
				white.getLastName(), 100, turn, 
				true);
		
		Player playerBlack = new BackgammonPlayer(black.getFirstName(),
				black.getLastName(), 100, turn, 
				false);
		
		LinkedList<ClassicGamePlayer> backgammonPlayers = new LinkedList<>();
		backgammonPlayers.add((BackgammonPlayer) playerWhite);
		backgammonPlayers.add((BackgammonPlayer) playerBlack);
				
		((ClassicGameTurnOrderManager)game.getTurnOrderManager()).setOrder(backgammonPlayers);
		
		game.setFirstPlayer(playerWhite);
		game.setSecondPlayer(playerBlack);		
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

	public List<GameUser> getUsers() {
		return users;
	}

	public void setUsers(List<GameUser> users) {
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

	public Backgammon getGame() {
		return game;
	}

	public void setGame(Backgammon game) {
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

	public Boolean getIsGameRoomReady() {
		return isGameRoomReady;
	}

	public void setIsGameRoomReady(Boolean isGameRoomReady) {
		this.isGameRoomReady = isGameRoomReady;
	}
	
	@Override
	public String toString() {
		return "GameRoom [gameRoomId=" + gameRoomId + ", gameRoomName=" + gameRoomName + ", isPrivateRoom="
				+ isPrivateRoom + ", openedBy=" + openedBy + ", white=" + white + ", black=" + black + ", speed="
				+ speed + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((black == null) ? 0 : black.hashCode());
		result = prime * result + ((gameRoomName == null) ? 0 : gameRoomName.hashCode());
		result = prime * result + ((isPrivateRoom == null) ? 0 : isPrivateRoom.hashCode());
		result = prime * result + ((openedBy == null) ? 0 : openedBy.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + ((white == null) ? 0 : white.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameRoom other = (GameRoom) obj;
		if (black == null) {
			if (other.black != null)
				return false;
		} else if (!black.equals(other.black))
			return false;
		if (gameRoomName == null) {
			if (other.gameRoomName != null)
				return false;
		} else if (!gameRoomName.equals(other.gameRoomName))
			return false;
		if (isPrivateRoom == null) {
			if (other.isPrivateRoom != null)
				return false;
		} else if (!isPrivateRoom.equals(other.isPrivateRoom))
			return false;
		if (openedBy == null) {
			if (other.openedBy != null)
				return false;
		} else if (!openedBy.equals(other.openedBy))
			return false;
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		if (white == null) {
			if (other.white != null)
				return false;
		} else if (!white.equals(other.white))
			return false;
		return true;
	}
}
