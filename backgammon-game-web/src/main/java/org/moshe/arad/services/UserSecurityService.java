package org.moshe.arad.services;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.moshe.arad.repositories.UserSecurityRepository;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userSecurityService")
public class UserSecurityService implements UserDetailsService{

	@Autowired
	private UserSecurityRepository userSecurityRepo;
	private Set<String> userNames = null;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return userSecurityRepo.loadUserByUsername(userName);
	}

	public boolean registerNewUser(GameUser gameUser, String role) {
		gameUser.setRole(role);
		boolean isSuccessfulSaved = userSecurityRepo.registerNewUser(gameUser);
		if(!isSuccessfulSaved) return false;
		else{
			userNames.add(gameUser.getUserName());
			Authentication auth = new UsernamePasswordAuthenticationToken(gameUser, 
					gameUser.getPassword(), gameUser.getAuthorities()); 
			SecurityContextHolder.getContext().setAuthentication(auth);
			return true;
		}
	}
	
	public boolean isUserNameAvailable(String userName){
		if(userNames == null) userNames = userSecurityRepo.getAllUserNames();
		if(userNames.contains(userName)) return false;
		else return true;
	}
}
