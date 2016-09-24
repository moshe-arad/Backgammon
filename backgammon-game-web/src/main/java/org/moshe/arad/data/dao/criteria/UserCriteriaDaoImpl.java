package org.moshe.arad.data.dao.criteria;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.moshe.arad.data.dao.AbstractDao;
import org.moshe.arad.data.dao.interfaces.UserDao;
import org.moshe.arad.data.entities.User;

public class UserCriteriaDaoImpl extends AbstractDao<User, Long> implements UserDao {

	private final Logger logger = LogManager.getLogger(UserCriteriaDaoImpl.class);
	
	@Override
	public List<User> findByFirstName(String firstName) {
//		List<User> usersByFirstName = null;
//		Session session = getSession();
//		Transaction tx = session.getTransaction();
//		
//		try{
//			tx.begin();
//			@SuppressWarnings("unchecked")
//			Query<User> query = session.createQuery("select u from User u" +
//			" where u.firstName = ?");
//			
//			query.setParameter(0, firstName);
//			
//			usersByFirstName = query.getResultList();
//		}
//		catch (Exception e) {
//			logger.error(e.getMessage());
//			logger.error(e);
//			tx.rollback();
//		}
//		finally{
//			session.close();
//		}
//		
//		return usersByFirstName;
	}

}
