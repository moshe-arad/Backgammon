package org.moshe.arad.services;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.general.DesEncryption;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	private LobbyService lobbyService;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private DesEncryption desEncryption; 
	
	@Before
	public void setup(){
		gameRoomRepository.deleteAllInBatch();
		gameUserRepository.deleteAllInBatch();
	}
	
	@After
	public void cleanup(){
		gameRoomRepository.deleteAllInBatch();
		gameUserRepository.deleteAllInBatch();
	}
	
	@Test
	@WithMockUser
	public void addNewGameRoomTest(){
		GameRoom gameRoom = new GameRoom("Arad room123",
				new Boolean(false), null, null, null, 2);
		
		GameUser user1 = context.getBean("loggedIn1", GameUser.class);
		user1.setUserName("user");
		
		gameUserRepository.save(user1);
		
		lobbyService.addNewGameRoom(gameRoom);
		
		assertEquals(1, gameRoomRepository.findAll().size());
		assertEquals(gameRoom.getGameRoomName(), gameRoomRepository.findAll().get(0).getGameRoomName());
		assertEquals(2, lobbyService.getAllGameRooms().size());
	}
	
	@Test
	public void joinGameRoomTest(){
		GameUser user1 = context.getBean("loggedIn1", GameUser.class);
		GameUser user2 = context.getBean("loggedIn2", GameUser.class);
		
		gameUserRepository.save(Arrays.asList(user1, user2));
		
		Authentication auth1 = new UsernamePasswordAuthenticationToken(user1, user1.getPassword(), user1.getAuthorities());
		Authentication auth2 = new UsernamePasswordAuthenticationToken(user2, user2.getPassword(), user2.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth1);
		
		GameRoom gameRoom = new GameRoom("Arad room123",
				new Boolean(false), null, null, null, 2);
		
		lobbyService.addNewGameRoom(gameRoom);
		
		Long gameRoomId = gameRoom.getGameRoomId();
		
		assertEquals(1, gameRoomRepository.findAll().size());
		assertEquals(gameRoom.getGameRoomName(), gameRoomRepository.findAll().get(0).getGameRoomName());
		
		SecurityContextHolder.getContext().setAuthentication(auth2);
		
		lobbyService.joinGameRoom(gameRoomId);
		
		assertEquals(user2.getUserId(), gameRoomRepository.findOne(gameRoomId).getBlack());
	}
	
	@Test
	public void encryptAllGameRoomsTokensTest(){
		List<GameRoom> rooms = gameRoomRepository.findAll();
		
		if(rooms.size() > 0){
			List<String> tokens = rooms.stream().map(room -> room.getToken()).collect(Collectors.toList());

			List<String> encryptedTokens = lobbyService.encryptAllGameRoomsTokens();
			
			for(int i=0; i<encryptedTokens.size(); i++){
				assertEquals(tokens.get(i), desEncryption.decrypt(encryptedTokens.get(i)));
			}

		}
	}
}
