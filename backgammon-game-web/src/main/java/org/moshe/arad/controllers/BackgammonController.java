package org.moshe.arad.controllers;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.UserMoveQueuesManager;
import org.moshe.arad.backgammon_dispatcher.entities.PairBackgammonDices;
import org.moshe.arad.backgammon_dispatcher.entities.UserMove;
import org.moshe.arad.game.move.BackgammonBoardLocation;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.services.BackgammonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/backgammon")
public class BackgammonController {

	private final Logger logger = LogManager.getLogger(BackgammonController.class);
	@Autowired
	private BackgammonService backgammonService;
	
	@RequestMapping(value = "/", params = {"gameRoomId", "player"})
	public String goGame(Model model, @RequestParam("gameRoomId") Long gameRoomId,
			@RequestParam("player") String player){
		model.addAttribute("gameRoomId", gameRoomId);
		model.addAttribute("player", player);
		return "backgammon";
	}
	
//	@RequestMapping(value = "/identifyMe")
//	@ResponseBody
//	public Map<String, Object> identifyUser(@ModelAttribute BasicUser basicUser){
//		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String, Object> jsonNodes = new HashMap<>();
//		
//		jsonNodes.put("basicUser", basicUser);
//		jsonNodes.put("loggedUserName", userName);
//		
//		return jsonNodes;
//	}
//	
//	@RequestMapping(value = "/sendUserMove", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
//	@ResponseBody
//	public UserMove putMoveForUser(@RequestBody UserMove userMove){
//		userMoveQueues.putMoveIntoQueue(userMove.getUser(), userMove);		
//		return userMove;
//	}
	
	@RequestMapping(value = "/init", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
	@ResponseBody
	public Boolean isReadyToPlay(@RequestBody String gameRoomId){
		GameRoom gameRoom = backgammonService.getGameRoomByJsonId(gameRoomId);
		return backgammonService.initAndStartGame(gameRoom);
//		Long gameRoomIdFromClient = getGameRoomIdFromJson(gameRoomId);
//		
//		GameRoom gameRoom = backgammonService.getGameRoomById(gameRoomIdFromClient);
//		
//		if(!gameRoom.getIsGameRoomReady()) 
//			gameRoom.setIsGameRoomReady(backgammonService.isBothPlayersOnRoom(gameRoom));
//			
//		if(gameRoom.getIsGameRoomReady() && gameRoom.getGame() == null) {
//			synchronized (gameRoom) {
//				if(gameRoom.getGame() == null){
//					logger.info("Starting game.");
//					backgammonService.initGame(gameRoom);
//					backgammonService.notifyWhiteSendMove(gameRoom);
//					backgammonService.startGame(gameRoom);
//				}				
//			}			
//		}
//		return gameRoom.getIsGameRoomReady();
	}
	
	@RequestMapping(value = "/roll_dices", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
	@ResponseBody
	public PairBackgammonDices rollDices(@RequestBody String gameRoomId){
		GameRoom gameRoom = backgammonService.getGameRoomByJsonId(gameRoomId);
		PairBackgammonDices dices = backgammonService.rollDices(gameRoom);
		return dices;
	}	
}














