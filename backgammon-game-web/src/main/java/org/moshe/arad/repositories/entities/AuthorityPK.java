package org.moshe.arad.repositories.entities;

import java.io.Serializable;

import javax.persistence.Column;

public class AuthorityPK implements Serializable{

	private static final long serialVersionUID = 7106049315073084899L;

	@Column(name = "username", insertable = false, updatable = false)
	private String userName;
	
	@Column(insertable = false, updatable = false)
	private String authority;

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
}
