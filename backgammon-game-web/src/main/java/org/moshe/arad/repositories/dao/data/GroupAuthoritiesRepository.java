package org.moshe.arad.repositories.dao.data;

import java.util.List;

import org.moshe.arad.repositories.entities.Group;
import org.moshe.arad.repositories.entities.GroupAuthorities;
import org.moshe.arad.repositories.entities.GroupAuthoritiesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAuthoritiesRepository extends JpaRepository<GroupAuthorities, GroupAuthoritiesPK> {

	public List<GroupAuthorities> findByGroup(Group group);
}
