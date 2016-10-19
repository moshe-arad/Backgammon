package org.moshe.arad.components;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameRooms {

	private Map<Long, GameRoom> gameRooms = new ConcurrentHashMap<>(1000, 0.75F, 1000);
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	
	@PostConstruct
	public void loadRoomsFromDb(){
		List<GameRoom> gameRoomsFromDb = gameRoomRepository.findAll();
		gameRoomsFromDb.stream().forEach(room -> gameRooms.put(room.getGameRoomId(), room));
	}
	
	public void addGameRoom(GameRoom gameRoom){
		gameRooms.put(gameRoom.getGameRoomId(), gameRoom);
	}
	
	public List<GameRoom> getAllGameRooms(){
		return gameRooms.values().stream().collect(Collectors.toList());
	}
	
	public GameRoom getGameRoomById(GameRoom gameRoom){
		return gameRooms.get(gameRoom.getGameRoomId());
	}
	
	public GameRoom getGameRoomById(Long gameRoomId){
		return gameRooms.get(gameRoomId);
	}
	
	public void reloadGameRoom(GameRoom gameRoom){
		gameRoom = securityRepository.getGameRoomById(gameRoom.getGameRoomId());
		gameRooms.put(gameRoom.getGameRoomId(), gameRoom);
	}
}
