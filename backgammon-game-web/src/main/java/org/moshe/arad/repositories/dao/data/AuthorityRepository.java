package org.moshe.arad.repositories.dao.data;

import org.moshe.arad.repositories.entities.Authority;
import org.moshe.arad.repositories.entities.AuthorityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityPK> {

}
