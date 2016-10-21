package org.moshe.arad.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.components.GameRooms;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.game.player.BackgammonPlayer;
import org.moshe.arad.game.player.Player;
import org.moshe.arad.game.turn.BackgammonTurn;
import org.moshe.arad.repositories.HomeRepository;
import org.moshe.arad.repositories.LobbyRepository;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LobbyService {

	private final Logger logger = LogManager.getLogger(LobbyService.class);
	@Autowired
	private GameRooms gameRooms;
//	private Map<Long, GameRoom> gameRooms = new ConcurrentHashMap<>(1000, 0.75F, 1000);
	private static AtomicLong roomlabel = new AtomicLong(1);
	
	@Autowired
	private LobbyRepository lobbyRepository;
	@Autowired
	private HomeRepository homeRepository;
	@Autowired
	private SecurityRepository securityRepository;
			
	public GameRoom addNewGameRoom(GameRoom gameRoom) {
		logger.info("New game room was opened, details: " + gameRoom);
		GameRoom roomInDb = lobbyRepository.createNewGameRoomWithLoggedInUser(gameRoom);
		lobbyRepository.createNewGroupForNewRoom(roomInDb);
		logger.info("New game room was added to DB, details: " + gameRoom);
//		gameRooms.put(roomInDb.getGameRoomId(), roomInDb);
		gameRooms.addGameRoom(roomInDb);
		logger.info("game room was added successfully, details: " + gameRoom);
		return roomInDb;
	}
	
	public void setDefaultValues(GameRoom gameRoom){
		logger.info("Setting default values for game room.");
		gameRoom.setGameRoomName("Backgammon " + LobbyService.roomlabel.getAndIncrement());
		gameRoom.setIsPrivateRoom(false);
		gameRoom.setSpeed(1);
		logger.info("Default values were set with: " + gameRoom);
	}

	public List<GameRoom> getAllGameRooms() {
//		return gameRooms.values().stream().collect(Collectors.toList());
		return gameRooms.getAllGameRooms();
	}

	public boolean isHasLoggedInUser() {
		return homeRepository.isHasLoggedInUser();
	}

	public GameRoom joinGameRoom(GameRoom gameRoom) {		
		lobbyRepository.addSecondPlayer(gameRoom);
		lobbyRepository.addAuthoritiesForSecondPlayer(gameRoom);
		gameRoom.setGame(gameRooms.getGameRoomById(gameRoom).getGame());
		gameRooms.addGameRoom(gameRoom);
//		reloadGameRoom(gameRoom);
		return gameRooms.getGameRoomById(gameRoom);
	}
	
	private void reloadGameRoom(GameRoom gameRoom){
//		gameRoom = lobbyRepository.getGameRoom(gameRoom);
//		gameRooms.replace(gameRoom.getGameRoomId(), gameRoom);
		gameRooms.reloadGameRoom(gameRoom);
	}
}
