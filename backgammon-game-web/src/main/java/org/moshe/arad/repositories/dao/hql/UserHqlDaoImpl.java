package org.moshe.arad.repositories.dao.hql;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moshe.arad.repositories.dao.AbstractDao;
import org.moshe.arad.repositories.dao.interfaces.UserDao;
import org.moshe.arad.repositories.entities.GameUser;


public class UserHqlDaoImpl extends AbstractDao<GameUser, Long> implements UserDao {

private final Logger logger = LogManager.getLogger(UserHqlDaoImpl.class);
	
	@Override
	public List<GameUser> findByFirstName(String firstName) {
		List<GameUser> usersByFirstName = null;
		Session session = getSession();
		Transaction tx = session.getTransaction();
		
		try{
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<GameUser> query = session.createQuery("select u from GameUser u" +
			" where u.firstName = ?");
			
			query.setParameter(0, firstName);
			
			usersByFirstName = query.getResultList();
			tx.commit();
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e);
			tx.rollback();
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
			tx.begin();
			Query<GameUser> query = session.createQuery("select u from GameUser u", GameUser.class);
			users = query.list();
			tx.commit();
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
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
			tx.begin();
			Query query = session.createQuery("delete from GameUser");
			int rows = query.executeUpdate();
			tx.commit();
			logger.info(rows + " rows affected, and deleted.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
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
			tx.begin();
			Query query = session.createQuery("select u from GameUser u "
					+ "where u.userName = ?");
			query.setParameter(0, userName);
			user = (GameUser) query.getSingleResult();
			tx.commit();
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
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
			tx.begin();
			
			createAndLastUpdate(entity);
			
			session.saveOrUpdate(entity);
			tx.commit();
			isSaved = true;
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
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
}
