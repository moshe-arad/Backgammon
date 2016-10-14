package org.moshe.arad.repositories.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class BasicUser {

	@Id
	@Column(name = "username")
	private String userName;
	
	@Column
	@NotBlank
	private String password;
	
	@Column
	@NotNull
	private Boolean enabled;
	
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
				+ ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy + ", gameUser=" + gameUser + ", authorities=" + authorities
				+ ", groupMembers=" + groupMembers + "]";
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
