package org.moshe.arad.repositories.dao.jpa;


import org.moshe.arad.repositories.dao.GameUserDao;
import org.moshe.arad.repositories.entities.GameUser;

public interface JpaGameUserDao extends GameUserDao, JpaDao<GameUser, Long>{
	
}
