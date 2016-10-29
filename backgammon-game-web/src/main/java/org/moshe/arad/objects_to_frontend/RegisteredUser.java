package org.moshe.arad.objects_to_frontend;

public class RegisteredUser {

	private boolean registered;

	public RegisteredUser() {
	}
	
	public RegisteredUser(boolean registered) {
		super();
		this.registered = registered;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	
	
}
