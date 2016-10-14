package org.moshe.arad.repositories.dao.data;

import java.util.Set;

import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameUserRepository extends JpaRepository<GameUser, Long> {

	@Query("select gu.gameRooms from GameUser gu join BasicUser b"
			+ " on gu.basicUser.userName = b.userName"
			+ " where b.userName = :userName")
	public Set<GameRoom> findGameRoomsByLoggedUser(@Param("userName")String userName);	
}
