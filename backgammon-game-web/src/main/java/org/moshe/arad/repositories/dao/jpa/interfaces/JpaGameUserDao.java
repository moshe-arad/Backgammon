package org.moshe.arad.repositories.dao.jpa.interfaces;

import org.moshe.arad.repositories.dao.general.GameUserDao;
import org.moshe.arad.repositories.entities.GameUser;

public interface JpaGameUserDao extends GameUserDao, JpaDao<GameUser, Long>{
	
}
