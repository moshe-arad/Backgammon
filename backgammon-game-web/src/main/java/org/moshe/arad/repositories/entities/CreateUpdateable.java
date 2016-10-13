package org.moshe.arad.repositories.entities;

import java.util.Date;

public interface CreateUpdateable {

	public Date getLastUpdatedDate();

	public void setLastUpdatedDate(Date lastUpdatedDate);

	public Long getLastUpdatedBy();

	public void setLastUpdatedBy(Long lastUpdatedBy);

	public Date getCreatedDate();

	public void setCreatedDate(Date createdDate);

	public Long getCreatedBy();

	public void setCreatedBy(Long createdBy);
}
