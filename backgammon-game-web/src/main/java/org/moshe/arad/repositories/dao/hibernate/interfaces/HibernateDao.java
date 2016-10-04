package org.moshe.arad.repositories.dao.hibernate.interfaces;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface HibernateDao<T, ID> {
	
	public Session getSession();
	
	public Transaction getTransaction();
	
	public T findById(ID id);
	
	public List<T> findAll();
	
	public boolean save(T entity);
	
	public void delete(T entity);
	
	public void deleteAll();
	
	public void flush();
	
	public void clear();
}
