package org.moshe.arad.services;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.BackgammonUserQueue;
import org.moshe.arad.backgammon_dispatcher.BackgammonUsersQueuesManager;
import org.moshe.arad.components.GameRooms;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BackgammonService {

	private final Logger logger = LogManager.getLogger(BackgammonService.class);
	@Autowired
	private GameRooms gameRooms;
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private BackgammonUsersQueuesManager userMoveQueues;
	
	private ApplicationContext gameContext = new ClassPathXmlApplicationContext("backgammon-web-context.xml");
	
	public void setMoveFromClient(Move move, Long gameRoomId){
		GameRoom gameRoom = gameRooms.getGameRoomById(gameRoomId);
		gameRoom.getGame().setMove(move);
		synchronized (gameRoom.getGame().getNextMoveLocker()) {
			logger.info("System is about to calculate next backgammon move, next move locker released.");
			gameRoom.getGame().getNextMoveLocker().notify();
		}
	}
	
	public GameRoom getGameRoomById(Long gameRoomId){
		return gameRooms.getGameRoomById(gameRoomId);
	}
	
	public void rollDices(GameRoom gameRoom){
//		PairBackgammonDices pair = null;
		
		if(isPlayerWithTurnWhite(gameRoom) && isLoogedUserWhite(gameRoom)){			
			synchronized (gameRoom.getGame().getDiceLocker()) {
				logger.info("System is about to roll dices, dices locker released.");
				gameRoom.getGame().getDiceLocker().notify();
			}
			
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			synchronized (gameRoom.getGame().getLocker()) {
//				BackgammonTurn turn = (BackgammonTurn) gameRoom.getGame().getFirstPlayer().getTurn();
//				pair = new PairBackgammonDices(turn.getFirstDice(), turn.getSecondDice()); 
//			}
		}
		
//		return pair;
	}
	
	public GameRoom getGameRoomByJsonId(String gameRoomId){
		Long gameRoomIdFromClient = getGameRoomIdFromJson(gameRoomId);
		return getGameRoomById(gameRoomIdFromClient);
	}
	
	public Boolean initAndStartGame(Long gameRoomId) {
		GameRoom gameRoom = gameRooms.getGameRoomById(gameRoomId);
		
		if(!gameRoom.getIsGameRoomReady()) 
			gameRoom.setIsGameRoomReady(isBothPlayersOnRoom(gameRoom));
			
		if(gameRoom.getIsGameRoomReady() && gameRoom.getGame() != null) {
			logger.info("Starting game.");
			gameRoom = initGame(gameRoom);
//			notifyWhiteSendMove(gameRoom);
			new Thread(gameRoom.getGame()).start();
		}
		return gameRoom.getIsGameRoomReady();
	}
	
	private GameRoom initGame(GameRoom gameRoom) {
		GameUser white = securityRepository.getGameUserByGameUserId(gameRoom.getWhite());
		logger.info("White is with ID of: " + white.getUserId());
		GameUser black = securityRepository.getGameUserByGameUserId(gameRoom.getBlack());
		logger.info("Black is with ID of: " + black.getUserId());
		gameRoom.initGame(white, black, gameContext.getBean(BackgammonTurn.class));
		logger.info("initializing queues for usesr.");
		return gameRoom;
	}
	
//	private void notifyWhiteSendMove(GameRoom gameRoom) {
//		BasicUser white = getWhiteBasicUser(gameRoom);
//		Move move = new Move(new BackgammonBoardLocation(UserMove.WHITE_PLAYER_TURN),
//				new BackgammonBoardLocation(UserMove.WHITE_PLAYER_TURN));
//		userMoveQueues.putMoveIntoQueue(white, move);
//	}
	
	private boolean isBothPlayersOnRoom(GameRoom gameRoom){
		Long white = gameRoomRepository.selectWhiteFromGameRoom(gameRoom.getGameRoomId());
		Long black = gameRoomRepository.selectBlackFromGameRoom(gameRoom.getGameRoomId());
		if(white != null) logger.info("White player is on room and ready to play.");
		if(black != null) logger.info("Black player is on room and ready to play.");
		return (white != null && black != null);
	}
	
	private boolean isPlayerWithTurnWhite(GameRoom gameRoom){
		BackgammonPlayer playerWithTurn = (BackgammonPlayer) gameRoom.getGame().getTurnOrderManager().howHasTurn();
		return playerWithTurn.isWhite() ? true : false;
	}
	
	private boolean isLoogedUserWhite(GameRoom gameRoom){
		return getWhiteGameUser(gameRoom).equals(getLoggedInGameUser());
	}
	private GameUser getWhiteGameUser(GameRoom gameRoom) {		
		return securityRepository.getGameUserByGameUserId(gameRoom.getWhite());
	}
	
	private BasicUser getWhiteBasicUser(GameRoom gameRoom) {		
		return securityRepository.getGameUserByGameUserId(gameRoom.getWhite()).getBasicUser();
	}
	
	private GameUser getLoggedInGameUser(){
		return securityRepository.getLoggedInGameUser();
	}
	
	private Long getGameRoomIdFromJson(String gameRoomId) {
		ObjectMapper mapper = new ObjectMapper();
		Long gameRoomIdFromClient = null;
		try {
			JsonNode node = mapper.readTree(gameRoomId);
			gameRoomIdFromClient = node.get("gameRoomId").asLong();
		} catch (IOException e) {
			logger.error("Failed to read game room id json.");
			logger.error(e.getMessage());
			logger.error(e);
		}
		return gameRoomIdFromClient;
	}
}
