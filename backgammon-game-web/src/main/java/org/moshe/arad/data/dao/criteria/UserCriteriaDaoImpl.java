package org.moshe.arad.data.dao.criteria;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.moshe.arad.data.dao.AbstractDao;
import org.moshe.arad.data.dao.interfaces.UserDao;
import org.moshe.arad.data.entities.User;

public class UserCriteriaDaoImpl extends AbstractDao<User, Long> implements UserDao {

	private final Logger logger = LogManager.getLogger(UserCriteriaDaoImpl.class);
	
	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public List<User> findByFirstName(String firstName) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<User> users = null;
				
		try{
			tx.begin();
			Criterion firstNameCriterion = Restrictions.eq("firstName", firstName);			
			Criteria firstNameCriteria = session.createCriteria(User.class).addOrder(Order.asc("firstName"));
			firstNameCriteria.add(firstNameCriterion);
			users = firstNameCriteria.list();
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
		}
		finally{
			session.close();
		}
		
		return users;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<User> findAll() {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		List<User> users = null;
		
		try{
			tx.begin();
			Criteria usersCriteria = session.createCriteria(User.class);
			users = usersCriteria.list();
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

}
