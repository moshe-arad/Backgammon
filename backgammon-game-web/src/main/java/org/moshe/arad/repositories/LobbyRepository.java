package org.moshe.arad.repositories;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.repositories.entities.Group;
import org.moshe.arad.repositories.entities.GroupMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class LobbyRepository {

	private final Logger logger = LogManager.getLogger(LobbyRepository.class); 
	
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private SecurityRepository securityRepository;
	
	public GameRoom createNewGameRoomWithLoggedInUser(GameRoom gameRoom){		
		setOpenedByAndWhite(gameRoom);
		GameUser loggedInGameUser = securityRepository.getLoggedInGameUser();
		String authorityToUser = "player_" + gameRoom.getGameRoomName() + "_" + loggedInGameUser.getUserId();
		loggedInGameUser = securityRepository.saveNewUserWithAuthority(loggedInGameUser, loggedInGameUser.getBasicUser(), new Authority(authorityToUser));
		Group groupRoom = new Group("group_" + gameRoom.getGameRoomName() + "_" + loggedInGameUser.getUserId());
		gameRoom = securityRepository.saveNewGameRoomAndGroupWithNewUser(gameRoom, groupRoom, loggedInGameUser, loggedInGameUser.getBasicUser());
		return gameRoom;
	}
	
	public void createNewGroupForNewRoom(GameRoom gameRoom){
		GameUser loggedInGameUser = securityRepository.getLoggedInGameUser();
		
		securityRepository.saveUserAsGroupMember(loggedInGameUser.getBasicUser(), Arrays.asList(gameRoom.getGroup()));
		List<Authority> authList = securityRepository.getAuthoritiesByUserName(loggedInGameUser.getBasicUser().getUserName());
		securityRepository.saveAuthoritiesForGroup(gameRoom.getGroup(), authList);
	}
	
	public List<GameRoom> getAllGameRooms(){
		return gameRoomRepository.findAll();
	}
	
	public void addSecondPlayer(GameRoom gameRoom){
		Long userId = ((GameUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
		gameRoom = gameRoomRepository.findOne(gameRoom.getGameRoomId());
		gameRoom.setBlack(userId);
		gameRoomRepository.save(gameRoom);
	}
	
	private void setOpenedByAndWhite(GameRoom gameRoom) {
		String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		GameUser loggedUser = basicUserRepository.findOne(loggedUserName).getGameUser();
		gameRoom.setOpenedBy(loggedUser.getUserId());
		gameRoom.setWhite(loggedUser.getUserId());
	}

	public void addAuthoritiesForSecondPlayer(GameRoom gameRoom) {
		BasicUser basicUserLoggedIn = securityRepository.getLoggedInBasicUser();
		GameUser gameUserLoggedIn = securityRepository.getLoggedInGameUser();
		Authority auth = new Authority("player_" + gameRoom.getGameRoomName() + "_" + gameUserLoggedIn.getUserId());
		
		securityRepository.saveNewAuthorityOnBasicUser(basicUserLoggedIn, auth);
		securityRepository.saveUserAsGroupMember(basicUserLoggedIn, Arrays.asList(gameRoom.getGroup()));		
		securityRepository.saveAuthoritiesForGroup(gameRoom.getGroup(), Arrays.asList(auth));
	}
}

























