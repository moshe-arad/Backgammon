package org.moshe.arad.repositories.dao.hibernate.interfaces;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.moshe.arad.repositories.dao.general.Dao;

public interface HibernateDao<T, ID> extends Dao<T,ID>{
	
	public Session getSession();
	
	public Transaction getTransaction();
}
