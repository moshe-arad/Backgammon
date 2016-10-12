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
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.dao.data.RepositoryUtils;
import org.moshe.arad.repositories.entities.BasicUser;
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
	@Autowired
	BasicUserRepository basicUserRepository;
	
	@Resource
	GameUser gameUser1;
	@Resource
	BasicUser basicUser1;
	
	@Before
	public void setup(){
		logger.info("Initializing test DB.");
		gameUserRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();

//		RepositoryUtils.setCreateAndUpdateSys(basicUser1);
//		RepositoryUtils.setCreateAndUpdateSys(gameUser1);
//		gameUser1.setBasicUser(basicUser1);
//		basicUser1.setGameUser(gameUser1);
		
	}
	
	@After
	public void cleanup(){
		gameUserRepository.deleteAllInBatch();
	}
	
	@Test
	public void isUserNameAvailableNotAvailableTest(){
		userSecurityService.registerNewUser(gameUser1, basicUser1);
		assertFalse(userSecurityService.isUserNameAvailable(gameUser1.getUsername()));
	}
	
	@Test
	public void isUserNameAvailableTest(){
		userSecurityService.registerNewUser(gameUser1, basicUser1);
		assertTrue(userSecurityService.isUserNameAvailable("userName2"));
	}
	
	@Test
	public void isEmailAvailableNotAvailableTest(){
		userSecurityService.registerNewUser(gameUser1, basicUser1);
		assertFalse(userSecurityService.isEmailAvailable(context.getBean("gameUser1", GameUser.class).getEmail()));
	}
	
	@Test
	public void isEmailAvailableTest(){
		userSecurityService.registerNewUser(gameUser1, basicUser1);
		assertTrue(userSecurityService.isEmailAvailable("email2@walla.com"));
	}
}
