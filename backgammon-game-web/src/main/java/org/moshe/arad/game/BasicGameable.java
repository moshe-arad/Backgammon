
package org.moshe.arad.game;

public interface BasicGameable {

	public void initGame();
	
	public void play();
	
	public void doWinnerActions();
	
	public boolean isHasWinner();
}
