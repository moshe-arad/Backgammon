package org.moshe.arad.game;

public abstract class BasicGame implements BasicGameable{

	@Override
	public void start(){
		initGame();
		play();
		doWinnerActions();
	}
	
	@Override
	public abstract void initGame();

	@Override
	public abstract void play();
	
	@Override
	public abstract void doWinnerActions();

	@Override
	public abstract boolean isHasWinner();

}
