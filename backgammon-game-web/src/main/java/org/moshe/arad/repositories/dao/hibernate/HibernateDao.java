package org.moshe.arad.repositories.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.moshe.arad.repositories.dao.Dao;


public interface HibernateDao<T, ID> extends Dao<T,ID>{
	
	public Session getSession();
	
	public Transaction getTransaction();
}
