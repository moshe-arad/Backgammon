package org.moshe.arad.repositories.dao.data;

import java.util.Date;

import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameUser;

public interface RepositoryUtils {

	public static void setCreateAndUpdateSys(Authority auth){
		Date now = new Date();
		
		if(auth.getCreatedDate() == null) auth.setCreatedDate(now);
		if(auth.getCreatedBy() == null) auth.setCreatedBy(-1L);
		
		auth.setLastUpdatedDate(now);
		auth.setLastUpdatedBy(-1L);
	}
	
	public static void setCreateAndUpdateSys(BasicUser basicUser){
		Date now = new Date();
		
		if(basicUser.getCreatedDate() == null) basicUser.setCreatedDate(now);
		if(basicUser.getCreatedBy() == null) basicUser.setCreatedBy(-1L);
		
		basicUser.setLastUpdatedDate(now);
		basicUser.setLastUpdatedBy(-1L);
	}
	
	public static void setCreateAndUpdateSys(GameUser gameUser){
		Date now = new Date();
		
		if(gameUser.getCreatedDate() == null) gameUser.setCreatedDate(now);
		if(gameUser.getCreatedBy() == null) gameUser.setCreatedBy(-1L);
		
		gameUser.setLastUpdatedDate(now);
		gameUser.setLastUpdatedBy(-1L);
	}
}
