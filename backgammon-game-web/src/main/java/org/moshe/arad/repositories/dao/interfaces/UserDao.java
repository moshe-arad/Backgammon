package org.moshe.arad.repositories.dao.interfaces;

import java.util.List;

import org.moshe.arad.repositories.entities.GameUser;


public interface UserDao extends Dao<GameUser,Long> {

	public List<GameUser> findByFirstName(String firstName);
	
	public GameUser findByUserName(String userName);
}
