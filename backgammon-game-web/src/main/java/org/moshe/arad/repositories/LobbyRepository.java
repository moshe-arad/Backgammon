package org.moshe.arad.repositories;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.dao.data.RepositoryUtils;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class LobbyRepository {

	@Autowired
	GameRoomRepository gameRoomRepository;
	@Autowired
	GameUserRepository gameUserRepository;
	@Autowired
	BasicUserRepository basicUserRepository;
	
	public GameRoom createNewGameRoomWithLoggedInUser(GameRoom gameRoom){
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		GameUser loggedUser = basicUserRepository.findByUserName(loggedUserName).getGameUser();
		RepositoryUtils.setCreateAndUpdateByUserId(gameRoom, loggedUser.getUserId());
		setOpenedByAndWhite(gameRoom);
		gameRoom.getUsers().add(loggedUser);
		loggedUser.getGameRooms().add(gameRoom);
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
		setUpdateCreateInfo(gameRoom);
		gameRoomRepository.save(gameRoom);
	}
	
	private void setOpenedByAndWhite(GameRoom gameRoom) {
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		GameUser loggedUser = basicUserRepository.findByUserName(loggedUserName).getGameUser();
		gameRoom.setOpenedBy(loggedUser.getUserId());
		gameRoom.setWhite(loggedUser.getUserId());
	}

	private void setUpdateCreateInfo(GameRoom gameRoom) {
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		GameUser loggedUser = basicUserRepository.findByUserName(loggedUserName).getGameUser();
		Date now = new Date();
		if(gameRoom.getCreatedBy() == null)	gameRoom.setCreatedBy(loggedUser.getUserId());
		if(gameRoom.getCreatedDate() == null)	gameRoom.setCreatedDate(now);
		
		gameRoom.setLastUpdatedBy(loggedUser.getUserId());
		gameRoom.setLastUpdatedDate(now);		
	}
}
