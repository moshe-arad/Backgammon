package org.moshe.arad.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.springframework.stereotype.Service;

@Service
public class LobbyService {

	private final Logger logger = LogManager.getLogger(LobbyService.class);
	
	private Map<Long, GameRoom> gameRooms = new ConcurrentHashMap<>(1000, 0.75F, 1000);
	private static AtomicLong roomlabel = new AtomicLong(1);
	
	@Autowired
	private LobbyRepository lobbyRepository;
	@Autowired
	private HomeRepository homeRepository;
	@Autowired
	private SecurityRepository securityRepository;
	
	@PostConstruct
	public void init(){
		gameRooms.putAll(lobbyRepository.getAllGameRooms());
	}
	
	public GameRoom addNewGameRoom(GameRoom gameRoom) {
		logger.info("New game room was opened, details: " + gameRoom);
		GameRoom roomInDb = lobbyRepository.createNewGameRoomWithLoggedInUser(gameRoom);
		lobbyRepository.createNewGroupForNewRoom(roomInDb);
		logger.info("New game room was added to DB, details: " + gameRoom);
		gameRooms.put(roomInDb.getGameRoomId(), roomInDb);
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
		return gameRooms.values().stream().collect(Collectors.toList());
	}

	public boolean isHasLoggedInUser() {
		return homeRepository.isHasLoggedInUser();
	}

	public void joinGameRoom(GameRoom gameRoom) {
		lobbyRepository.addSecondPlayer(gameRoom);
		lobbyRepository.addAuthoritiesForSecondPlayer(gameRoom);
		reloadGameRoom(gameRoom);
	}
	
	private void reloadGameRoom(GameRoom gameRoom){
		gameRoom = lobbyRepository.getGameRoom(gameRoom);
		gameRooms.replace(gameRoom.getGameRoomId(), gameRoom);
	}
	
	private void beginGameInRoom(GameRoom gameRoom){
		gameRoom = gameRooms.get(gameRoom.getGameRoomId());
		
		GameUser white =  securityRepository.getGameUserByGameUserId(gameRoom.getWhite());
		Player playerWhite = new BackgammonPlayer(white.getFirstName(),
				white.getLastName(), 100, BackgammonTurn.getInstance(), 
				true);
		

		GameUser black =  securityRepository.getGameUserByGameUserId(gameRoom.getBlack());
		Player playerBlack = new BackgammonPlayer(black.getFirstName(),
				black.getLastName(), 100, BackgammonTurn.getInstance(), 
				false);
		
		((Backgammon)gameRoom.getGame()).setFirstPlayer(playerWhite);
		((Backgammon)gameRoom.getGame()).setSecondPlayer(playerBlack);
		Backgammon backgammonGame = (Backgammon) gameRoom.getGame();
		backgammonGame.start();
	}
}
