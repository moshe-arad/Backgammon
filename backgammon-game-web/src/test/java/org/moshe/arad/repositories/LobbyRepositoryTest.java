package org.moshe.arad.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.data.AuthorityRepository;
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:persistence-context-test.xml"),
	@ContextConfiguration("classpath:lobby-context-test.xml"),
})
public class LobbyRepositoryTest {

	private final Logger logger = LogManager.getLogger(LobbyRepositoryTest.class);
	
	@Autowired
	private LobbyRepository lobbyRepository;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private GameUserRepository gameUserRepository;
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Before
	public void setup(){
		authorityRepository.deleteAllInBatch();
		gameRoomRepository.deleteAllInBatch();
		gameUserRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();		
				
		GameUser gameUser = new GameUser("John", "Terry", "ashley.cole@gmail.com");
		BasicUser basicUser = new BasicUser("user", "password", true);
		
		gameUser.setBasicUser(basicUser);
		basicUser.setGameUser(gameUser);
		
		gameUserRepository.save(gameUser);
	}
	
	@After
	public void cleanup(){
		authorityRepository.deleteAllInBatch();
		gameRoomRepository.deleteAllInBatch();
		gameUserRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();				
	}
	
	@Test
	@WithMockUser
	public void createAndSaveNewGameRoomTest(){
		GameRoom gameRoom = new GameRoom("Arad room123",
				new Boolean(false), null, null, null, 2);
		
		assertNull(gameRoom.getWhite());
		assertNull(gameRoom.getOpenedBy());
		assertNull(gameRoom.getCreatedBy());
		assertNull(gameRoom.getCreatedDate());
		assertNull(gameRoom.getLastModifiedBy());
		assertNull(gameRoom.getLastModifiedDate());
		
		gameRoom = lobbyRepository.createNewGameRoomWithLoggedInUser(gameRoom);
		
		assertNotNull(gameRoom.getWhite());
		assertNotNull(gameRoom.getOpenedBy());
		assertNotNull(gameRoom.getCreatedBy());
		assertNotNull(gameRoom.getCreatedDate());
		assertNotNull(gameRoom.getLastModifiedBy());
		assertNotNull(gameRoom.getLastModifiedDate());
		
		assertEquals(1, gameRoomRepository.findAll().size());
		assertEquals("Arad room123", gameRoomRepository.findAll().get(0).getGameRoomName());
	}
}
