package org.moshe.arad.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.hibernate.HibernateGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:persistence-context-test.xml",
						"classpath:user-security-context-test.xml"})
public class UserSecurityRepositoryHibernateTest {
	
	private final Logger logger = LogManager.getLogger(UserSecurityRepositoryHibernateTest.class);
	
	@Autowired
	ApplicationContext context;
	@Autowired
	UserSecurityRepositoryHibernate userSecurityRepo;
	@Resource
	HibernateGameUserDao hibernateGameUserCriteriaDao;
	
	@Before
	public void setup(){
		logger.info("Initializing test DB.");
 
		hibernateGameUserCriteriaDao.save(context.getBean("gameUser1", GameUser.class));
		hibernateGameUserCriteriaDao.save(context.getBean("gameUser2", GameUser.class));
		hibernateGameUserCriteriaDao.save(context.getBean("gameUser3", GameUser.class));
	}
	
	@After
	public void cleanup(){
		hibernateGameUserCriteriaDao.deleteAll();
	}
	
	@Test
	public void loadUserByUserNameTest(){
		GameUser expected = context.getBean("gameUser1", GameUser.class);
		GameUser actual = userSecurityRepo.loadUserByUsername("userName1");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void loadUserByUserNameNotInDBTest(){
		GameUser actual = userSecurityRepo.loadUserByUsername("userNotInDB");
		
		assertNull(actual);
	}
	
	@Test
	public void registerNewUserTest(){
		hibernateGameUserCriteriaDao.deleteAll();
		GameUser gameUser = context.getBean("gameUser1", GameUser.class);
		assertTrue(userSecurityRepo.registerNewUser(gameUser));
		List<GameUser> users = hibernateGameUserCriteriaDao.findAll();
		
		assertEquals(1, users.size());
		assertEquals(gameUser.getUsername(), users.get(0).getUserName());
	}
	
	@Test
	public void registerNewUserNullValueTest(){
		assertFalse(userSecurityRepo.registerNewUser(null));
	}
	
	@Test
	public void getAllUserNamesTest(){
		Set<String> userNames = userSecurityRepo.getAllUserNames();
		
		assertTrue(userNames.contains(context.getBean("gameUser1", GameUser.class).getUsername()));
		assertTrue(userNames.contains(context.getBean("gameUser2", GameUser.class).getUsername()));
		assertTrue(userNames.contains(context.getBean("gameUser3", GameUser.class).getUsername()));
		assertEquals(3, userNames.size());
	}
	
	@Test
	public void getAllUserNamesEmptySetTest(){
		hibernateGameUserCriteriaDao.deleteAll();
		Set<String> userNames = userSecurityRepo.getAllUserNames();
		
		assertEquals(0, userNames.size());
	}
	
	@Test
	public void getAllEmailsTest(){
		Set<String> emails = userSecurityRepo.getAllEmails();
		
		assertTrue(emails.contains(context.getBean("gameUser1", GameUser.class).getEmail()));
		assertTrue(emails.contains(context.getBean("gameUser2", GameUser.class).getEmail()));
		assertTrue(emails.contains(context.getBean("gameUser3", GameUser.class).getEmail()));
		assertEquals(3, emails.size());
	}
	
	@Test
	public void getAllEmailsEmptySetTest(){
		hibernateGameUserCriteriaDao.deleteAll();
		Set<String> emails = userSecurityRepo.getAllEmails();
		
		assertEquals(0, emails.size());
	}
}
