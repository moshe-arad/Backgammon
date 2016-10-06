package org.moshe.arad.repositories.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.entities.GameRoom;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GameRoomValidator implements Validator{

	private final Logger logger = LogManager.getLogger(GameRoomValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(GameRoom.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		GameRoom gameRoom = (GameRoom)target;
		validateRoomNameByRegEx(gameRoom);
		
		validateRoomName(errors, gameRoom);	
		validatePrivateRoom(errors, gameRoom);
		validateSpeed(errors, gameRoom);
	}

	private void validateSpeed(Errors errors, GameRoom gameRoom) {
		if(gameRoom.getSpeed() == null ||
				(!gameRoom.getSpeed().equals(0) &&
						!gameRoom.getSpeed().equals(1) &&
						!gameRoom.getSpeed().equals(2))){
			logger.error("Speed validation failed.");
			errors.rejectValue("speed", "Invalid speed value.");
		}
	}
	
	private void validatePrivateRoom(Errors errors, GameRoom gameRoom) {
		if(gameRoom.getIsPrivateRoom() == null){
			logger.error("Private room validation failed.");
			errors.rejectValue("isPrivateRoom", "Invalid private room value.");
		}
	}
	
	private void validateRoomName(Errors errors, GameRoom gameRoom) {
		if(gameRoom.getGameRoomName() == null ||
				gameRoom.getGameRoomName().equals("") ||
				!validateRoomNameByRegEx(gameRoom)){
			logger.error("Room name validation failed.");
			errors.rejectValue("gameRoomName", "Invalid game room name.");
		}
	}

	private boolean validateRoomNameByRegEx(GameRoom gameRoom) {
		String gameRoomNameRegEx = "^([a-z|A-Z])[a-z|A-Z| ]+[a-z|A-Z|0-9]+$";
		Pattern gameRoomNamePattern = Pattern.compile(gameRoomNameRegEx);
		Matcher matcher = gameRoomNamePattern.matcher(gameRoom.getGameRoomName());
		return matcher.find();
	}

}
