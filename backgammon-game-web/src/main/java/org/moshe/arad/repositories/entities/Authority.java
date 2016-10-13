package org.moshe.arad.repositories.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities")
public class Authority implements CreateUpdateable{
	
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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username")
	private BasicUser basicUser;

	public Authority() {
	}

	public Authority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "Authority [authority=" + authority + ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedBy="
				+ lastUpdatedBy + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", basicUser="
				+ basicUser + "]";
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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
	
	public BasicUser getBasicUser() {
		return basicUser;
	}

	public void setBasicUser(BasicUser basicUser) {
		this.basicUser = basicUser;
	}
}
