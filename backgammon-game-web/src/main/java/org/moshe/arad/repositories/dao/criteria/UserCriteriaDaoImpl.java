package org.moshe.arad.repositories.dao.criteria;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.moshe.arad.repositories.dao.AbstractDao;
import org.moshe.arad.repositories.dao.interfaces.UserDao;
import org.moshe.arad.repositories.entities.GameUser;


public class UserCriteriaDaoImpl extends AbstractDao<GameUser, Long> implements UserDao {

	private final Logger logger = LogManager.getLogger(UserCriteriaDaoImpl.class);
	
	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public List<GameUser> findByFirstName(String firstName) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<GameUser> users = null;
				
		try{
			logger.info("Try to find users with this first name = " + firstName);
			tx.begin();
			Criterion firstNameCriterion = Restrictions.eq("firstName", firstName);			
			Criteria firstNameCriteria = session.createCriteria(GameUser.class).addOrder(Order.asc("firstName"));
			firstNameCriteria.add(firstNameCriterion);
			users = firstNameCriteria.list();
			tx.commit();
			logger.info("Found " + users.size() + " users with this first name = " + firstName);
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			logger.info("Users with this first name = " + firstName  + " was not found.");
			tx.rollback();
		}
		finally{
			session.close();
		}
		
		return users;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<GameUser> findAll() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<GameUser> users = null;
		
		try{
			logger.info("Try to retrieve all users from DB.");
			tx.begin();
			Criteria usersCriteria = session.createCriteria(GameUser.class);
			users = usersCriteria.list();
			tx.commit();
			logger.info("Retrieved " + users.size() + " users from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to retrieve all users from DB.");
		}
		finally {
			session.close();
		}
		return users;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void deleteAll() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		
		try{
			logger.info("Try to delete all users from DB.");
			tx.begin();
			Criteria usersCriteria = session.createCriteria(GameUser.class);
			List<GameUser> usersToDelete = usersCriteria.list();
			
			for(GameUser user:usersToDelete)
				session.delete(user);
			
			tx.commit();
			logger.info(usersToDelete.size() + "users were delete from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to delete all users from DB.");
		}
		finally {
			session.close();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public GameUser findByUserName(String userName) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		GameUser user = null;
		
		try{
			logger.info("Try to specific user by its user name = " + userName + ".");
			tx.begin();
			Criterion userNameCriterion = Restrictions.eq("userName", userName);
			Criteria userNameCriteria = session.createCriteria(GameUser.class).add(userNameCriterion);
			user = userNameCriteria.list().size() > 0 ? (GameUser)userNameCriteria.list().get(0) : null;
			tx.commit();
			logger.info("User with user name = " + userName + " , was found in DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to find user with user name = " + userName + ".");
		}
		finally {
			session.close();
		}
		
		return user;
	}
	
	@Override
	public boolean save(GameUser entity) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		boolean isSaved = false;
		
		try{
			logger.info("Trying to save user = " + entity);
			tx.begin();
			
			createAndLastUpdate(entity);
			logger.info("*********************" + session.contains(entity));
			session.saveOrUpdate(entity);
			
			tx.commit();
			isSaved = true;
			logger.info("User = " + entity + " was saved.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Falied to save User = " + entity + ".");
		}
		finally{
			session.close();
		}
		return isSaved;
	}

	private void createAndLastUpdate(GameUser entity) {
		entity.setLastUpdatedDate(new Date());
		entity.setLastUpdatedBy(0L);
		if(entity.getUserId() == null){
			entity.setCreatedDate(new Date());
			entity.setCreatedBy(0L);
		}
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<String> getAllUserNames() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<String> userNames = null;
		List<GameUser> users = null;
		
		try{
			logger.info("Try to retrieve all user names from DB.");
			tx.begin();
			Criteria userNamesCriteria = session.createCriteria(GameUser.class);
			users = userNamesCriteria.list();
			tx.commit();
			
			userNames =  users.stream().map(user -> user.getUsername()).collect(Collectors.toList());
			logger.info("Retrieved " + userNames.size() + " user names from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to retrieve user names from DB.");
		}
		finally{
			session.close();
		}
		return userNames;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<String> getAllEmails() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<String> emails = null;
		List<GameUser> users = null;
		
		try{
			logger.info("Try to retrieve all emails from DB.");
			tx.begin();
			Criteria userNamesCriteria = session.createCriteria(GameUser.class);
			users = userNamesCriteria.list();
			tx.commit();
			
			emails =  users.stream().map(user -> user.getEmail()).collect(Collectors.toList());
			logger.info("Retrieved " + emails.size() + " emails from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
			logger.info("Failed to retrieve emails from DB.");
		}
		finally{
			session.close();
		}
		return emails;
	}
}
