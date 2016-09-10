package org.moshe.arad.backgammon;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author moshe-arad
 *
 * However is first in Deque will has turn, and should have Turn object.
 */
public class BackgammonTurnOrderManager extends TurnOrderManager{

	public BackgammonTurnOrderManager(Player firstPlayer, Player secondPlayer) {
		LinkedList<Player> order = new LinkedList<>();
		order.add(firstPlayer);
		order.add(secondPlayer);
		setOrder(order);
	}

	@Override
	public Player howHasTurn() {
		return (order.peek().getTurn() != null) ? order.peek() : null;  
	}

	@Override
	public boolean passTurn(Player from, Player to) {
		if(order.peek().getTurn() != null){
			Player played = order.pop();
			order.peek().setTurn(played.getTurn());
			played.setTurn(null);
			order.push(played);
			return true;
		}
		else return false;
	}

	@Override
	public Player howIsNextInTurn() {
		return (order.peek().getTurn() != null) ? order.peek() : null;
	}
}
