package org.moshe.arad.services;

import java.util.Set;

import javax.annotation.PostConstruct;

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
//	private UserSecurityRepositoryHibernate userSecurityRepo;
	private Set<String> userNames = null;
	private Set<String> emails = null;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return userSecurityRepo.loadUserByUsername(userName);
	}

	public void registerNewUser(GameUser gameUser, String role) {
		gameUser.setRole(role);
		userSecurityRepo.registerNewUser(gameUser);
		
		initVirtualRepo();
		
		userNames.add(gameUser.getUserName());
		emails.add(gameUser.getEmail());
		
		Authentication auth = new UsernamePasswordAuthenticationToken(gameUser, 
				gameUser.getPassword(), gameUser.getAuthorities()); 
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	public boolean isUserNameAvailable(String userName){
		initVirtualRepo();
		if(userNames.contains(userName)) return false;
		else return true;
	}
	
	public boolean isEmailAvailable(String email){
		initVirtualRepo();
		if(emails.contains(email)) return false;
		else return true;
	}
	
	private void initVirtualRepo() {
		if(userNames == null) userNames = userSecurityRepo.getAllUserNames();
		if(emails == null) emails = userSecurityRepo.getAllEmails();
	}
}



















