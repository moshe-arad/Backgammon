package org.moshe.arad.backgammon.turn;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.moshe.arad.backgammon.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 
 * @author moshe-arad
 *
 * However is first in Deque will has turn, and should have Turn object.
 */
public class BackgammonTurnOrder extends TurnOrder{

	@Autowired
	public BackgammonTurnOrder(@Qualifier("firstPlayer") Player firstPlayer, @Qualifier("secondPlayer") Player secondPlayer, Turn turn) {
		if((firstPlayer == null) || (secondPlayer == null) || (turn == null)) throw new NullPointerException();
		LinkedList<Player> order = new LinkedList<>();
		firstPlayer.setTurn(turn);
		order.add(firstPlayer);
		order.add(secondPlayer);
		super.order = order;
	}

	@Override
	public Player howHasTurn() {
		return (order.peek().getTurn() != null) ? order.peek() : null;  
	}

	@Override
	public boolean passTurn() {
		if(order.peek().getTurn() != null){
			Player played = order.pop();
			order.peek().setTurn(played.getTurn());
			played.setTurn(null);
			order.addLast(played);
			return true;
		}
		else return false;
	}

	@Override
	public Player howIsNextInTurn() {
		return (order.peek().getTurn() != null) ? order.peekLast() : null;
	}
}
