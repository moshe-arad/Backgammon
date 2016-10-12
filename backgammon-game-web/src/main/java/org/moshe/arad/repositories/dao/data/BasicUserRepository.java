package org.moshe.arad.repositories.dao.data;

import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicUserRepository extends JpaRepository<BasicUser, String> {

	public BasicUser findByUserName(String UserName);
}
