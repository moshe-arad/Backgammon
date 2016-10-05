package org.moshe.arad.repositories.dao.jpa.criteria;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.dao.jpa.JpaGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;

public class JpaUserCriteriaDaoImpl implements JpaGameUserDao{

	private final Logger logger = LogManager.getLogger(JpaUserCriteriaDaoImpl.class);
	
	@Resource
	private EntityManagerFactory entityManagerFactory;
	
	@Override
	public List<GameUser> findAll() {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<GameUser> users = null;
				
		try{
			logger.info("Try to retrieve all users from DB.");
			tx.begin();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<GameUser> criteriaQuery = cb.createQuery(GameUser.class);
			
			Root<GameUser> fromGameUser = criteriaQuery.from(GameUser.class);
			criteriaQuery.select(fromGameUser);
			
			TypedQuery<GameUser> query = em.createQuery(criteriaQuery);
			users = query.getResultList();
			
			tx.commit();
			logger.info("Retrieved " + users.size() + " users from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			logger.info("Failed to retrieve all users from DB.");
			tx.rollback();
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
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaDelete<GameUser> criteriaQuery = cb.createCriteriaDelete(GameUser.class);
			
			criteriaQuery.from(GameUser.class);
			int rows = em.createQuery(criteriaQuery).executeUpdate();
			
			tx.commit();
			logger.info(rows + "users were delete from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			logger.info("Failed to delete all users from DB.");
			tx.rollback();
		}
		finally{
			em.close();
		}	
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
			
			Root<GameUser> fromGameUser = criteriaQuery.from(GameUser.class);
			criteriaQuery.select(fromGameUser)
				.where(new Predicate[]{
						cb.equal(fromGameUser.get("firstName"), firstName)
				});
			
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
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		GameUser user = null;
		
		try{
			logger.info("Try to specific user by its user name = " + userName + ".");
			tx.begin();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<GameUser> criteriaQuery = cb.createQuery(GameUser.class);
			
			Root<GameUser> fromGameUser = criteriaQuery.from(GameUser.class);
			
			criteriaQuery.select(fromGameUser)
				.where(new Predicate[]{
						cb.equal(fromGameUser.get("userName"), userName)
						});
			
			TypedQuery<GameUser> typedQuery = em.createQuery(criteriaQuery);
			user = typedQuery.getSingleResult();
			
			tx.commit();
			logger.info("User with user name = " + userName + " , was found in DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			logger.info("Failed to find user with user name = " + userName + ".");
			tx.rollback();
		}
		finally {
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
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = cb.createQuery(String.class);
			
			Root<GameUser> gameUserFrom = criteriaQuery.from(GameUser.class);
			criteriaQuery.select(gameUserFrom.get("userName"));
			
			TypedQuery<String> query = em.createQuery(criteriaQuery);
			userNames = query.getResultList();
			
			tx.commit();
			logger.info("Retrieved " + userNames.size() + " user names from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			logger.info("Failed to retrieve user names from DB.");
			tx.rollback();
		}
		finally {
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
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<String> criteriaQuery = cb.createQuery(String.class);
			
			Root<GameUser> gameUserFrom = criteriaQuery.from(GameUser.class);
			criteriaQuery.select(gameUserFrom.get("email"));
			
			TypedQuery<String> query = em.createQuery(criteriaQuery);
			emails = query.getResultList();
			
			tx.commit();
			logger.info("Retrieved " + emails.size() + " emails from DB.");
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			logger.info("Failed to retrieve emails from DB.");
			tx.rollback();
		}
		finally {
			em.close();
		}
		
		return emails;
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	@Override
	public EntityTransaction getTransaction() {
		return getEntityManager().getTransaction();
	}

	@Override
	public GameUser findById(Long id) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		GameUser item = null;
		
		try{
			tx.begin();
			item  = em.find(GameUser.class, id);
			tx.commit();
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
		}
		finally{
			em.close();
		}
		return item;
	}

	@Override
	public boolean save(GameUser entity) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		boolean isSaved = false;
		
		try{
			tx.begin();
			em.merge(entity);
			tx.commit();
			isSaved = true;
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
		}
		finally{
			em.close();
		}
		return isSaved;
	}

	@Override
	public void delete(GameUser entity) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try{
			tx.begin();
			em.remove(entity);
			tx.commit();
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			tx.rollback();
		}
		finally{
			em.close();
		}
	}

	@Override
	public void flush() {
		getEntityManager().flush();
	}

	@Override
	public void clear() {
		getEntityManager().clear();
	}

}
