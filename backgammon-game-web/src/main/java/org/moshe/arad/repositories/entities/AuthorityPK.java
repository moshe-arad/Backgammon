package org.moshe.arad.repositories.entities;

import java.io.Serializable;

public class AuthorityPK implements Serializable{

	private static final long serialVersionUID = 7106049315073084899L;

	private String userName;
	
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
