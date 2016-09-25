package org.moshe.arad.repositories.dao.interfaces;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface Dao<T, ID> {
	
	public Session getSession();
	
	public Transaction getTransaction();
	
	public T findById(ID id);
	
	public List<T> findAll();
	
	public void save(T entity);
	
	public void delete(T entity);
	
	public void deleteAll();
	
	public void flush();
	
	public void clear();
}
