package org.moshe.arad.repositories.dao.data;

import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {

	@Query("select r.group from GameRoom r where r.gameRoomId = :gameRoomId")
	public Group findByGameRoomId(@Param("gameRoomId") Long gameRoomId);
}
