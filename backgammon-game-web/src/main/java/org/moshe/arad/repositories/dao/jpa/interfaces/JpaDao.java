package org.moshe.arad.repositories.dao.jpa.interfaces;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public interface JpaDao<T, ID> {
	
	public EntityManager getEntityManager();
	
	public EntityTransaction getTransaction();
	
	public T findById(ID id);
	
	public List<T> findAll();
	
	public boolean save(T entity);
	
	public void delete(T entity);
	
	public void deleteAll();
	
	public void flush();
	
	public void clear();
}
