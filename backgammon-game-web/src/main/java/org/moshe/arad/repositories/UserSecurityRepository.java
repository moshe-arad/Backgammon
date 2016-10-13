package org.moshe.arad.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import org.moshe.arad.repositories.dao.data.AuthorityRepository;
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.dao.data.RepositoryUtils;
import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityRepository {

	@Autowired
	GameUserRepository gameUserRepository;
	@Autowired
	AuthorityRepository authorityRepository;
	@Autowired
	BasicUserRepository basicUserRepository;

	public GameUser loadUserByUsername(String userName){
		BasicUser basicUser = basicUserRepository.findByUserName(userName);
		if(basicUser != null) return basicUser.getGameUser();
		else return null;
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
	
	public boolean isHasLoggedInUser() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!userName.equals("anonymous")){
			return basicUserRepository.findByUserName(userName) != null ? true : false;
		}
		else return false;
	}
}
