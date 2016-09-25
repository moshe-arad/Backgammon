package org.moshe.arad.data.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.criteria.UserCriteriaDaoImpl;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:persistence-context-test.xml")
public class UserCriteriaDaoTest {

	@Autowired
	UserCriteriaDaoImpl userCriteriaDao;
	@Autowired
	SessionFactory sessionFactory;
	Session session;
	Transaction tx;
	final Logger logger = LogManager.getLogger(UserCriteriaDaoTest.class);
	
	@Before
	public void setup(){
		userCriteriaDao.deleteAll();
		session = userCriteriaDao.getSession();
		tx = session.getTransaction();
	}
	
	@Test
	public void findByFirstNameTest(){
		GameUser user1 = new GameUser("Moshe", "lastName1", "email1", "userName1", "password1",
				"role1", new Date(), 1L, new Date(), 1L);
		GameUser user2 = new GameUser("Jimi", "lastName2", "email2", "userName2", "password2",
				"role2", new Date(), 2L, new Date(), 2L);
		GameUser user3 = new GameUser("Moshe", "lastName3", "email3", "userName3", "password3",
				"role3", new Date(), 3L, new Date(), 3L);
	
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<GameUser> users = userCriteriaDao.findByFirstName("Moshe");
			for(GameUser user:users)
				assertEquals("Moshe", user.getFirstName());
			assertEquals(2, users.size());
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			throw ex;
		}
		finally {
			session.close();
		}
	}
	
	@Test
	public void findAllTest(){
		GameUser user1 = new GameUser("Moshe", "lastName1", "email1", "userName1", "password1",
				"role1", new Date(), 1L, new Date(), 1L);
		GameUser user2 = new GameUser("Jimi", "lastName2", "email2", "userName2", "password2",
				"role2", new Date(), 2L, new Date(), 2L);
		GameUser user3 = new GameUser("Moshe", "lastName3", "email3", "userName3", "password3",
				"role3", new Date(), 3L, new Date(), 3L);
	
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<GameUser> users = userCriteriaDao.findAll();
			
			assertEquals(3, users.size());
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			throw ex;
		}
		finally {
			session.close();
		}
	}
	
	@Test
	public void findByUserNameTest(){
		GameUser user1 = new GameUser("Moshe", "lastName1", "email1", "userName1", "password1",
				"role1", new Date(), 1L, new Date(), 1L);
		GameUser user2 = new GameUser("Jimi", "lastName2", "email2", "userName2", "password2",
				"role2", new Date(), 2L, new Date(), 2L);
		GameUser user3 = new GameUser("Moshe", "lastName3", "email3", "userName3", "password3",
				"role3", new Date(), 3L, new Date(), 3L);
	
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			GameUser user = userCriteriaDao.findByUserName("userName2");
			
			assertEquals("userName2", user.getUserName());
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			throw ex;
		}
		finally {
			session.close();
		}
	}
}
