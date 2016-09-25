package org.moshe.arad.repositories;

import org.moshe.arad.repositories.dao.interfaces.UserDao;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityRepository {

	@Autowired
	UserDao userDao;
	
	public GameUser loadUserByUsername(String userName){
		return userDao.findByUserName(userName);
	}
}
