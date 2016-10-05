package org.moshe.arad.repositories.dao.general;

import java.util.List;

public interface Dao<T, ID> {

	public T findById(ID id);
	
	public List<T> findAll();
	
	public boolean save(T entity);
	
	public void delete(T entity);
	
	public void deleteAll();
	
	public void flush();
	
	public void clear();
}
