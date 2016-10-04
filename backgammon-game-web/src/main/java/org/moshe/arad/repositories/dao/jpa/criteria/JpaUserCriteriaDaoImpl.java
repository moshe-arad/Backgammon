package org.moshe.arad.repositories.dao.jpa.criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.moshe.arad.repositories.dao.jpa.JpaAbstractDao;
import org.moshe.arad.repositories.dao.jpa.interfaces.JpaGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;

public class JpaUserCriteriaDaoImpl extends JpaAbstractDao<GameUser, Long> implements JpaGameUserDao{

	private final Logger logger = LogManager.getLogger(JpaUserCriteriaDaoImpl.class);
	@Override
	public List<GameUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GameUser> findByFirstName(String firstName) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<GameUser> users = null;
				
		try{
			logger.info("Try to find users with this first name = " + firstName);
			tx.begin();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<GameUser> criteriaQuery = cb.createQuery(GameUser.class);
			
			Root<GameUser> root = criteriaQuery.from(GameUser.class);
			criteriaQuery.select(root);
			
			TypedQuery<GameUser> query = em.createQuery(criteriaQuery);
			
			users = query.getResultList();
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
			em.close();
		}
		
		return users;
	}

	@Override
	public GameUser findByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllUserNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllEmails() {
		// TODO Auto-generated method stub
		return null;
	}

}
