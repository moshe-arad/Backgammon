package org.moshe.arad.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.repositories.validators.GameUserValidator;
import org.moshe.arad.services.LobbyService;
import org.moshe.arad.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	private final Logger logger = LogManager.getLogger(HomeController.class);
	
	@Autowired
	UserSecurityService userSecurityService;
	@Autowired
	LobbyService lobbyService;

	@RequestMapping(value={"/", "/home", "/login", "/register"}, method=RequestMethod.GET)
	public String goHome(){
		if(lobbyService.isHasLoggedInUser()){
			logger.info("Routing for lobby page.");
			return "redirect:/lobby/";
		}
		else{
			logger.info("Routing for home page.");
			return "home";
		}
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute GameUser gameUser, 
			@Valid @ModelAttribute BasicUser basicUser, Errors errors, Model model){
		if(errors.hasErrors()){
			logger.info("Some errors occured while trying to bind game user");
			logger.info("There are " + errors.getErrorCount() + " errors.");
			
			for(FieldError error:errors.getFieldErrors()){
				logger.warn("Field name:  " + error.getField());
				logger.warn(error.getDefaultMessage());
			}
			
			if(!GameUserValidator.acceptableErrors(errors)){
				logger.info("Routing for home page.");
				return "home";
			}
		}
		
		logger.info("The GameUser bind result: " + gameUser);
		
		try{
			userSecurityService.registerNewUser(gameUser, basicUser);
			logger.info("User was successfuly register.");
			logger.info("Routing for Lobby page.");
			model.addAttribute("gameRooms", lobbyService.getAllGameRooms());
			model.addAttribute("newGameRoom", new GameRoom());
			model.addAttribute("speedOptions", getSpeedOptions());
			model.addAttribute("privateRoomOptions", getPrivateRoomOptions());
			return "lobby";
		}
		catch(Exception ex){
			logger.info("User register failed.");
			logger.info("Routing for home page.");
			logger.error(ex.getMessage());
			logger.error(ex);
			return "home";
		}
	}

	@RequestMapping(value="/user_name", method = RequestMethod.GET)
	@ResponseBody
	public String userNameAvailable(@RequestParam String userName){
		try{
			logger.info("User name bind result: " + userName);
			if(userSecurityService.isUserNameAvailable(userName)){
				logger.info("User name available for registeration.");
				return "";
			}
			else {
				logger.info("User name not available can't register.");
				return "User name is not availbale.";
			}
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.error(ex);
			return "Ajax call encountred a server error.";
		}
		
	}
	
	@RequestMapping(value="/email", method = RequestMethod.GET)
	@ResponseBody
	public String emailAvailable(@RequestParam String email){
		try{
			logger.info("Email bind result: " + email);
			if(userSecurityService.isEmailAvailable(email)){
				logger.info("Email available for registeration.");
				return "";
			}
			else{
				logger.info("Email not available can't register.");
				return "Email is not availbale.";
			}
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex);
			return "Ajax call encountred a server error.";
		}
		
	}
	
	@InitBinder("gameUser")
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new GameUserValidator());
	}
	
	public List<String> getSpeedOptions(){
		List<String> options = new ArrayList<>();
		options.add("High - 30 sec");
		options.add("Medium - 45 sec");
		options.add("Low - 60 sec");
		return options;
	}
	
	public List<String> getPrivateRoomOptions(){
		List<String> options = new ArrayList<>();
		options.add("No");
		options.add("Yes");
		return options;
	}
}
