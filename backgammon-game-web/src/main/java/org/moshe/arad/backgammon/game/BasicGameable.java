package org.moshe.arad.backgammon.game;

public interface BasicGameable {

	public void play();
	
	public void doWinnerActions();
	
	public boolean isHasWinner();
}
