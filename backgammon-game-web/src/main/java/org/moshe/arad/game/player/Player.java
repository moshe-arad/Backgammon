package org.moshe.arad.game.player;

import org.moshe.arad.game.instrument.Color;
import org.moshe.arad.game.turn.Turn;
import org.springframework.beans.factory.annotation.Autowired;

public class Player {

	private String id;
	private String firstName;
	private String lastName;
	private int age;
	private Turn turn;
	private Color color;

	public Player(String id, String firstName, String lastName, int age, Turn turn, int color) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.turn = turn;
		this.color = Color.getColorByInt(color);
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

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
