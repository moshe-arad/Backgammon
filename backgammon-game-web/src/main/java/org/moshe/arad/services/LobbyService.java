package org.moshe.arad.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.LobbyRepository;
import org.moshe.arad.repositories.UserSecurityRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LobbyService {

	private final Logger logger = LogManager.getLogger(LobbyService.class);
	
	private List<GameRoom> gameRooms = new CopyOnWriteArrayList<GameRoom>();
	private static AtomicLong roomlabel = new AtomicLong(1);
	
	@Autowired
	LobbyRepository lobbyRepository;
	@Autowired
	UserSecurityRepository userSecurityRepository;
	
	@PostConstruct
	public void init(){
		gameRooms.addAll(lobbyRepository.getAllGameRooms());
	}
	
	public void addNewGameRoom(GameRoom gameRoom) {
		logger.info("New game room was opened, details: " + gameRoom);
		GameRoom roomInDb = lobbyRepository.createAndSaveNewGameRoom(gameRoom);
		logger.info("New game room was added to DB, details: " + gameRoom);
		gameRooms.add(roomInDb);
		logger.info("game room was added successfully, details: " + gameRoom);
	}
	
	public void setDefaultValues(GameRoom gameRoom){
		logger.info("Setting default values for game room.");
		gameRoom.setGameRoomName("Backgammon " + LobbyService.roomlabel.getAndIncrement());
		gameRoom.setIsPrivateRoom(false);
		gameRoom.setSpeed(1);
		logger.info("Default values were set with: " + gameRoom);
	}
	
	@Deprecated
	private void createDummyGameRooms(){
		GameRoom gameRoom1 = new GameRoom("Dummy_Room_1", new Boolean(false), null, null, null, 0);
		GameRoom gameRoom2 = new GameRoom("Dummy_Room_2", new Boolean(false), null, null, null, 1);
		GameRoom gameRoom3 = new GameRoom("Dummy_Room_3", new Boolean(false), null, null, null, 2);
		GameRoom gameRoom4 = new GameRoom("Dummy_Room_4", new Boolean(true), null, null, null, 1);
		GameRoom gameRoom5 = new GameRoom("Dummy_Room_5", new Boolean(true), null, null, null, 2);
		
		gameRooms.add(gameRoom1);
		gameRooms.add(gameRoom2);
		gameRooms.add(gameRoom3);
		gameRooms.add(gameRoom4);
		gameRooms.add(gameRoom5);
	}

	public List<GameRoom> getAllGameRooms() {
		return gameRooms;
	}

	public boolean isHasLoggedInUser() {
		return userSecurityRepository.isHasLoggedInUser();
	}
}
