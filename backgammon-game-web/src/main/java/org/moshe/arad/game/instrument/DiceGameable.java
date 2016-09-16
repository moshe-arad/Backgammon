package org.moshe.arad.game.instrument;

import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.Player;

public interface DiceGameable {

	public void rollDices(Player player);
	
	public boolean initDices(Player player, Move move);
}
