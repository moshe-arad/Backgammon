package org.moshe.arad.repositories.dao.jpa.interfaces;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.moshe.arad.repositories.dao.general.Dao;

public interface JpaDao<T, ID> extends Dao<T, ID>{
	
	public EntityManager getEntityManager();
	
	public EntityTransaction getTransaction();
}
