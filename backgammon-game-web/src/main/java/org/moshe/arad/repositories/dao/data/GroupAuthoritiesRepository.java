package org.moshe.arad.repositories.dao.data;

import org.moshe.arad.repositories.entities.GroupAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAuthoritiesRepository extends JpaRepository<GroupAuthorities, Long> {

}
