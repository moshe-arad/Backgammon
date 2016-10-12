package org.moshe.arad.repositories.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "users")
public class BasicUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "u_id")
	private Long userId;
	
	@Column(name ="username")
	@NotBlank
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

	@OneToOne(mappedBy = "basicUser")
	private GameUser gameUser;
	
	
	public BasicUser() {
	}
	
	public BasicUser(String userName, String password, Boolean enabled) {
		Date now = new Date();
		
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		
		this.lastUpdatedDate = now;
		this.lastUpdatedBy = -1L;
		this.createdDate = now;
		this.createdBy = -1L;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
	
	public GameUser getGameUser() {
		return gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

}
