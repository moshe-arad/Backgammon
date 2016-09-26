package org.moshe.arad.services;

import org.moshe.arad.repositories.UserSecurityRepository;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userSecurityService")
public class UserSecurityService implements UserDetailsService{

	@Autowired
	private UserSecurityRepository userSecurityRepo;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return userSecurityRepo.loadUserByUsername(userName);
	}
}
