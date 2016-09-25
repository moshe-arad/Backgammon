package org.moshe.arad.data.dao.hql;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moshe.arad.data.dao.AbstractDao;
import org.moshe.arad.data.dao.interfaces.UserDao;
import org.moshe.arad.data.entities.User;

public class UserHqlDaoImpl extends AbstractDao<User, Long> implements UserDao {

private final Logger logger = LogManager.getLogger(UserHqlDaoImpl.class);
	
	@Override
	public List<User> findByFirstName(String firstName) {
		List<User> usersByFirstName = null;
		Session session = getSession();
		Transaction tx = session.getTransaction();
		
		try{
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<User> query = session.createQuery("select u from User u" +
			" where u.firstName = ?");
			
			query.setParameter(0, firstName);
			
			usersByFirstName = query.getResultList();
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
	public List<User> findAll() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<User> users = null;
		
		try{
			Query<User> query = session.createQuery("select u from User u", User.class);
			users = query.list();
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

	@Override
	public void deleteAll() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		
		try{
			Query<User> query = session.createQuery("delete from User", User.class);
			int rows = query.executeUpdate();
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

}
