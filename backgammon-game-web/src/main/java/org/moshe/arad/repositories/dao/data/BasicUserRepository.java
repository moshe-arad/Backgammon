package org.moshe.arad.repositories.dao.data;

import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicUserRepository extends JpaRepository<BasicUser, String> {

}
