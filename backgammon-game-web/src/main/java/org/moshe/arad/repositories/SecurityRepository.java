package org.moshe.arad.repositories;

import java.util.Arrays;
import java.util.List;

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
	
	public void saveNewUser(GameUser gameUser, BasicUser basicUser){
		if(gameUser != null && basicUser != null){
			gameUser.setBasicUser(basicUser);
			gameUserRepository.save(gameUser);
		}		
	}
	
	public void saveNewUserWithAuthority(GameUser gameUser, BasicUser basicUser, Authority auth){
		if(gameUser != null && basicUser != null && auth != null){
			gameUser.setBasicUser(basicUser);
			basicUser.setAuthorities(Arrays.asList(auth));
			gameUserRepository.save(gameUser);
		}		
	}
	
	public void saveNewUserWithAuthorities(GameUser gameUser, BasicUser basicUser, List<Authority> authList){
		if(gameUser != null && basicUser != null && authList != null){
			gameUser.setBasicUser(basicUser);
			basicUser.setAuthorities(authList);
			gameUserRepository.save(gameUser);
		}		
	}
	
	public GameRoom saveNewGameRoomAndGroup(GameRoom gameRoom, Group group){
		if(gameRoom != null && group != null){
			gameRoom.setGroup(group);
			gameRoom  = gameRoomRepository.save(gameRoom);
		}
		return gameRoom;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
