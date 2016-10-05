package org.moshe.arad.repositories.dao.hibernate.hql;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moshe.arad.repositories.dao.hibernate.HibernateGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;


public class GameUserHqlDaoImpl implements HibernateGameUserDao {

	private final Logger logger = LogManager.getLogger(GameUserHqlDaoImpl.class);
	
	@Resource
	private SessionFactory mySessionFactory;
	
	@Override
	public List<GameUser> findByFirstName(String firstName) {
		List<GameUser> usersByFirstName = null;
		Session session = getSession();
		Transaction tx = session.getTransaction();
		
		try{
			logger.info("Try to find users with this first name = " + firstName);
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<GameUser> query = session.createQuery("select u from GameUser u" +
			" where u.firstName = ?");
			
			query.setParameter(0, firstName);
			
			usersByFirstName = query.getResultList();
			tx.commit();
			logger.info("Found " + usersByFirstName.size() + " users with this first name = " + firstName);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e);
			tx.rollback();
			logger.info("Users with this first name = " + firstName  + " was not found.");
		}
		finally{
			session.close();
		}
		
		return usersByFirstName;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<GameUser> findAll() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<GameUser> users = null;
		
		try{
			logger.info("Try to retrieve all users from DB.");
			tx.begin();
			Query<GameUser> query = session.createQuery("select u from GameUser u", GameUser.class);
			users = query.list();
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

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteAll() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		
		try{
			logger.info("Try to delete all users from DB.");
			tx.begin();
			Query query = session.createQuery("delete from GameUser");
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
		finally {
			session.close();
		}		
	}

	@Override
	public GameUser findByUserName(String userName) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		GameUser user = null;
		
		try{
			logger.info("Try to specific user by its user name = " + userName + ".");
			tx.begin();
			Query<GameUser> query = session.createQuery("select u from GameUser u "
					+ "where u.userName = ?", GameUser.class);
			query.setParameter(0, userName);
			user = (GameUser) query.getSingleResult();
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
	
	@Override
	public List<String> getAllUserNames() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<String> userNames = null;
	
		try{
			logger.info("Try to retrieve all user names from DB.");
			tx.begin();
			Query<String> query = session.createQuery("select u.userName from GameUser u", String.class);
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
			session.close();
		}
		return userNames;
	}

	@Override
	public List<String> getAllEmails() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<String> emails = null;
	
		try{
			logger.info("Try to retrieve all emails from DB.");
			tx.begin();
			Query<String> query = session.createQuery("select u.email from GameUser u", String.class);
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
			session.close();
		}
		return emails;
	}

	@Override
	public Session getSession() {
		try{
			return mySessionFactory.getCurrentSession();
		}
		catch(Exception ex){
			return mySessionFactory.openSession();
		}
		
	}

	@Override
	public Transaction getTransaction() {
		return getSession().getTransaction();
	}

	@Override
	public GameUser findById(Long id) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		GameUser item = null;
		
		try{
			tx.begin();
			item  = session.load(GameUser.class, id);
			tx.commit();
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
		}
		finally{
			session.close();
		}
		return item;
	}

	@Override
	public void delete(GameUser entity) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		
		try{
			tx.begin();
			session.delete(entity);
			tx.commit();
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
		}
		finally{
			session.close();
		}
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public void clear() {
		getSession().clear();
	}
}





















