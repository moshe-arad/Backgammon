package org.moshe.arad.objects_to_frontend;

public class AuthenticatedUser {

	private boolean isAuthenticated;
	private String userName;
	
	public AuthenticatedUser() {
	}
	
	public AuthenticatedUser(boolean isAuthenticated, String userName) {
		this.isAuthenticated = isAuthenticated;
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "AuthenticatedUser [isAuthenticated=" + isAuthenticated + ", userName=" + userName + "]";
	}
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
