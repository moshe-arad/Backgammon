package org.moshe.arad.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.components.GameRooms;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.dao.data.GameRoomRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackgammonService {

	private final Logger logger = LogManager.getLogger(BackgammonService.class);
	@Autowired
	private GameRooms gameRooms;
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private GameRoomRepository gameRoomRepository;
	
	public boolean isBothPlayersOnRoom(GameRoom gameRoom){
		Long white = gameRoomRepository.selectWhiteFromGameRoom(gameRoom.getGameRoomId());
		Long black = gameRoomRepository.selectBlackFromGameRoom(gameRoom.getGameRoomId());
		if(white != null) logger.info("White player is on room and ready to play.");
		if(black != null) logger.info("Black player is on room and ready to play.");
		return (white != null && black != null);
	}
	
	private GameUser getLoggedInGameUser(){
		return securityRepository.getLoggedInGameUser();
	}
	
	
}
