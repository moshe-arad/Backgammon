package org.moshe.arad.repositories.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.moshe.arad.repositories.dao.hibernate.interfaces.HibernateDao;

public abstract class HibernateAbstractDao<T, ID extends Serializable> implements HibernateDao<T, ID> {

	private final Logger logger = LogManager.getLogger(HibernateAbstractDao.class);
	
	@Resource
	private SessionFactory mySessionFactory;
	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public HibernateAbstractDao(){
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
	public T findById(ID id) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		T item = null;
		
		try{
			tx.begin();
			item  = session.load(persistentClass, id);
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
	public abstract List<T> findAll();

	@Override
	public boolean save(T entity) {
		Session session = getSession();
		Transaction tx = session.getTransaction();
		boolean isSaved = false;
		
		try{
			tx.begin();
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

	@Override
	public void delete(T entity) {
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
	public abstract void deleteAll();
	
	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public void clear() {
		getSession().clear();
	}	
}
