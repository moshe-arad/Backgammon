package org.moshe.arad.repositories;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
			setCreateAndUpdateDbInfo(gameUser);
			gameUserRepository.save(gameUser);
		}
		catch (NullPointerException e) {
		}
	}

	private void setCreateAndUpdateDbInfo(GameUser gameUser) {
		gameUser.setCreatedBy(-1L);
		gameUser.setCreatedDate(new Date());
		gameUser.setLastUpdatedBy(-1L);
		gameUser.setLastUpdatedDate(new Date());
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
	
	public boolean isHasLoggedInUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		return gameUserRepository.findByUserName(userName) != null ? true : false;
	}
}
