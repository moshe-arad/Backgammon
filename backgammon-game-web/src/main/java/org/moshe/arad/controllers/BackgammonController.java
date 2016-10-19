package org.moshe.arad.controllers;

import javax.annotation.PostConstruct;

import org.moshe.arad.backgammon_dispatcher.UserMoveQueuesManager;
import org.moshe.arad.backgammon_dispatcher.entities.UserMove;
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

@Controller
@RequestMapping(value = "/backgammon")
public class BackgammonController {

	@Autowired
	private UserMoveQueuesManager userMoveQueues;
	@Autowired
	private BackgammonService backgammonService;
	
	@RequestMapping(value = "/", params = {"gameRoomId"})
	public String goGame(Model model, @RequestParam("gameRoomId") Long gameRoomId){
		model.addAttribute("gameRoomId", gameRoomId);
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
	
	@RequestMapping(value = "/init/{gameRoomId}")
	@ResponseBody
	public Boolean isReadyToPlay(@PathVariable("gameRoomId") GameRoom gameRoom){
		Boolean isReady = backgammonService.isBothPlayersOnRoom(gameRoom); 		
		return isReady;
	}
}














