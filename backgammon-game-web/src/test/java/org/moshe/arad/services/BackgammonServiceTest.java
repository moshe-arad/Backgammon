package org.moshe.arad.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BackgammonServiceTest {

	@Autowired
	private BackgammonService backgammonService;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	
	@Test
	public void getGameRoomByJsonId(){
		GameRoom gameRoom = new GameRoom("game room name", 
				false, 1L, 1L, 2L, 2);
		gameRoomRepository.save(gameRoom);
//		game
		String gameRoomIdJsonStr = "{gameRoomId:1}";
	}
}
