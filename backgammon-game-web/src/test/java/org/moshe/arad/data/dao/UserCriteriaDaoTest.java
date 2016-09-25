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
import org.moshe.arad.data.dao.criteria.UserCriteriaDaoImpl;
import org.moshe.arad.data.entities.User;
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
		User user1 = new User("Moshe", "lastName1", "email1", "userName1", "password1",
				"role1", new Date(), 1L, new Date(), 1L);
		User user2 = new User("Jimi", "lastName2", "email2", "userName2", "password2",
				"role2", new Date(), 2L, new Date(), 2L);
		User user3 = new User("Moshe", "lastName3", "email3", "userName3", "password3",
				"role3", new Date(), 3L, new Date(), 3L);
	
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<User> users = userCriteriaDao.findByFirstName("Moshe");
			for(User user:users)
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
		User user1 = new User("Moshe", "lastName1", "email1", "userName1", "password1",
				"role1", new Date(), 1L, new Date(), 1L);
		User user2 = new User("Jimi", "lastName2", "email2", "userName2", "password2",
				"role2", new Date(), 2L, new Date(), 2L);
		User user3 = new User("Moshe", "lastName3", "email3", "userName3", "password3",
				"role3", new Date(), 3L, new Date(), 3L);
	
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<User> users = userCriteriaDao.findAll();
			
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
}
