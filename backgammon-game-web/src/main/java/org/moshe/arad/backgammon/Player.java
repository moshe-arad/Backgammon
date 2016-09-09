package org.moshe.arad.backgammon;

public class Player {

	private String id;
	private String firstName;
	private String lastName;
	private int age;
	
	public Player(String id, String firstName, String lastName, int age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
	}
}
