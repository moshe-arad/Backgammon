package org.moshe.arad.backgammon_dispatcher.entities;

import java.io.IOException;
import java.util.Iterator;

import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.repositories.entities.BasicUser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class UserMoveJsonDeserializer extends JsonDeserializer<UserMove> {

	@Override
	public UserMove deserialize(JsonParser jsonParser, 
			DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		Move move = new Move();
		BasicUser basicUser = new BasicUser();
		UserMove userMove = new UserMove();
		
		JsonNode node = jsonParser.readValueAsTree(); 
		int fromIndex = node.get("move").get("move").get("from").get("index").asInt();
		int toIndex = node.get("move").get("move").get("to").get("index").asInt();
		BackgammonBoardLocation from = new BackgammonBoardLocation(fromIndex);
		BackgammonBoardLocation to = new BackgammonBoardLocation(toIndex);
		move.setFrom(from);
		move.setTo(to);
		userMove.setMove(move);
		
		basicUser.setUserName(node.get("user").get("userName").asText());
		basicUser.setPassword(node.get("user").get("password").asText());
		basicUser.setEnabled(node.get("user").get("enabled").asBoolean());
		userMove.setUser(basicUser);
		return userMove;
	}

}
