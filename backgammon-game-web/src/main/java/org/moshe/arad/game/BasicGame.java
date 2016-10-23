package org.moshe.arad.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;

public abstract class BasicGame implements BasicGameable{

	private final Logger logger = LogManager.getLogger("org.moshe.arad");
	
	@Override
	public void startGame(){
		logger.info("Template pattern begins.");
		initGame();
		play();
		doWinnerActions();
		logger.info("Template pattern ends.");
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
