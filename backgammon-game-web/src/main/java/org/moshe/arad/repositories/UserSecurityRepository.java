package org.moshe.arad.repositories;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Resource;

import org.moshe.arad.repositories.dao.data.GameUserDataDao;
import org.moshe.arad.repositories.dao.hibernate.interfaces.HibernateGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityRepository {

//	@Resource
//	HibernateGameUserDao hibernateCriteriaDao;
	
	@Autowired
	GameUserDataDao gameUserDataDao;
	
	public GameUser loadUserByUsername(String userName){
		return gameUserDataDao.findByUserName(userName);
	}

	public boolean registerNewUser(GameUser gameUser) {
		gameUserDataDao.save(gameUser);
		return true;
	}

	public Set<String> getAllUserNames() {
		Collection<String> userNamesCollection = gameUserDataDao.getAllUserNames();
		return new ConcurrentSkipListSet<String>(userNamesCollection);
	}

	public Set<String> getAllEmails() {
		Collection<String> emailsCollection = gameUserDataDao.getAllEmails();
		return new ConcurrentSkipListSet<String>(emailsCollection);
	}
}
