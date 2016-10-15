package org.moshe.arad.repositories.dao.data;

import java.util.List;

import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.Group;
import org.moshe.arad.repositories.entities.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long> {

	public List<BasicUser> findByGroup(Group group);
	
	@Query("select gm from GroupMembers gm where gm.group = :group")
	public List<GroupMembers> findGroupMembersByGroup(@Param("group") Group group);
}
