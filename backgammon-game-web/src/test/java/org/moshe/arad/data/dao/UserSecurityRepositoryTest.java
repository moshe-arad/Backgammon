package org.moshe.arad.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.UserSecurityRepository;
import org.moshe.arad.repositories.dao.interfaces.UserDao;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.hibernate.Session;
import org.hibernate.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:persistence-context-test.xml", 
						"classpath:user-security-context-test.xml"})
public class UserSecurityRepositoryTest {
	
	private final Logger logger = LogManager.getLogger(UserSecurityRepositoryTest.class);
	
	@Autowired
	ApplicationContext context;
	@Autowired
	UserSecurityRepository userSecurityRepo;
	@Resource
	UserDao userDao;
	
	@Before
	public void setup(){
		logger.info("Initializing test DB.");

		userDao.deleteAll(); 
//		GameUser u1 = context.getBean("gameUser1", GameUser.class);
//		logger.info("******************" +u1);
		userDao.save(context.getBean("gameUser1", GameUser.class));
		userDao.save(context.getBean("gameUser2", GameUser.class));
		userDao.save(context.getBean("gameUser3", GameUser.class));
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
}
