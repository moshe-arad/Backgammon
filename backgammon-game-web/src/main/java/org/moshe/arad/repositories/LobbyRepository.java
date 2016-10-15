package org.moshe.arad.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.dao.data.GroupAuthoritiesRepository;
import org.moshe.arad.repositories.dao.data.GroupMembersRepository;
import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.repositories.entities.Group;
import org.moshe.arad.repositories.entities.GroupAuthorities;
import org.moshe.arad.repositories.entities.GroupMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LobbyRepository {

	private final Logger logger = LogManager.getLogger(LobbyRepository.class); 
	
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private GameUserRepository gameUserRepository;
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private GroupAuthoritiesRepository groupAuthoritiesRepository;
	@Autowired
	private GroupMembersRepository groupMembersRepository;
	
	@Transactional
	public GameRoom createNewGameRoomWithLoggedInUser(GameRoom gameRoom){
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		BasicUser basicUser = basicUserRepository.findOne(loggedUserName);
		GameUser loggedUser = basicUser.getGameUser();
		
		setOpenedByAndWhite(gameRoom);
		
		gameRoom.getUsers().add(loggedUser);
		Set<GameRoom> rooms = gameUserRepository.findGameRoomsByLoggedUser(loggedUserName);
		rooms.add(gameRoom);
		loggedUser.setGameRooms(rooms);
		
		Authority auth = new Authority("player_" + gameRoom.getGameRoomName() + "_" + loggedUser.getUserId());
		auth.setBasicUser(basicUser);
		basicUser.getAuthorities().add(auth);
		
		Group groupRoom = new Group("group_" + gameRoom.getGameRoomName() + "_" + loggedUser.getUserId());
		gameRoom.setGroup(groupRoom);
		
		gameRoomRepository.save(gameRoom);
		return gameRoom;
	}
	
	@Transactional
	public void createNewGroupForNewRoom(GameRoom gameRoom){
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		BasicUser basicUser = basicUserRepository.findOne(loggedUserName);
		GameUser loggedUser = basicUser.getGameUser();
		
		Group groupRoom = gameRoomRepository.findOne(gameRoom.getGameRoomId()).getGroup();
		
		List<GroupMembers> groupMembersList = new ArrayList<>();
		GroupMembers groupMembers = new GroupMembers();
		groupMembers.setGroup(groupRoom);
		groupMembers.setBasicUser(basicUser);
		groupMembersList.add(groupMembers);
		groupRoom.setGroupMembers(groupMembersList);
		
		List<GroupAuthorities> groupAuthoritiesList = new ArrayList<>();
		GroupAuthorities groupAuthorities = new GroupAuthorities("player_" + gameRoom.getGameRoomName() + "_" + loggedUser.getUserId());
		groupAuthorities.setGroup(groupRoom);
		groupAuthoritiesList.add(groupAuthorities);
		groupRoom.setGroupAuthorities(groupAuthoritiesList);
		
		
		groupAuthorities.setGroupId(groupRoom.getGroupId());
		groupAuthoritiesRepository.save(groupAuthorities);
		logger.info("after group Authorities Repository");
		
		groupMembersRepository.save(groupMembers);
		logger.info("after group Members Repository");
		
		gameRoom.setGroup(groupRoom);
		gameRoomRepository.save(gameRoom);
		logger.info("after game room Repository");
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
		GameUser loggedUser = basicUserRepository.findOne(loggedUserName).getGameUser();
		gameRoom.setOpenedBy(loggedUser.getUserId());
		gameRoom.setWhite(loggedUser.getUserId());
	}
}
