package org.moshe.arad.repositories.dao.hibernate;


import org.moshe.arad.repositories.dao.GameUserDao;
import org.moshe.arad.repositories.entities.GameUser;


public interface HibernateGameUserDao extends GameUserDao, HibernateDao<GameUser,Long> {
	
}
