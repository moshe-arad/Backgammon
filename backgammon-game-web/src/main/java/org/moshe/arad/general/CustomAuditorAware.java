package org.moshe.arad.general;

import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuditorAware implements AuditorAware<String> {

	@Autowired
	BasicUserRepository basicUserRepository;
	
	@Override
	public String getCurrentAuditor() {
		String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();
		BasicUser loggedBasicUser = basicUserRepository.findOne(loggedName);
		if(loggedBasicUser == null) return "System";
		else return loggedBasicUser.getUserName();
	}

}
