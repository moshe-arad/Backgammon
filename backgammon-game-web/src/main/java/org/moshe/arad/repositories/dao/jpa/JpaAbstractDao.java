package org.moshe.arad.repositories.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.dao.jpa.interfaces.JpaDao;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class JpaAbstractDao <T, ID extends Serializable> implements JpaDao<T, ID>{

	private final Logger logger = LogManager.getLogger(JpaAbstractDao.class);
	
	@Autowired
	private EntityManagerFactory emf;
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public JpaAbstractDao(){
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	@Override
	public EntityTransaction getTransaction() {
		return getEntityManager().getTransaction();
	}
	
	@Override
	public T findById(ID id) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		T item = null;
		
		try{
			tx.begin();
			item  = em.find(persistentClass, id);
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
	public abstract List<T> findAll();

	@Override
	public boolean save(T entity) {
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
	public void delete(T entity) {
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
	public abstract void deleteAll();
	
	@Override
	public void flush() {
		getEntityManager().flush();
	}

	@Override
	public void clear() {
		getEntityManager().clear();
	}
	
}
