package org.moshe.arad.repositories.dao.data;

import org.moshe.arad.repositories.entities.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {

}
