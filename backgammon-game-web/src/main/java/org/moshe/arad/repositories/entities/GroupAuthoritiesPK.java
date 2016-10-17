package org.moshe.arad.repositories.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GroupAuthoritiesPK implements Serializable{

	private Long groupId;
	
	private String authority;

	public GroupAuthoritiesPK() {
	}
	
	public GroupAuthoritiesPK(Long groupId, String authority) {
		this.groupId = groupId;
		this.authority = authority;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
