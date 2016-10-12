package org.moshe.arad.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:persistence-context-test.xml",
						"classpath:user-security-context-test.xml"})
public class UserSecurityServiceTest {

	private final Logger logger = LogManager.getLogger(UserSecurityServiceTest.class);
	
	@Autowired
	ApplicationContext context;
	@Autowired
	UserSecurityService userSecurityService;
	@Autowired
	GameUserRepository gameUserRepository;
	
	@Before
	public void setup(){
		logger.info("Initializing test DB.");

		gameUserRepository.deleteAllInBatch(); 
	}
	
	@After
	public void cleanup(){
		gameUserRepository.deleteAllInBatch();
	}
	
	@Test
	public void isUserNameAvailableNotAvailableTest(){
		userSecurityService.registerNewUser(context.getBean("gameUser1", GameUser.class), "Role_Test");
		assertFalse(userSecurityService.isUserNameAvailable(context.getBean("gameUser1", GameUser.class).getUserName()));
	}
	
	@Test
	public void isUserNameAvailableTest(){
		userSecurityService.registerNewUser(context.getBean("gameUser1", GameUser.class), "Role_Test");
		assertTrue(userSecurityService.isUserNameAvailable("userName2"));
	}
	
	@Test
	public void isEmailAvailableNotAvailableTest(){
		userSecurityService.registerNewUser(context.getBean("gameUser1", GameUser.class), "Role_Test");
		assertFalse(userSecurityService.isEmailAvailable(context.getBean("gameUser1", GameUser.class).getEmail()));
	}
	
	@Test
	public void isEmailAvailableTest(){
		userSecurityService.registerNewUser(context.getBean("gameUser1", GameUser.class), "Role_Test");
		assertTrue(userSecurityService.isEmailAvailable("email2@walla.com"));
	}
}
