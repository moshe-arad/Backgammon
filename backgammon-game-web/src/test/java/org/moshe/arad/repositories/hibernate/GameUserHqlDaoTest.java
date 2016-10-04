package org.moshe.arad.repositories.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.hibernate.hql.GameUserHqlDaoImpl;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:persistence-context-test.xml", 
						"classpath:user-security-context-test.xml"})
public class GameUserHqlDaoTest {

	final Logger logger = LogManager.getLogger(GameUserHqlDaoTest.class);
	
	@Resource
	GameUserHqlDaoImpl hqlDao;
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	ApplicationContext context;
	Session session;
	Transaction tx;
	
	GameUser user1;
	GameUser user2;
	GameUser user3;
	
	@Before
	public void setup(){
		hqlDao.deleteAll();
		session = hqlDao.getSession();
		tx = session.getTransaction();
		
		user1 = context.getBean("gameUser1", GameUser.class);
		user2 = context.getBean("gameUser2", GameUser.class);
		user3 = context.getBean("gameUser3", GameUser.class);
	}
	
	@Test
	public void findByFirstNameTest(){
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<GameUser> users = hqlDao.findByFirstName("Moshe");
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
			session.close();
		}
	}
	
	@Test
	public void findAllTest(){
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<GameUser> users = hqlDao.findAll();
			
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
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			GameUser user = hqlDao.findByUserName("userName2");
			
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
	
	@Test
	public void getAllUserNamesTest(){
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<String> userNames = hqlDao.getAllUserNames();
			
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
			session.close();
		}
	}
	
	@Test
	public void getAllEmailsTest(){
		try{
			tx.begin();
			session.save(user1);
			session.save(user2);
			session.save(user3);
			tx.commit();
			
			List<String> emails = hqlDao.getAllEmails();
			
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
			session.close();
		}
	}
}
