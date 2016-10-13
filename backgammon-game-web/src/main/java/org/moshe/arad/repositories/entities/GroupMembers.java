package org.moshe.arad.repositories.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "group_members")
public class GroupMembers implements CreateUpdateable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long groupMemberId;
	
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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id")
	private Group group;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username")
	private BasicUser basicUser;
	
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public BasicUser getBasicUser() {
		return basicUser;
	}

	public void setBasicUser(BasicUser basicUser) {
		this.basicUser = basicUser;
	}

	public Long getGroupMemberId() {
		return groupMemberId;
	}

	public void setGroupMemberId(Long groupMemberId) {
		this.groupMemberId = groupMemberId;
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
}
