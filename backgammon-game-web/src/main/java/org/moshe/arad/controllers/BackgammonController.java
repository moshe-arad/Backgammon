package org.moshe.arad.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.services.BackgammonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
//	@RequestMapping(value = "/init", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
//	@ResponseBody
//	public Boolean isReadyToPlay(@RequestBody String gameRoomId){
//		GameRoom gameRoom = backgammonService.getGameRoomByJsonId(gameRoomId);
//		return backgammonService.initAndStartGame(gameRoom);
//	}
	
	@RequestMapping(value = "/roll_dices", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
	@ResponseBody
	public String rollDices(@RequestBody String gameRoomId){
		GameRoom gameRoom = backgammonService.getGameRoomByJsonId(gameRoomId);
//		PairBackgammonDices dices = backgammonService.rollDices(gameRoom);
		backgammonService.rollDices(gameRoom);
		return "";
	}
	
//	@RequestMapping(value = "/move", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
//	@ResponseBody
//	public String rollDices(@RequestBody MoveAndGameRoomId moveAndId){
//		backgammonService.setMoveFromClient(moveAndId.getMove(), moveAndId.getGameRoomId());
//		return "";
//	}
}














