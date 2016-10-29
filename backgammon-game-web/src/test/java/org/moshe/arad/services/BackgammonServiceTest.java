package org.moshe.arad.services;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.components.GameRooms;
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.dao.data.GroupRepository;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.repositories.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:persistence-context-test.xml"),
	@ContextConfiguration("classpath:backgammon-context-test.xml"),
	@ContextConfiguration("classpath:security-context-test.xml")
})
@WithAnonymousUser
public class BackgammonServiceTest {

	@Autowired
	private BackgammonService backgammonService;
	@Autowired
	private GameRooms gameRooms;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private GameUserRepository gameUserRepository;
	
	@Resource
	GameRoom gameRoom1;
	@Resource
	Group group1;
	@Resource
	BasicUser basicUser1;
	@Resource
	GameUser gameUser1;
	@Resource
	BasicUser basicUser2;
	@Resource
	GameUser gameUser2;
	
	@Before
	public void setup(){
		clearDB();
	}
	
	@After
	public void cleanup(){
		clearDB();
	}
	
	private void clearDB() {
		gameRoomRepository.deleteAllInBatch();		
		groupRepository.deleteAllInBatch();		
		gameUserRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();
	}
	
	@Test
	public void getGameRoomByJsonId(){
		gameRoom1.setGameRoomId(1L);
		gameRoom1.setBlack(1L);
		gameRoom1.setWhite(2L);
		gameRoom1.setOpenedBy(2L);
		gameRoom1.setGroup(group1);
		gameRooms.addGameRoom(gameRoom1);
		
		String gameRoomIdJsonStr = "{\"gameRoomId\":1}";
		
		GameRoom actualGameRoom = backgammonService.getGameRoomByJsonId(gameRoomIdJsonStr);
		
		assertEquals(gameRoom1, actualGameRoom);
	}
	
	@Test
	public void initAndStartGame(){
		gameRoom1.setGroup(group1);
		gameRoom1.setIsGameRoomReady(false);
		
		gameUser1.setBasicUser(basicUser1);
		gameUser2.setBasicUser(basicUser2);
		
		gameUser1 = gameUserRepository.save(gameUser1);
		gameUser2 = gameUserRepository.save(gameUser2);
		
		gameRoom1.setWhite(gameUser1.getUserId());
		gameRoom1.setOpenedBy(gameUser1.getUserId());
		gameRoom1.setBlack(gameUser2.getUserId());
		
		gameRoom1 = gameRoomRepository.save(gameRoom1);
		
		gameRooms.addGameRoom(gameRoom1);
		assertTrue(backgammonService.initAndStartGame(gameRoom1.getGameRoomId()));
		
	}
	
}
