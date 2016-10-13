package org.moshe.arad.repositories.dao.data;

import org.moshe.arad.repositories.entities.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long> {

}
