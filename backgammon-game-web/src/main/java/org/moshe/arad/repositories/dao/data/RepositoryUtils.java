package org.moshe.arad.repositories.dao.data;

import java.util.Date;

import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.CreateUpdateable;
import org.moshe.arad.repositories.entities.GameUser;

public interface RepositoryUtils {

	public static <T extends CreateUpdateable> void setCreateAndUpdateSys(T item){
		Date now = new Date();
		
		if(item.getCreatedDate() == null) item.setCreatedDate(now);
		if(item.getCreatedBy() == null) item.setCreatedBy(-1L);
		
		item.setLastUpdatedDate(now);
		item.setLastUpdatedBy(-1L);
	}
}
