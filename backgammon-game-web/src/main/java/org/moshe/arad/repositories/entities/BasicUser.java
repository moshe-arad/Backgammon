package org.moshe.arad.repositories.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "users")
public class BasicUser implements CreateUpdateable{

	@Id
	@Column(name = "username")
	private String userName;
	
	@Column
	@NotBlank
	private String password;
	
	@Column
	@NotNull
	private Boolean enabled;
	
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
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "basicUser")
	private GameUser gameUser;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "basicUser")
	private List<Authority> authorities = new ArrayList<>(100);

	@OneToMany(mappedBy = "basicUser")
	private List<GroupMembers> groupMembers = new ArrayList<GroupMembers>(100);
	
	public BasicUser() {
	}
	
	public BasicUser(String userName, String password, Boolean enabled) {
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "BasicUser [userName=" + userName + ", password=" + password + ", enabled=" + enabled
				+ ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedBy=" + lastUpdatedBy + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", gameUser=" + gameUser + ", authorities=" + authorities
				+ "]";
	}
	
	
	public List<GroupMembers> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMembers> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public GameUser getGameUser() {
		return gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
