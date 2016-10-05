package org.moshe.arad.repositories;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.annotation.Resource;
import org.moshe.arad.repositories.dao.hibernate.HibernateGameUserDao;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityRepository {

	@Resource
	HibernateGameUserDao hibernateGameUserCriteriaDao;
	
//	@Autowired
//	GameUserDataDao gameUserDataDao;
	
	public GameUser loadUserByUsername(String userName){
		return hibernateGameUserCriteriaDao.findByUserName(userName);
	}

	public boolean registerNewUser(GameUser gameUser) {
		return hibernateGameUserCriteriaDao.save(gameUser);
	}

	public Set<String> getAllUserNames() {
		Collection<String> userNamesCollection = hibernateGameUserCriteriaDao.getAllUserNames();
		return new ConcurrentSkipListSet<String>(userNamesCollection);
	}

	public Set<String> getAllEmails() {
		Collection<String> emailsCollection = hibernateGameUserCriteriaDao.getAllEmails();
		return new ConcurrentSkipListSet<String>(emailsCollection);
	}
}
