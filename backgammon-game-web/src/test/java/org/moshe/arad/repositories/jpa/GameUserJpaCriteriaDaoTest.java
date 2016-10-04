package org.moshe.arad.repositories.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.hibernate.criteria.GameUserHibernateCriteriaDaoImpl;
import org.moshe.arad.repositories.dao.jpa.criteria.JpaUserCriteriaDaoImpl;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:persistence-context-test.xml", 
						"classpath:user-security-context-test.xml"})
public class GameUserJpaCriteriaDaoTest {

	final Logger logger = LogManager.getLogger(GameUserJpaCriteriaDaoTest.class);
	
	@Autowired
	JpaUserCriteriaDaoImpl userCriteriaDao;
	@Autowired
	EntityManagerFactory entityManagerFactory;
	@Autowired
	ApplicationContext context;
	EntityManager entityManager;
	EntityTransaction tx;
	
	GameUser user1;
	GameUser user2;
	GameUser user3;
	
	@Before
	public void setup(){
		userCriteriaDao.deleteAll();
		entityManager = userCriteriaDao.getEntityManager();
		tx = entityManager.getTransaction();
		
		user1 = context.getBean("gameUser1", GameUser.class);
		user2 = context.getBean("gameUser2", GameUser.class);
		user3 = context.getBean("gameUser3", GameUser.class);
	}
	
	@Test
	public void findByFirstNameTest(){
		try{
			tx.begin();
			entityManager.persist(user1);
			entityManager.persist(user2);
			entityManager.persist(user3);
			tx.commit();
			
			List<GameUser> users = userCriteriaDao.findByFirstName("Moshe");
			for(GameUser user:users)
				assertEquals("Moshe", user.getFirstName());
			assertEquals(1, users.size());
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			throw ex;
		}
		finally {
			entityManager.close();
		}
	}
	
	@Test
	public void findAllTest(){	
		try{
			tx.begin();
			entityManager.persist(user1);
			entityManager.persist(user2);
			entityManager.persist(user3);
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
			entityManager.close();
		}
	}
	
	@Test
	public void findByUserNameTest(){
		try{
			tx.begin();
			entityManager.persist(user1);
			entityManager.persist(user2);
			entityManager.persist(user3);
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
			entityManager.close();
		}
	}
	
	@Test
	public void getAllUserNamesTest(){	
		try{
			tx.begin();
			entityManager.persist(user1);
			entityManager.persist(user2);
			entityManager.persist(user3);
			tx.commit();
			
			List<String> userNames = userCriteriaDao.getAllUserNames();
			
			assertEquals(3, userNames.size());
			assertTrue(userNames.contains(user1.getUsername()));
			assertTrue(userNames.contains(user2.getUsername()));
			assertTrue(userNames.contains(user3.getUsername()));
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			throw ex;
		}
		finally {
			entityManager.close();
		}
	}
	
	@Test
	public void getAllEmailsTest(){
		try{
			tx.begin();
			entityManager.persist(user1);
			entityManager.persist(user2);
			entityManager.persist(user3);
			tx.commit();
			
			List<String> emails = userCriteriaDao.getAllEmails();
			
			assertEquals(3, emails.size());
			assertTrue(emails.contains(user1.getEmail()));
			assertTrue(emails.contains(user2.getEmail()));
			assertTrue(emails.contains(user3.getEmail()));
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			throw ex;
		}
		finally {
			entityManager.close();
		}
	}
}
