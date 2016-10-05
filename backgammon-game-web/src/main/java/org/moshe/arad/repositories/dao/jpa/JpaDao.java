package org.moshe.arad.repositories.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.moshe.arad.repositories.dao.Dao;



public interface JpaDao<T, ID> extends Dao<T, ID>{
	
	public EntityManager getEntityManager();
	
	public EntityTransaction getTransaction();
}
