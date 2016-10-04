package org.moshe.arad.repositories;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Resource;

import org.moshe.arad.repositories.dao.hibernate.interfaces.HibernateGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityRepository {

	@Resource
	HibernateGameUserDao hibernateCriteriaDao;
	
	public GameUser loadUserByUsername(String userName){
		return hibernateCriteriaDao.findByUserName(userName);
	}

	public boolean registerNewUser(GameUser gameUser) {
		return hibernateCriteriaDao.save(gameUser);
	}

	public Set<String> getAllUserNames() {
		Collection<String> userNamesCollection = hibernateCriteriaDao.getAllUserNames();
		return new ConcurrentSkipListSet<String>(userNamesCollection);
	}

	public Set<String> getAllEmails() {
		Collection<String> emailsCollection = hibernateCriteriaDao.getAllEmails();
		return new ConcurrentSkipListSet<String>(emailsCollection);
	}
}
