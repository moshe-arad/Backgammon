package org.moshe.arad.repositories.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "groups")
public class Group implements CreateUpdateable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long groupId;
	
	@Column(name = "group_name")
	private String groupName;
	
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

	@OneToMany(mappedBy = "group")
	private List<GroupMembers> groupMembers = new ArrayList<>(100);
	
	@OneToMany(mappedBy = "group")
	private List<GroupAuthorities> groupAuthorities = new ArrayList<>(100);
	
	@OneToOne(mappedBy = "group")
	private GameRoom gameRoom;
	
	public List<GroupMembers> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMembers> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public List<GroupAuthorities> getGroupAuthorities() {
		return groupAuthorities;
	}

	public void setGroupAuthorities(List<GroupAuthorities> groupAuthorities) {
		this.groupAuthorities = groupAuthorities;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Override
	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	@Override
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Override
	public Long getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	@Override
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public Date getCreatedDate() {
		return this.createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;		
	}

	@Override
	public Long getCreatedBy() {
		return this.createdBy;
	}

	@Override
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
}
