package org.moshe.arad.repositories.dao.data;

import java.util.Date;
import java.util.List;

import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.AuthorityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, AuthorityPK> {

	public List<Authority> findByUserName(String UserName);
}
