package org.moshe.arad.repositories;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.data.AuthorityRepository;
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.dao.data.GroupAuthoritiesRepository;
import org.moshe.arad.repositories.dao.data.GroupMembersRepository;
import org.moshe.arad.repositories.dao.data.GroupRepository;
import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.repositories.entities.Group;
import org.moshe.arad.repositories.entities.GroupAuthorities;
import org.moshe.arad.repositories.entities.GroupMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:persistence-context-test.xml",
						"classpath:security-context-test.xml"})
@WithAnonymousUser
public class SecurityRepositoryTest {

	@Resource
	private GameUser gameUser1;
	@Resource
	private GameUser gameUser2;
	@Resource
	private GameUser gameUser3;
	@Resource
	private BasicUser basicUser1;
	@Resource
	private BasicUser basicUser2;
	@Resource
	private BasicUser basicUser3;
	
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private GameUserRepository gameUserRepository;
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupMembersRepository groupMembersRepository;
	@Autowired
	private GroupAuthoritiesRepository groupAuthoritiesRepository;
	
	@Resource
	List<Authority> authList1;
	@Resource
	List<Authority> authList2;
	@Resource
	List<Authority> authList3;
	@Resource
	GameRoom gameRoom1;
	@Resource
	Group group1;
	
	@Before
	public void setup(){		
		gameRoomRepository.deleteAllInBatch();
		groupAuthoritiesRepository.deleteAllInBatch();
		groupMembersRepository.deleteAllInBatch();
		groupRepository.deleteAllInBatch();		
		authorityRepository.deleteAllInBatch();
		gameUserRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();				
		
		gameRoom1.setGroup(group1);
		
		for(Authority auth:authList1){
			auth.setBasicUser(basicUser1);
		}
		basicUser1.setAuthorities(authList1);
		
		for(Authority auth:authList2){
			auth.setBasicUser(basicUser2);
		}
		basicUser2.setAuthorities(authList2);
		
		for(Authority auth:authList3){
			auth.setBasicUser(basicUser3);
		}
		basicUser3.setAuthorities(authList3);
		
		gameUser1.setBasicUser(basicUser1);
		gameUser2.setBasicUser(basicUser2);
		gameUser3.setBasicUser(basicUser3);
		
		gameUser1.setUserId(null);
		gameUser2.setUserId(null);
		gameUser3.setUserId(null);
		
		gameUserRepository.save(Arrays.asList(gameUser1, gameUser2, gameUser3));
		basicUserRepository.save(Arrays.asList(basicUser1, basicUser2, basicUser3));
		
		gameRoom1.setGameRoomId(null);
		group1.setGroupId(null);
		gameRoom1.setOpenedBy(gameUser1.getUserId());
		gameRoomRepository.save(gameRoom1);	
		
		GroupMembers groupMembers1 = new GroupMembers();
		groupMembers1.setGroup(group1);
		groupMembers1.setBasicUser(basicUser1);
		
		groupMembersRepository.save(groupMembers1);
		
		GroupAuthorities groupAuthorities1 = new GroupAuthorities("ROLE_WATCHER");
		groupAuthorities1.setGroupId(group1.getGroupId());
		
		groupAuthoritiesRepository.save(groupAuthorities1);
	}
	
	@After
	public void cleanup(){		
		gameRoomRepository.deleteAllInBatch();
		groupAuthoritiesRepository.deleteAllInBatch();
		groupMembersRepository.deleteAllInBatch();
		groupRepository.deleteAllInBatch();		
		authorityRepository.deleteAllInBatch();
		gameUserRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();
	}
	
	@Test
	@WithMockUser(username = "username1", password="password1", roles={"WATCHER"})
	public void getLoggedInGameUser(){
		GameUser loggedInGameUser = securityRepository.getLoggedInGameUser();
		assertEquals(gameUser1.getFirstName(), loggedInGameUser.getFirstName());
		assertEquals(gameUser1.getLastName(), loggedInGameUser.getLastName());
		assertEquals(gameUser1.getEmail(), loggedInGameUser.getEmail());
	}
	
	@Test
	@WithMockUser(username = "username1", password="password1", roles={"WATCHER"})
	public void getLoggedInBasicUser(){
		BasicUser loggedInBasicUser = securityRepository.getLoggedInBasicUser();
		assertEquals(basicUser1.getUserName(), loggedInBasicUser.getUserName());
		assertEquals(basicUser1.getPassword(), loggedInBasicUser.getPassword());
	}
	
	@Test
	public void getGameUserByGameUserId(){
		GameUser gameUserById = securityRepository.getGameUserByGameUserId(gameUser1.getUserId());
		assertEquals(gameUser1, gameUserById);
	}
	
	@Test
	public void getGameUserByBasicUser(){
		GameUser gameUserFromDb = securityRepository.getGameUserByBasicUser(basicUser1);
		assertEquals(gameUser1, gameUserFromDb);
	}
	
	@Test
	public void getBasicUserByUserName(){
		BasicUser basicUserFromDb = securityRepository.getBasicUserByUserName(basicUser1.getUserName());
		assertEquals(basicUser1, basicUserFromDb);
	}
	
	@Test
	public void getBasicUserByGameUser(){
		BasicUser basicUserFromDb = securityRepository.getBasicUserByGameUser(gameUser1);
		assertEquals(basicUser1, basicUserFromDb);
	}
	
	@Test
	public void getAuthoritiesOfGameUser(){
		List<Authority> authListGameUser = securityRepository.getAuthoritiesOfGameUser(gameUser1);
		for(int i=0; i<authListGameUser.size(); i++){
			assertTrue( authListGameUser.contains(authList1.get(i)));
		}
	}
	
	@Test
	public void getAuthoritiesOfBasicUser(){
		List<Authority> authListGameUser = securityRepository.getAuthoritiesOfBasicUser(basicUser1);
		for(int i=0; i<authListGameUser.size(); i++){
			assertTrue(  authListGameUser.contains(authList1.get(i)));
		}
	}
	
	@Test
	public void getAuthoritiesByUserName(){
		List<Authority> authListGameUser = securityRepository.getAuthoritiesByUserName(basicUser1.getUserName());
		for(int i=0; i<authListGameUser.size(); i++){
			assertTrue( authListGameUser.contains(authList1.get(i)));
		}
	}
	
	@Test
	public void getGameRoomByGroup(){
		GameRoom gameRoomFromDb = securityRepository.getGameRoomByGroup(group1);
		assertEquals(gameRoom1, gameRoomFromDb);
	}
	
	@Test
	public void getGroupByGameRoom(){
		Group groupFromDb = securityRepository.getGroupByGameRoom(gameRoom1);
		assertEquals(group1, groupFromDb);
	}
	
	@Test
	public void getGroupByGameRoomId(){
		Group groupFromDb = securityRepository.getGroupByGameRoomId(gameRoom1.getGameRoomId());
		assertEquals(group1, groupFromDb);
	}
	
	@Test
	public void getGroupMembersByGroup(){
		List<GroupMembers> groupMembersList = securityRepository.getGroupMembersByGroup(group1);
		assertEquals(1, groupMembersList.size());
		assertEquals(group1.getGroupId(), groupMembersList.get(0).getGroup().getGroupId());
		assertEquals(basicUser1.getUserName(), groupMembersList.get(0).getBasicUser().getUserName());
	}
	
	@Test
	public void getGroupMembersByBasicUser(){
		List<GroupMembers> groupMembersList = securityRepository.getGroupMembersByBasicUser(basicUser1);
		assertEquals(1, groupMembersList.size());
		assertEquals(group1.getGroupId(), groupMembersList.get(0).getGroup().getGroupId());
		assertEquals(basicUser1.getUserName(), groupMembersList.get(0).getBasicUser().getUserName());
	}
	
	@Test
	public void getGroupAuthoritiesByGroup(){
		List<GroupAuthorities> groupAuthoritiesList = securityRepository.getGroupAuthoritiesByGroup(group1);
		assertEquals(1, groupAuthoritiesList.size());
		assertEquals("ROLE_WATCHER", groupAuthoritiesList.get(0).getAuthority());
	}
	
	@Test
	public void saveNewUser(){
		securityRepository.saveNewUser(gameUser2, basicUser2);
		GameUser gameUserFromDb = gameUserRepository.findOne(gameUser2.getUserId());
		BasicUser basicUserFromDb = basicUserRepository.findOne(basicUser2.getUserName());
		assertEquals(gameUser2, gameUserFromDb);
		assertEquals(basicUser2, basicUserFromDb);
	}
	
	@Test
	public void saveNewUserWithAuthority(){
		authorityRepository.deleteAllInBatch();
		securityRepository.saveNewUserWithAuthority(gameUser2, basicUser2, authList2.get(0));
		
		List<Authority> authListFromDb = securityRepository.getAuthoritiesOfBasicUser(basicUser2);
		GameUser gameUserFromDb = gameUserRepository.findOne(gameUser2.getUserId());
		BasicUser basicUserFromDb = basicUserRepository.findOne(basicUser2.getUserName());
		
		assertEquals(1, authListFromDb.size());
		assertTrue(authListFromDb.contains(authList2.get(0)));
		assertEquals(gameUser2, gameUserFromDb);
		assertEquals(basicUser2, basicUserFromDb);
	}
	
	@Test
	public void saveNewUserWithAuthorities(){
		authorityRepository.deleteAllInBatch();
		securityRepository.saveNewUserWithAuthorities(gameUser2, basicUser2, authList2);
		
		List<Authority> authListFromDb = securityRepository.getAuthoritiesOfBasicUser(basicUser2);
		GameUser gameUserFromDb = gameUserRepository.findOne(gameUser2.getUserId());
		BasicUser basicUserFromDb = basicUserRepository.findOne(basicUser2.getUserName());
		
		assertEquals(2, authListFromDb.size());
		assertTrue(authListFromDb.containsAll(authList2));
		assertEquals(gameUser2, gameUserFromDb);
		assertEquals(basicUser2, basicUserFromDb);
	}
	
	@Test
	public void saveNewGameRoomAndGroup(){
		gameRoomRepository.deleteAllInBatch();
		gameRoom1 = securityRepository.saveNewGameRoomAndGroup(gameRoom1, group1);
		GameRoom gameRoomFromDb = gameRoomRepository.findOne(gameRoom1.getGameRoomId());
		Group groupFromDb = groupRepository.findOne(group1.getGroupId());
		assertEquals(gameRoom1, gameRoomFromDb);
		assertEquals(group1, groupFromDb);
	}
}
