package org.moshe.arad.services;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.components.GameRooms;
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
	
	private ExecutorService gamesPool = Executors.newFixedThreadPool(8);
	
	private ApplicationContext gameContext = new ClassPathXmlApplicationContext("backgammon-web-context.xml");
	
	public void setMoveFromClient(Move move, Long gameRoomId){
		GameRoom gameRoom = gameRooms.getGameRoomById(gameRoomId);
		
		if((isPlayerWithTurnWhite(gameRoom) && isLoogedUserWhite(gameRoom)) ||
				(isPlayerWithTurnBlack(gameRoom) && isLoogedUserBlack(gameRoom))){
			gameRoom.getGame().setMove(move);
			synchronized (gameRoom.getGame().getNextMoveLocker()) {
				logger.info("System is about to calculate next backgammon move, next move locker released.");
				gameRoom.getGame().getNextMoveLocker().notify();
			}
		}
	}
	
	public GameRoom getGameRoomById(Long gameRoomId){
		return gameRooms.getGameRoomById(gameRoomId);
	}
	
	public void rollDices(GameRoom gameRoom){
		if((isPlayerWithTurnWhite(gameRoom) && isLoogedUserWhite(gameRoom)) ||
				(isPlayerWithTurnBlack(gameRoom) && isLoogedUserBlack(gameRoom))){			
			synchronized (gameRoom.getGame().getDiceLocker()) {
				logger.info("System is about to roll dices, dices locker released.");
				gameRoom.getGame().getDiceLocker().notify();
			}
		}
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
			
			gamesPool.submit(gameRoom.getGame());
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
	
	private boolean isPlayerWithTurnBlack(GameRoom gameRoom){
		BackgammonPlayer playerWithTurn = (BackgammonPlayer) gameRoom.getGame().getTurnOrderManager().howHasTurn();
		return playerWithTurn.isWhite() ? false : true;
	}
	
	public boolean isLoogedUserWhite(GameRoom gameRoom){
		return getWhiteGameUser(gameRoom).equals(getLoggedInGameUser());
	}
	
	public boolean isLoogedUserBlack(GameRoom gameRoom){
		return getBlackGameUser(gameRoom).equals(getLoggedInGameUser());
	}
	
	private GameUser getWhiteGameUser(GameRoom gameRoom) {		
		return securityRepository.getGameUserByGameUserId(gameRoom.getWhite());
	}
	
	private GameUser getBlackGameUser(GameRoom gameRoom) {		
		return securityRepository.getGameUserByGameUserId(gameRoom.getBlack());
	}
	
	@SuppressWarnings("unused")
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
