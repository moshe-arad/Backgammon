package org.moshe.arad.repositories.dao.hibernate.interfaces;

import org.moshe.arad.repositories.dao.general.GameUserDao;
import org.moshe.arad.repositories.entities.GameUser;


public interface HibernateGameUserDao extends GameUserDao, HibernateDao<GameUser,Long> {
	
}
