package org.moshe.arad.repositories.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "authorities")
@EntityListeners(AuditingEntityListener.class)
public class Authority {
	
	@Id
	@Column
	private String authority;
	
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
		return "Authority [authority=" + authority + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy="
				+ lastModifiedBy + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", basicUser="
				+ basicUser + "]";
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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public BasicUser getBasicUser() {
		return basicUser;
	}

	public void setBasicUser(BasicUser basicUser) {
		this.basicUser = basicUser;
	}
}
