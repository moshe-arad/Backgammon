package org.moshe.arad.repositories.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities")
@IdClass(AuthorityPK.class)
public class Authority {

	@Id
	@Column(name = "username")
	private String userName;
	
	@Id
	@Column
	private String authority;
	
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
	
	public Authority() {
	}

	public Authority(String userName, String authority) {
		this.userName = userName;
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "Authority [userName=" + userName + ", authority=" + authority + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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
}
