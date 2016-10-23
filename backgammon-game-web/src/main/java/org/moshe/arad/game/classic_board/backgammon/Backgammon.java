package org.moshe.arad.game.classic_board.backgammon;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Appender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.moshe.arad.backgammon_dispatcher.BackgammonUserQueue;
import org.moshe.arad.backgammon_dispatcher.entities.BasicDetails;
import org.moshe.arad.backgammon_dispatcher.entities.DiceRolling;
import org.moshe.arad.backgammon_dispatcher.entities.DispatchableEntity;
import org.moshe.arad.backgammon_dispatcher.entities.InvalidMove;
import org.moshe.arad.backgammon_dispatcher.entities.ValidMove;
import org.moshe.arad.game.classic_board.ClassicBoardGame;
import org.moshe.arad.game.instrument.BackgammonBoard;
import org.moshe.arad.game.instrument.Board;
import org.moshe.arad.game.instrument.Dice;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.BoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.TurnOrderable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Backgammon extends ClassicBoardGame implements Runnable{

	private Move move = null;	
	private String gameRoomName;
	
	private BackgammonUserQueue whiteQueue = null;
	private BackgammonUserQueue blackQueue = null;
	
	private Logger logger = LogManager.getLogger(Backgammon.class);
	
	private Object diceLocker = new Object();
	private Object nextMoveLocker = new Object();
	
	private ApplicationContext gameContext = new ClassPathXmlApplicationContext("backgammon-web-context.xml");
	
	public Backgammon(Board board, TurnOrderable turnOrderManager) {
		super(board, turnOrderManager);
	}		

	@Override
	public void run() {
		super.startGame();
	}
	
	@Override
	public void playGameTurn(Player player) {
		BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player;
		
		BasicDetails basicDetailsWhite = gameContext.getBean("basicDetails", BasicDetails.class);
		BasicDetails basicDetailsBlack = gameContext.getBean("basicDetails", BasicDetails.class);
		
		
		if(backgammonPlayer.isWhite()){
			basicDetailsWhite.setIsYourTurn(true);
			basicDetailsWhite.setColor("white");
			
			basicDetailsBlack.setColor("black");
			basicDetailsBlack.setIsYourTurn(false);
		}
		else {
			basicDetailsWhite.setIsYourTurn(false);
			basicDetailsWhite.setColor("white");
			
			basicDetailsBlack.setColor("black");
			basicDetailsBlack.setIsYourTurn(true);
		}
		
		basicDetailsWhite.setMessageToken(1);
		basicDetailsBlack.setMessageToken(1);
		
		whiteQueue.putMoveIntoQueue(basicDetailsWhite);
		blackQueue.putMoveIntoQueue(basicDetailsBlack);
		
		Dice first = backgammonPlayer.getTurn().getFirstDice();
		Dice second = backgammonPlayer.getTurn().getSecondDice();
		Scanner reader = new Scanner(System.in);
		String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName() + ": ";
		
		logger.info(name + "it's your turn. roll the dices.");
		
		synchronized (diceLocker) {
			try {
				diceLocker.wait();
				player.rollDices();				
				logger.info(name + "you rolled - " + first.getValue() + ": " + second.getValue());
				
				DiceRolling diceRollingWhite = gameContext.getBean("diceRolling", DiceRolling.class);
				DiceRolling diceRollingBlack = gameContext.getBean("diceRolling", DiceRolling.class);
				
				if(backgammonPlayer.isWhite()){
					diceRollingWhite.setColor("white");
					diceRollingWhite.setIsYourTurn(true);
					diceRollingWhite.setFirstDice(backgammonPlayer.getTurn().getFirstDice().getValue());
					diceRollingWhite.setSecondDice(backgammonPlayer.getTurn().getSecondDice().getValue());
					
					diceRollingBlack.setColor("black");
					diceRollingBlack.setIsYourTurn(false);
					diceRollingBlack.setFirstDice(backgammonPlayer.getTurn().getFirstDice().getValue());
					diceRollingBlack.setSecondDice(backgammonPlayer.getTurn().getSecondDice().getValue());
				}
				else
				{
					diceRollingWhite.setColor("white");
					diceRollingWhite.setIsYourTurn(false);
					diceRollingWhite.setFirstDice(backgammonPlayer.getTurn().getFirstDice().getValue());
					diceRollingWhite.setSecondDice(backgammonPlayer.getTurn().getSecondDice().getValue());
					
					diceRollingBlack.setColor("black");
					diceRollingBlack.setIsYourTurn(true);
					diceRollingBlack.setFirstDice(backgammonPlayer.getTurn().getFirstDice().getValue());
					diceRollingBlack.setSecondDice(backgammonPlayer.getTurn().getSecondDice().getValue());
				}
				 
				diceRollingBlack.setMessageToken(2);
				diceRollingWhite.setMessageToken(2);
				
				whiteQueue.putMoveIntoQueue(diceRollingWhite);
				blackQueue.putMoveIntoQueue(diceRollingBlack);
				
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				logger.error(e);
			}
		}
		
		try {
			logger.info("The board as follows:");
			logger.info(board);
			
			while(isCanKeepPlay(player)){				
				synchronized (nextMoveLocker) {
					nextMoveLocker.wait();
					//move = enterNextMove(player, reader);
				}
				
				int eatenWhite = ((BackgammonBoard)board).getWhiteEatenSize();
				int eatenBlack = ((BackgammonBoard)board).getBlackEatenSize();
				
				logger.info("Before move, white outs = " + eatenWhite);
				logger.info("Before move, black outs = " + eatenBlack);
				if(board.isValidMove(player, move)){					
					board.executeMove(player, move);
					player.makePlayed(move);					
					logger.info("A move was made...");
					logger.info("************************************");
					logger.info(board);
					
					ValidMove validMoveWhite = gameContext.getBean("validMove", ValidMove.class);
					ValidMove validMoveBlack = gameContext.getBean("validMove", ValidMove.class);
					
					boolean isEatenWhite =  (eatenWhite + 1) == ((BackgammonBoard)board).getWhiteEatenSize() ? true : false;
					boolean isEatenBlack =  (eatenBlack + 1) == ((BackgammonBoard)board).getBlackEatenSize() ? true : false;
					
					logger.info("After move, white outs = " + eatenWhite);
					logger.info("After move, black outs = " + eatenBlack);
					
					if(backgammonPlayer.isWhite()){
						validMoveWhite.setColor("white");
						validMoveWhite.setIsYourTurn(true);
						validMoveWhite.setFrom(((BackgammonBoardLocation)move.getFrom()).getIndex());
						validMoveWhite.setTo(((BackgammonBoardLocation)move.getTo()).getIndex());
						validMoveWhite.setColumnSizeOnFrom(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getFrom()));
						validMoveWhite.setColumnSizeOnTo(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getTo()));
						validMoveWhite.setHasMoreMoves(isCanKeepPlay(player));
						validMoveWhite.setEaten(isEatenBlack);
						
						validMoveBlack.setColor("black");
						validMoveBlack.setIsYourTurn(false);
						validMoveBlack.setFrom(((BackgammonBoardLocation)move.getFrom()).getIndex());
						validMoveBlack.setTo(((BackgammonBoardLocation)move.getTo()).getIndex());
						validMoveBlack.setColumnSizeOnFrom(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getFrom()));
						validMoveBlack.setColumnSizeOnTo(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getTo()));
						validMoveBlack.setHasMoreMoves(isCanKeepPlay(player));
						validMoveBlack.setEaten(isEatenBlack);
					}
					else{
						validMoveWhite.setColor("white");
						validMoveWhite.setIsYourTurn(false);
						validMoveWhite.setFrom(((BackgammonBoardLocation)move.getFrom()).getIndex());
						validMoveWhite.setTo(((BackgammonBoardLocation)move.getTo()).getIndex());
						validMoveWhite.setColumnSizeOnFrom(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getFrom()));
						validMoveWhite.setColumnSizeOnTo(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getTo()));
						validMoveWhite.setHasMoreMoves(isCanKeepPlay(player));
						validMoveWhite.setEaten(isEatenWhite);
						
						validMoveBlack.setColor("black");
						validMoveBlack.setIsYourTurn(true);
						validMoveBlack.setFrom(((BackgammonBoardLocation)move.getFrom()).getIndex());
						validMoveBlack.setTo(((BackgammonBoardLocation)move.getTo()).getIndex());
						validMoveBlack.setColumnSizeOnFrom(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getFrom()));
						validMoveBlack.setColumnSizeOnTo(((BackgammonBoard)(super.board)).getSizeOfColumn((BackgammonBoardLocation)move.getTo()));
						validMoveBlack.setHasMoreMoves(isCanKeepPlay(player));
						validMoveBlack.setEaten(isEatenWhite);
					}
					
					validMoveWhite.setMessageToken(4);
					validMoveBlack.setMessageToken(4);
					whiteQueue.putMoveIntoQueue(validMoveWhite);
					blackQueue.putMoveIntoQueue(validMoveBlack);
				}
				else{
					InvalidMove invalidMoveWhite = gameContext.getBean("invalidMove", InvalidMove.class);
					InvalidMove invalidMoveBlack = gameContext.getBean("invalidMove", InvalidMove.class);
					
					invalidMoveWhite.setColor("white");
					invalidMoveWhite.setIsYourTurn(true);
					invalidMoveWhite.setIsInvalid(true);
					
					invalidMoveBlack.setColor("black");
					invalidMoveBlack.setIsYourTurn(true);
					invalidMoveBlack.setIsInvalid(true);
					
					invalidMoveBlack.setMessageToken(3);
					invalidMoveWhite.setMessageToken(3);
					
					if(backgammonPlayer.isWhite()) whiteQueue.putMoveIntoQueue(invalidMoveWhite);
					else if(!backgammonPlayer.isWhite()) blackQueue.putMoveIntoQueue(invalidMoveBlack);
					
					notifyOnInvalidMove(player);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isCanKeepPlay(Player player) throws Exception {
		return !board.isWinner(player) && board.isHasMoreMoves(player);
	}
	
	/**
	 * TODO to write test, after get rid of final class Scanner 
	 * need to remove Scanner object from method signature.
	 */

	@Deprecated
	private Move enterNextMove(Player player, Scanner reader) {
		if(player == null) return null;
		BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player; 
		String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName();
		Move move = new Move();
		
		logger.info(name+": enter your next move.");
		String msg = "from where to move? (index -1:24).";
		String input = getMoveInput(name, reader, msg);
		move.setFrom(new BackgammonBoardLocation(Integer.parseInt(input)));
		msg = "where move to? (index -1:24).";
		input = getMoveInput(name, reader, msg);
		move.setTo(new BackgammonBoardLocation(Integer.parseInt(input)));
		return move;
	}

	@Deprecated
	private String getMoveInput(String name, Scanner reader, String msg) {
		logger.info(name+": " + msg);
		String input = reader.next();
		while(!NumberUtils.isNumber(input) || Integer.parseInt(input) <= -2 || Integer.parseInt(input) >= 25){
			logger.warn("Your input is invalid. try again.");
			input = reader.next();
		}
		return input;
	}


	public void notifyOnInvalidMove(Player player) {		
		if(player == null) logger.error("Player is null.");
		else{
			BackgammonPlayer backgammonPlayer = (BackgammonPlayer)player; 
			String name = backgammonPlayer.getFirstName() + " " + backgammonPlayer.getLastName();
			logger.warn(name + ": you made invalid move. try again.");
		}
	}
	
	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
	
	public BackgammonUserQueue getWhiteQueue() {
		return whiteQueue;
	}

	public void setWhiteQueue(BackgammonUserQueue whiteQueue) {
		this.whiteQueue = whiteQueue;
	}

	public BackgammonUserQueue getBlackQueue() {
		return blackQueue;
	}

	public void setBlackQueue(BackgammonUserQueue blackQueue) {
		this.blackQueue = blackQueue;
	}	
	
	public Object getDiceLocker() {
		return diceLocker;
	}

	public Object getNextMoveLocker() {
		return nextMoveLocker;
	}
	
	public String getGameRoomName() {
		return gameRoomName;
	}

	public void setGameRoomName(String gameRoomName) {
		this.gameRoomName = gameRoomName;
	}
}
