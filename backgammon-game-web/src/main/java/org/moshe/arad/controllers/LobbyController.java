package org.moshe.arad.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.validators.GameRoomValidator;
import org.moshe.arad.services.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/lobby")
public class LobbyController {

	private final Logger logger = LogManager.getLogger(LobbyController.class);
	
	@Autowired
	LobbyService lobbyService;
	
	@RequestMapping(value = "/")
	public String goLobby(){
		logger.info("Routing to lobby page.");
		return "lobby";
	}
	
	@ModelAttribute("gameRooms")
	public List<GameRoom> getGameRooms(){
		return lobbyService.getAllGameRooms();
	}
	
	@ModelAttribute("speedOptions")
	public List<String> getSpeedOptions(){
		List<String> options = new ArrayList<>();
		options.add("High - 30 sec");
		options.add("Medium - 45 sec");
		options.add("Low - 60 sec");
		return options;
	}
	
	@ModelAttribute("privateRoomOptions")
	public List<String> getPrivateRoomOptions(){
		List<String> options = new ArrayList<>();
		options.add("No");
		options.add("Yes");
		return options;
	}
	
	@ModelAttribute("newGameRoom")
	public GameRoom getNewGameRoom(){
		return new GameRoom();
	}
	
	@RequestMapping(value="/open")
	public String openNewGameRoom(@ModelAttribute @Valid GameRoom gameRoom, 
			Errors errors, RedirectAttributes attributes){
		logger.info("Try to open a new game room.");
		logger.info("The game room bind result is: " + gameRoom);
		
		if(errors.hasErrors()){
			logger.error("Binding game room has errors.");
			
			if(!GameRoomValidator.acceptableErrors(errors)){
				logger.info("Setting default value");
				lobbyService.setDefaultValues(gameRoom);
			}
		}
		
		gameRoom = lobbyService.addNewGameRoom(gameRoom);
		attributes.addAttribute("gameRoomId", gameRoom.getGameRoomId());
		attributes.addAttribute("player", "white");
		return "redirect:/backgammon/";
	}
	
	@RequestMapping(value = "/join/{gameRoomId}")
	public String joinGameRoom(@PathVariable("gameRoomId") GameRoom gameRoom, 
			RedirectAttributes attributes){
		try{
			logger.info("adding current logged user to game room with details of: " +gameRoom);
			gameRoom = lobbyService.joinGameRoom(gameRoom);
			logger.info("routing for backgammon board page");
			attributes.addAttribute("gameRoomId", gameRoom.getGameRoomId());
			attributes.addAttribute("player", "black");
			return "redirect:/backgammon/";
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.error(ex);
			logger.error("Error occured while trying to add current logged user to game room with deatils of: " + gameRoom);
			return "lobby";
		}
	}
	
	@InitBinder("gameRoom")
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new GameRoomValidator());
	}
}
