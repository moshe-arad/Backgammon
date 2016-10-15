package org.moshe.arad.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.stream.Collectors;

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
import org.moshe.arad.repositories.entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:persistence-context-test.xml"),
	@ContextConfiguration("classpath:lobby-context-test.xml"),
})
public class LobbyServiceTest {

	private final Logger logger = LogManager.getLogger(LobbyServiceTest.class);
	
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	private GameUserRepository gameUserRepository;
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private ApplicationContext context; 
	
	@Before
	public void setup(){		
		gameUserRepository.deleteAllInBatch();
		authorityRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();
		gameRoomRepository.deleteAllInBatch();
	}
	
	@After
	public void cleanup(){			
		gameUserRepository.deleteAllInBatch();
		authorityRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();
		gameRoomRepository.deleteAllInBatch();
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void addNewGameRoomTest(){
		GameRoom gameRoom = new GameRoom("Arad room123",
				new Boolean(false), null, null, null, 2);
		
		GameUser user1 = context.getBean("loggedIn1", GameUser.class);
		BasicUser basicUser = new BasicUser("user", "password", true);
		
		user1.setBasicUser(basicUser);
		basicUser.setGameUser(user1);
		
		gameUserRepository.save(user1);
		
		lobbyService.addNewGameRoom(gameRoom);
		
		assertEquals(1, authorityRepository.findByBasicUser(basicUser)
		 .stream()
		 .map(auth -> auth.getAuthority())
		 .filter(auth -> !auth.equals("ROLE_WATCHER"))
		 .collect(Collectors.toList())
		 .size());
		
		assertNotNull(gameRoomRepository.findByGameRoomId(gameRoom.getGameRoomId()));	
		Group group = gameRoomRepository.findByGameRoomId(gameRoom.getGameRoomId());
		
		assertEquals(1, group.getGroupAuthorities()
		.stream()
		.map(auth -> auth.getAuthority())
		.filter(auth -> !auth.equals("ROLE_WATCHER"))
		 .collect(Collectors.toList())
		 .size());
		
		assertEquals(1,group.getGroupMembers().size());
		
		assertEquals(1, gameRoomRepository.findAll().size());
		assertEquals(gameRoom.getGameRoomName(), gameRoomRepository.findAll().get(0).getGameRoomName());
		assertEquals(1, lobbyService.getAllGameRooms().size());
	}
	
//	@Test
//	public void joinGameRoomTest(){
//		GameUser user1 = context.getBean("loggedIn1", GameUser.class);
//		GameUser user2 = context.getBean("loggedIn2", GameUser.class);
//		
//		gameUserRepository.save(Arrays.asList(user1, user2));
//		
//		Authentication auth1 = new UsernamePasswordAuthenticationToken(user1, user1.getPassword(), user1.getAuthorities());
//		Authentication auth2 = new UsernamePasswordAuthenticationToken(user2, user2.getPassword(), user2.getAuthorities());
//		
//		SecurityContextHolder.getContext().setAuthentication(auth1);
//		
//		GameRoom gameRoom = new GameRoom("Arad room123",
//				new Boolean(false), null, null, null, 2);
//		
//		lobbyService.addNewGameRoom(gameRoom);
//		
//		String gameRoomToken = gameRoomRepository.findOne(gameRoom.getGameRoomId()).getToken();
////		String encryptedToken = desEncryption.encrypt(gameRoomToken);
//		
//		assertEquals(1, gameRoomRepository.findAll().size());
//		assertEquals(gameRoom.getGameRoomName(), gameRoomRepository.findAll().get(0).getGameRoomName());
//		
//		SecurityContextHolder.getContext().setAuthentication(auth2);
//		
////		lobbyService.joinGameRoom(encryptedToken);
//		
//		assertEquals(user2.getUserId(), gameRoomRepository.findByToken(gameRoomToken).getBlack());
//	}
}
