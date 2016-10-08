package org.moshe.arad.controllers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("classpath:persistence-context-test.xml"),
	@ContextConfiguration("classpath:lobby-context-test.xml"),
	@ContextConfiguration("classpath:webapp-context-test.xml")
})
public class LobbyControllerTest {

	private final Logger logger = LogManager.getLogger(LobbyControllerTest.class);
	private MockMvc mockMvc;
	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	@Autowired
	GameUserRepository gameUserRepository;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		gameRoomRepository.deleteAllInBatch();
		
		gameUserRepository.deleteAllInBatch();
		gameUserRepository.save(new GameUser("John", "Terry", "ashley.cole@gmail.com", "user", "password", "ROLE_USER"));
	}
	
	@After
	public void cleanup(){
		gameRoomRepository.deleteAllInBatch();
		gameUserRepository.deleteAllInBatch();
	}
	
	@Test
	@WithMockUser
	public void openNewGameRoomTest() throws Exception{
		GameUser user = gameUserRepository.findByUserName("user");
		
		GameRoom gameRoom = new GameRoom("Arad room123",
				new Boolean(false), user.getUserId(), user.getUserId(), null, 2);
		mockMvc.perform(get("/lobby/open")
				.param("gameRoomName", "Arad room123")
				.param("isPrivateRoom", "false")
				.param("speed", "2")).andExpect(status().isOk())
			.andExpect(view().name("backgammon"))
			.andExpect(forwardedUrl("/WEB-INF/views/backgammon.jsp"))
			.andExpect(model().hasErrors())
			.andExpect(model().errorCount(5));
		
		assertEquals(1,gameRoomRepository.findAll().size());
	}
}
