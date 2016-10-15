package org.moshe.arad.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;
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
	private GameUserRepository gameUserRepository;
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		gameUserRepository.deleteAllInBatch();
		authorityRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();
		gameRoomRepository.deleteAllInBatch();
		
		GameUser gameUser = new GameUser("John", "Terry", "ashley.cole@gmail.com");
		BasicUser basicUser = new BasicUser("user", "password", true);
		
		gameUser.setBasicUser(basicUser);
		basicUser.setGameUser(gameUser);
		
		gameUserRepository.save(gameUser);
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
	public void openNewGameRoomTest() throws Exception{
		mockMvc.perform(get("/lobby/open")
				.param("gameRoomName", "Arad room123")
				.param("isPrivateRoom", "false")
				.param("speed", "2")).andExpect(status().isOk())
			.andExpect(view().name("backgammon"))
			.andExpect(forwardedUrl("/WEB-INF/views/backgammon.jsp"))
			.andExpect(model().hasErrors())
			.andExpect(model().errorCount(5))
			.andExpect(model().attribute("gameRooms", lobbyService.getAllGameRooms()))
			.andExpect(model().attribute("speedOptions", getSpeedOptions()))
			.andExpect(model().attribute("privateRoomOptions", getPrivateRoomOptions()));
		
		assertEquals(1,gameRoomRepository.findAll().size());
	}
	
	@Test
	@WithMockUser
	@Transactional
	public void openNewGameRoomWithErrorsTest() throws Exception{
		mockMvc.perform(get("/lobby/open")
				.param("gameRoomName", "Ara$$$$$d room123")
				.param("isPrivateRoom", "false")
				.param("speed", "2")).andExpect(status().isOk())
			.andExpect(view().name("backgammon"))
			.andExpect(forwardedUrl("/WEB-INF/views/backgammon.jsp"))
			.andExpect(model().hasErrors())
			.andExpect(model().errorCount(7))
			.andExpect(model().attribute("gameRooms", lobbyService.getAllGameRooms()))
			.andExpect(model().attribute("speedOptions", getSpeedOptions()))
			.andExpect(model().attribute("privateRoomOptions", getPrivateRoomOptions()));		
		
		assertEquals(1,gameRoomRepository.findAll().size());
		assertEquals("Backgammon 1", gameRoomRepository.findAll().get(0).getGameRoomName());
	}
	
	private List<String> getSpeedOptions(){
		List<String> options = new ArrayList<>();
		options.add("High - 30 sec");
		options.add("Medium - 45 sec");
		options.add("Low - 60 sec");
		return options;
	}
	
	private List<String> getPrivateRoomOptions(){
		List<String> options = new ArrayList<>();
		options.add("No");
		options.add("Yes");
		return options;
	}
}
