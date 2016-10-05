package org.moshe.arad.repositories.dao.jpa.jpql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.dao.jpa.JpaAbstractDao;
import org.moshe.arad.repositories.dao.jpa.interfaces.JpaGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;

public class GameUserJpqlDaoImpl extends JpaAbstractDao<GameUser, Long> implements JpaGameUserDao {

	private final Logger logger = LogManager.getLogger(GameUserJpqlDaoImpl.class);
	
	@Override
	public List<GameUser> findByFirstName(String firstName) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<GameUser> usersByFirstName = null;
		
		try{
			logger.info("Try to find users with this first name = " + firstName);
			tx.begin();
			
			TypedQuery<GameUser> query = em.createQuery("select u from GameUser u where u.firstName = :firstName", GameUser.class);
			query.setParameter("firstName", firstName);
			usersByFirstName = query.getResultList();
			tx.commit();
			logger.info("Found " + usersByFirstName.size() + " users with this first name = " + firstName);
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Users with this first name = " + firstName  + " was not found.");
		}
		finally{
			em.close();
		}
		return usersByFirstName;
	}

	@Override
	public GameUser findByUserName(String userName) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		GameUser user = null;
		
		try{
			logger.info("Try to specific user by its user name = " + userName + ".");
			tx.begin();
			
			TypedQuery<GameUser> query = em.createQuery("from GameUser u where u.userName = :userName", GameUser.class);
			query.setParameter("userName", userName);
			user = query.getSingleResult();
			tx.commit();
			logger.info("User with user name = " + userName + " , was found in DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to find user with user name = " + userName + ".");
		}
		finally{
			em.close();
		}
		return user;
	}

	@Override
	public List<String> getAllUserNames() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<String> userNames = null;
		
		try{
			logger.info("Try to retrieve all user names from DB.");
			tx.begin();
			
			TypedQuery<String> query = em.createQuery("select u.userName from GameUser u", String.class);
			userNames = query.getResultList();
			tx.commit();
			logger.info("Retrieved " + userNames.size() + " user names from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to retrieve user names from DB.");
		}
		finally{
			em.close();
		}
		return userNames;
	}

	@Override
	public List<String> getAllEmails() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<String> emails = null;
		
		try{
			logger.info("Try to retrieve all emails from DB.");
			tx.begin();
			
			TypedQuery<String> query = em.createQuery("select u.email from GameUser u", String.class);
			emails = query.getResultList();
			tx.commit();
			logger.info("Retrieved " + emails.size() + " emails from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to retrieve emails from DB.");
		}
		finally{
			em.close();
		}
		return emails;
	}

	@Override
	public List<GameUser> findAll() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<GameUser> users = null;
		
		try{
			logger.info("Try to retrieve all users from DB.");
			tx.begin();
			
			TypedQuery<GameUser> query = em.createQuery("select u from GameUser u", GameUser.class);
			users = query.getResultList();
			tx.commit();
			logger.info("Retrieved " + users.size() + " users from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to retrieve all users from DB.");
		}
		finally{
			em.close();
		}
		return users;
	}

	@Override
	public void deleteAll() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try{
			logger.info("Try to delete all users from DB.");
			tx.begin();
			
			Query query = em.createQuery("delete from GameUser u");
			int rows = query.executeUpdate();
			tx.commit();
			logger.info(rows + "users were delete from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to delete all users from DB.");
		}
		finally{
			em.close();
		}	
	}

}
