package org.moshe.arad.repositories;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

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

	public boolean registerNewUser(GameUser gameUser) {
		return userDao.save(gameUser);
	}

	public Set<String> getAllUserNames() {
		Collection<String> userNamesCollection = userDao.getAllUserNames();
		return new ConcurrentSkipListSet<String>(userNamesCollection);
	}
}
