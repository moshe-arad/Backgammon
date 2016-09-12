package org.moshe.arad.game.player;

import org.moshe.arad.game.turn.Turn;
import org.springframework.beans.factory.annotation.Autowired;

public class Player {

	private String id;
	private String firstName;
	private String lastName;
	private int age;
	@Autowired
	private Turn turn;

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
	
	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public Turn getTurn() {
		return turn;
	}
}
