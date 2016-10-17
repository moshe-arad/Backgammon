package org.moshe.arad.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.moshe.arad.repositories.dao.data.AuthorityRepository;
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
import org.springframework.util.StringUtils;

@Repository
public class SecurityRepository {

	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private GameUserRepository gameUserRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private GroupMembersRepository groupMembersRepository;
	@Autowired
	private GroupAuthoritiesRepository groupAuthoritiesRepository;
	
	/*** read operations ***/
	
	public GameUser getLoggedInGameUser(){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		BasicUser loggedInBasicUser = basicUserRepository.findOne(userName);
		return gameUserRepository.findByBasicUser(loggedInBasicUser);		
	}
	
	public BasicUser getLoggedInBasicUser(){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return basicUserRepository.findOne(userName);
	}
	
	public GameUser getGameUserByGameUserId(Long gameUserId){
		if(gameUserId == null) return null;
		return gameUserRepository.findOne(gameUserId);
	}
	
	public GameUser getGameUserByBasicUser(BasicUser basicUser){
		if(basicUser == null) return null;
		return gameUserRepository.findByBasicUser(basicUser);
	}
	
	public BasicUser getBasicUserByUserName(String userName){
		if((userName == null) || (StringUtils.isEmpty(userName))) return null;
		return basicUserRepository.findOne(userName);
	}
	
	public BasicUser getBasicUserByGameUser(GameUser gameUser){
		if(gameUser == null) return null;
		String userName = gameUser.getUsername();
		if((userName == null) || (StringUtils.isEmpty(userName))) return null;
		return basicUserRepository.findOne(userName);
	}
	
	public List<Authority> getAuthoritiesOfGameUser(GameUser gameUser){
		if(gameUser == null) return null;
		String userName = gameUser.getUsername();
		if((userName == null) || (StringUtils.isEmpty(userName))) return null;
		BasicUser basicUserFromDb = basicUserRepository.findOne(userName);
		return authorityRepository.findByBasicUser(basicUserFromDb);
	}
	
	public List<Authority> getAuthoritiesOfBasicUser(BasicUser basicUser){
		if(basicUser == null) return null;
		return authorityRepository.findByBasicUser(basicUser);
	}
	
	public List<Authority> getAuthoritiesByUserName(String userName){
		if((userName == null) || (StringUtils.isEmpty(userName))) return null;
		BasicUser basicUser = basicUserRepository.findOne(userName);
		if(basicUser == null) return null;
		return authorityRepository.findByBasicUser(basicUser);
	}
	
	public GameRoom getGameRoomByGroup(Group group){
		if(group == null) return null;
		return gameRoomRepository.findByGroup(group);
	}
	
	public Group getGroupByGameRoom(GameRoom gameRoom){
		if(gameRoom == null) return null;
		return gameRoomRepository.findGroupByGameRoomId(gameRoom.getGameRoomId());
	}
	
	public Group getGroupByGameRoomId(Long gameRoomId){
		if(gameRoomId == null) return null;
		return gameRoomRepository.findGroupByGameRoomId(gameRoomId);
	}
	
	public List<GroupMembers> getGroupMembersByGroup(Group group){
		if(group == null) return null;
		return groupMembersRepository.findGroupMembersByGroup(group);
	}
	
	public List<GroupMembers> getGroupMembersByBasicUser(BasicUser basicUser){
		if(basicUser == null) return null;
		return groupMembersRepository.findByBasicUser(basicUser);
	}
	
	public List<GroupAuthorities> getGroupAuthoritiesByGroup(Group group){
		if(group == null) return null;
		return groupAuthoritiesRepository.findByGroup(group);
	}
	
	/*** insert - update, operations ***/
	
	public GameUser saveNewUser(GameUser gameUser, BasicUser basicUser){
		if(gameUser != null && basicUser != null){
			gameUser.setBasicUser(basicUser);
			gameUser = gameUserRepository.save(gameUser);
		}	
		return gameUser;
	}
	
	public GameUser saveNewUserWithAuthority(GameUser gameUser, BasicUser basicUser, Authority auth){
		if(gameUser != null && basicUser != null && auth != null){
			auth.setBasicUser(basicUser);
			gameUser.setBasicUser(basicUser);
			basicUser.setAuthorities(Arrays.asList(auth));
			gameUser = gameUserRepository.save(gameUser);
		}
		return gameUser;
	}
	
	public GameUser saveNewUserWithAuthorities(GameUser gameUser, BasicUser basicUser, List<Authority> authList){
		if(gameUser != null && basicUser != null && authList != null){
			gameUser.setBasicUser(basicUser);
			basicUser.setAuthorities(authList);
			gameUser = gameUserRepository.save(gameUser);
		}
		return gameUser;
	}
	
	public BasicUser saveNewAuthorityOnBasicUser(BasicUser basicUser, Authority authority){
		if(basicUser == null || authority == null) return null;
		authority.setBasicUser(basicUser);
		basicUser.setAuthorities(Arrays.asList(authority));
		basicUser = basicUserRepository.save(basicUser);
		return basicUser;
	}
	
	public GameRoom saveNewGameRoomAndGroup(GameRoom gameRoom, Group group){
		if(gameRoom != null && group != null){
			gameRoom.setGroup(group);
			gameRoom  = gameRoomRepository.save(gameRoom);
		}
		return gameRoom;
	}
	
	public GameRoom saveNewGameRoomAndGroupWithNewUser(GameRoom gameRoom, Group group, GameUser gameUser, BasicUser basicUser){
		if(gameUser == null || basicUser == null) return null;
		
		gameUser = saveNewUser(gameUser, basicUser);
		
		if(gameRoom != null && group != null){
			gameRoom.setGroup(group);
			List<GameRoom> gameRooms = new ArrayList<>(1000);
			gameRooms.add(gameRoom);
			gameUser.setGameRooms(gameRooms);
			gameUser = gameUserRepository.save(gameUser);
			return gameUser.getGameRooms().get(0);			
		}
		
		return null;
	}
	
	public GameUser saveNewGameRoomAndGroupWithNewUserAndAuthorities(GameRoom gameRoom, Group group, GameUser gameUser, BasicUser basicUser, List<Authority> authList){
		if(gameUser == null || basicUser == null) return null;
		
		gameUser = saveNewUserWithAuthorities(gameUser, basicUser, authList);
		
		if(gameRoom != null && group != null){
			gameRoom.setGroup(group);
			List<GameRoom> gameRooms = new ArrayList<>(1000);
			gameRooms.add(gameRoom);
			gameUser.setGameRooms(gameRooms);
			gameUser = gameUserRepository.save(gameUser);
			return gameUser;
		}
		
		return null;
	}
	
	public void saveUserAsGroupMember(BasicUser basicUser, List<Group> groups){
		if(basicUser == null || groups == null) return;
		for(Group group:groups){
			GroupMembers groupMembers = new GroupMembers();
			groupMembers.setBasicUser(basicUser);
			groupMembers.setGroup(group);
			groupMembersRepository.save(groupMembers);			
		}
	}
	
	public void saveAuthoritiesForGroup(Group group, List<Authority> authList){
		if(authList == null || group == null) return;
		for(Authority auth:authList){
			GroupAuthorities groupAuthorities = new GroupAuthorities(auth.getAuthority());
			groupAuthorities.setGroupId(group.getGroupId());
			groupAuthorities.setGroup(group);
			groupAuthoritiesRepository.save(groupAuthorities);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
