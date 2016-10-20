package org.moshe.arad.services;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.UserMoveQueuesManager;
import org.moshe.arad.backgammon_dispatcher.entities.PairBackgammonDices;
import org.moshe.arad.backgammon_dispatcher.entities.UserMove;
import org.moshe.arad.components.GameRooms;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.game.move.BackgammonBoardLocation;
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
	private UserMoveQueuesManager userMoveQueues;
	
	private ApplicationContext gameContext = new ClassPathXmlApplicationContext("backgammon-web-context.xml");
	
	public PairBackgammonDices rollDices(GameRoom gameRoom){
		PairBackgammonDices pair = null;
		
		if(isPlayerWithTurnWhite(gameRoom) && isLoogedUserWhite(gameRoom)){			
			synchronized (gameRoom.getGame().getLocker()) {
				gameRoom.getGame().getLocker().notify();
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (gameRoom.getGame().getLocker()) {
				BackgammonTurn turn = (BackgammonTurn) gameRoom.getGame().getFirstPlayer().getTurn();
				pair = new PairBackgammonDices(turn.getFirstDice(), turn.getSecondDice()); 
			}
		}
		
		return pair;
	}
	
	public GameRoom getGameRoomByJsonId(String gameRoomId){
		Long gameRoomIdFromClient = getGameRoomIdFromJson(gameRoomId);
		return getGameRoomById(gameRoomIdFromClient);
	}
	
	public Boolean initAndStartGame(GameRoom gameRoom) {
		if(!gameRoom.getIsGameRoomReady()) 
			gameRoom.setIsGameRoomReady(isBothPlayersOnRoom(gameRoom));
			
		if(gameRoom.getIsGameRoomReady() && gameRoom.getGame() == null) {
			synchronized (gameRoom) {
				if(gameRoom.getGame() == null){
					logger.info("Starting game.");
					initGame(gameRoom);
					notifyWhiteSendMove(gameRoom);
					startGame(gameRoom);
				}				
			}			
		}
		return gameRoom.getIsGameRoomReady();
	}
	
	private void startGame(GameRoom gameRoom) {
		gameRoom.getGame().start();
	}
	
	private void initGame(GameRoom gameRoom) {
		gameRoom = gameRooms.getGameRoomById(gameRoom);
		gameRoom.setGame(gameContext.getBean(Backgammon.class));
		GameUser white = securityRepository.getGameUserByGameUserId(gameRoom.getWhite());
		logger.info("White is with ID of: " + white.getUserId());
		GameUser black = securityRepository.getGameUserByGameUserId(gameRoom.getBlack());
		logger.info("Black is with ID of: " + black.getUserId());
		gameRoom.initGame(white, black, gameContext.getBean(BackgammonTurn.class));
		logger.info("initializing queues for usesr.");
		userMoveQueues.createNewQueueForUser(white.getBasicUser());
		userMoveQueues.createNewQueueForUser(black.getBasicUser());
	}
	
	private void notifyWhiteSendMove(GameRoom gameRoom) {
		BasicUser white = getWhiteBasicUser(gameRoom);
		Move move = new Move(new BackgammonBoardLocation(UserMove.WHITE_PLAYER_TURN),
				new BackgammonBoardLocation(UserMove.WHITE_PLAYER_TURN));
		UserMove userMove = new UserMove(move, white);
		userMoveQueues.putMoveIntoQueue(white, userMove);
	}
	
	private boolean isBothPlayersOnRoom(GameRoom gameRoom){
		Long white = gameRoomRepository.selectWhiteFromGameRoom(gameRoom.getGameRoomId());
		Long black = gameRoomRepository.selectBlackFromGameRoom(gameRoom.getGameRoomId());
		if(white != null) logger.info("White player is on room and ready to play.");
		if(black != null) logger.info("Black player is on room and ready to play.");
		return (white != null && black != null);
	}
	
	private GameRoom getGameRoomById(Long gameRoomId){
		return gameRooms.getGameRoomById(gameRoomId);
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
