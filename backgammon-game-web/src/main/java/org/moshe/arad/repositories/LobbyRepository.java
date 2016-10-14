package org.moshe.arad.repositories;

import java.util.List;
import java.util.Set;

import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.repositories.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LobbyRepository {

	@Autowired
	GameRoomRepository gameRoomRepository;
	@Autowired
	GameUserRepository gameUserRepository;
	@Autowired
	BasicUserRepository basicUserRepository;
	
	@Transactional
	public GameRoom createNewGameRoomWithLoggedInUser(GameRoom gameRoom){
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		GameUser loggedUser = basicUserRepository.findByUserName(loggedUserName).getGameUser();
		
		setOpenedByAndWhite(gameRoom);
		gameRoom.getUsers().add(loggedUser);
		Set<GameRoom> rooms = gameUserRepository.findGameRoomsByLoggenUser(loggedUserName);
		rooms.add(gameRoom);
		loggedUser.setGameRooms(rooms);
		Group groupRoom = new Group("group_" + gameRoom.getGameRoomName() + "_" + gameRoom.getGameRoomId());
		
		
		gameRoom.setGroup(groupRoom);
		gameRoomRepository.save(gameRoom);
		return gameRoom;
	}
	
	public List<GameRoom> getAllGameRooms(){
		return gameRoomRepository.findAll();
	}
	
	public void addSecondPlayer(Long roomId){
		Long userId = ((GameUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		GameRoom gameRoom = gameRoomRepository.findOne(roomId);
		gameRoom.setBlack(userId);
		gameRoomRepository.save(gameRoom);
	}
	
	private void setOpenedByAndWhite(GameRoom gameRoom) {
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		GameUser loggedUser = basicUserRepository.findByUserName(loggedUserName).getGameUser();
		gameRoom.setOpenedBy(loggedUser.getUserId());
		gameRoom.setWhite(loggedUser.getUserId());
	}
}
