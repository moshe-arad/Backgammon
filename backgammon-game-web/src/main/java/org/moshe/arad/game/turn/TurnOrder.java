package org.moshe.arad.game.turn;

import java.util.LinkedList;

import org.moshe.arad.game.player.Player;

public abstract class TurnOrder implements TurnOrderable {

	protected LinkedList<Player> order = new LinkedList<Player>();

	public TurnOrder() {
	}
}
