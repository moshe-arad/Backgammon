package org.moshe.arad.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.dao.hibernate.HibernateGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
	@ContextConfiguration("classpath:user-security-context-test.xml"),
	@ContextConfiguration("classpath:webapp-context-test.xml"),
	@ContextConfiguration("classpath:lobby-context-test.xml")
})
public class HomeControllerTest {

	private final Logger logger = LogManager.getLogger(HomeControllerTest.class);
	private MockMvc mockMvc;
	@Autowired
	UserSecurityService userSecurityService;
	@Autowired
	ApplicationContext context;
	@Autowired
	WebApplicationContext wac;
	@Autowired
	private GameUserRepository gameUserRepository;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		gameUserRepository.deleteAllInBatch();
	}
	
	@After
	public void cleanup(){
		gameUserRepository.deleteAllInBatch();
	}
	
	@Test
	@WithAnonymousUser
	public void goHomeTest1() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect(forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	@Test
	@WithAnonymousUser
	public void goHomeTest2() throws Exception {
		mockMvc.perform(get("/home"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect(forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	@Test
	@WithAnonymousUser
	public void goHomeTest3() throws Exception {
		mockMvc.perform(get("/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect(forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	@Test
	@WithAnonymousUser
	public void goHomeTest4() throws Exception {
		mockMvc.perform(get("/register"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect(forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	@Test
	@WithMockUser
	public void goHomeTest5() throws Exception {
		gameUserRepository.save(new GameUser("John", "Terry", "ashley.cole@gmail.com", "user", "password", "ROLE_USER"));
		mockMvc.perform(get("/"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/lobby/"))
		.andExpect(redirectedUrl("/lobby/"));
	}
	
	@Test
	@WithMockUser
	public void goHomeTest6() throws Exception {
		gameUserRepository.save(new GameUser("John", "Terry", "ashley.cole@gmail.com", "user", "password", "ROLE_USER"));
		mockMvc.perform(get("/home"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/lobby/"))
		.andExpect(redirectedUrl("/lobby/"));
	}
	
	@Test
	@WithMockUser
	public void goHomeTest7() throws Exception {
		gameUserRepository.save(new GameUser("John", "Terry", "ashley.cole@gmail.com", "user", "password", "ROLE_USER"));
		mockMvc.perform(get("/login"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/lobby/"))
		.andExpect(redirectedUrl("/lobby/"));
	}
	
	@Test
	@WithMockUser
	public void goHomeTest8() throws Exception {
		gameUserRepository.save(new GameUser("John", "Terry", "ashley.cole@gmail.com", "user", "password", "ROLE_USER"));
		mockMvc.perform(get("/register"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/lobby/"))
		.andExpect(redirectedUrl("/lobby/"));
	}
	
	@Test
	public void doRegisterTest() throws Exception{
		GameUser user1 = context.getBean("gameUser1", GameUser.class);
		
		mockMvc.perform(post("/register")
				.param("firstName", user1.getFirstName())
				.param("lastName", user1.getLastName())
				.param("email", user1.getEmail())
				.param("userName", user1.getUserName())
				.param("password", user1.getPassword())
				.param("role", user1.getRole()))
		.andExpect(status().isOk())
		.andExpect(model().attribute("gameUser", user1))
		.andExpect(view().name("lobby"))
		.andExpect(forwardedUrl("/WEB-INF/views/lobby.jsp"));
		
		assertEquals(1, gameUserRepository.findAll().size());
		assertEquals(user1, gameUserRepository.findAll().get(0));
				
	}
	
	@Test
	public void doRegisterHasErrorsTest() throws Exception{
		GameUser user1 = context.getBean("gameUser1", GameUser.class);
		user1.setEmail("not an email");
		
		mockMvc.perform(post("/register")
				.param("firstName", user1.getFirstName())
				.param("lastName", user1.getLastName())
				.param("email", "not an email")
				.param("userName", user1.getUserName())
				.param("password", user1.getPassword())
				.param("role", user1.getRole()))
		.andExpect(status().isOk())
		.andExpect(model().attribute("gameUser", user1))
		.andExpect(model().hasErrors())
		.andExpect(model().errorCount(5))
		.andExpect(view().name("home"))
		.andExpect(forwardedUrl("/WEB-INF/views/home.jsp"));
		
		assertEquals(0, gameUserRepository.findAll().size());
	}
	
	@Test
	public void doRegisterFailedToRegisterTest() throws Exception{
		mockMvc.perform(post("/register"))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect(forwardedUrl("/WEB-INF/views/home.jsp"));			
	}
	
	@Test
	public void userNameAvailableValidTest() throws Exception{
		mockMvc.perform(get("/user_name").param("userName", "user name not in DB"))
		.andExpect(status().isOk())
		.andExpect(content().string(""));
	}
	
	@Test
	public void userNameAvailableInValidTest() throws Exception{
		GameUser user1 = context.getBean("gameUser1", GameUser.class);
		
		mockMvc.perform(post("/register")
				.param("firstName", user1.getFirstName())
				.param("lastName", user1.getLastName())
				.param("email", user1.getEmail())
				.param("userName", user1.getUserName())
				.param("password", user1.getPassword())
				.param("role", user1.getRole())).andExpect(status().isOk())
		.andExpect(view().name("lobby"))
		.andExpect(forwardedUrl("/WEB-INF/views/lobby.jsp"));
		
		assertEquals(1, gameUserRepository.findAll().size());
		assertEquals(user1, gameUserRepository.findAll().get(0));
		
		mockMvc.perform(get("/user_name").param("userName", "userName1"))
		.andExpect(status().isOk())
		.andExpect(content().string("User name is not availbale."));
	}
	
	@Test
	public void emailAvailableValidTest() throws Exception{
		mockMvc.perform(get("/email").param("email", "mockEmail@gmail.com"))
		.andExpect(status().isOk())
		.andExpect(content().string(""));
	}
	
	@Test
	public void emailAvailableInValidTest() throws Exception{
		GameUser user1 = context.getBean("gameUser1", GameUser.class);
		
		mockMvc.perform(post("/register")
				.param("firstName", user1.getFirstName())
				.param("lastName", user1.getLastName())
				.param("email", user1.getEmail())
				.param("userName", user1.getUserName())
				.param("password", user1.getPassword())
				.param("role", user1.getRole())).andExpect(status().isOk())
		.andExpect(view().name("lobby"))
		.andExpect(forwardedUrl("/WEB-INF/views/lobby.jsp"));
		
		assertEquals(1, gameUserRepository.findAll().size());
		assertEquals(user1, gameUserRepository.findAll().get(0));
		
		mockMvc.perform(get("/email").param("email", "email1@walla.com"))
		.andExpect(status().isOk())
		.andExpect(content().string("Email is not availbale."));
	}
}
