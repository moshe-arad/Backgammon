package org.moshe.arad.repositories;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityRepository {

	@Autowired
	GameUserRepository gameUserRepository;

	public GameUser loadUserByUsername(String userName){
		return gameUserRepository.findByUserName(userName);
	}

	public void registerNewUser(GameUser gameUser) {
		try{
			if(gameUser == null) throw new NullPointerException();
			gameUserRepository.save(gameUser);
		}
		catch (NullPointerException e) {
		}
	}
	
	public Set<String> getAllUserNames() {
		List<GameUser> users = gameUserRepository.findAll();
		List<String> userNames = users.parallelStream().map(user->user.getUsername()).collect(Collectors.toList());
		return new ConcurrentSkipListSet<String>(userNames);
	}
	
	public Set<String> getAllEmails() {
		List<GameUser> users = gameUserRepository.findAll();
		List<String> emails = users.parallelStream().map(user->user.getEmail()).collect(Collectors.toList());
		return new ConcurrentSkipListSet<String>(emails);
	}	
}
