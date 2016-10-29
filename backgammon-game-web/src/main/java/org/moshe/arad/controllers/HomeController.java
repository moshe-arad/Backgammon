package org.moshe.arad.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.objects_to_frontend.AuthenticatedUser;
import org.moshe.arad.objects_to_frontend.RegisteredUser;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.repositories.validators.GameUserValidator;
import org.moshe.arad.services.LobbyService;
import org.moshe.arad.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	private final Logger logger = LogManager.getLogger(HomeController.class);
	
	@Autowired
	private HomeService userSecurityService;
	@Autowired
	private LobbyService lobbyService;
	@Autowired
	private SecurityRepository securityRepository;

	@RequestMapping(value={"/", "/home", "/login", "/register"}, method=RequestMethod.GET)
	public String goHome(){
		if(lobbyService.isHasLoggedInUser()){
			logger.info("Routing for lobby page.");
			return "/ng/app/partials/lobby.html";
		}
		else{
			logger.info("Routing for home page.");
			return "/ng/index.html";
		}
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	@ResponseBody
	public RegisteredUser doRegister(@RequestBody GameUser gameUser){
//		if(errors.hasErrors()){
//			logger.info("Some errors occured while trying to bind game user");
//			logger.info("There are " + errors.getErrorCount() + " errors.");
//			
//			for(FieldError error:errors.getFieldErrors()){
//				logger.warn("Field name:  " + error.getField());
//				logger.warn(error.getDefaultMessage());
//			}
//			
//			if(!GameUserValidator.acceptableErrors(errors)){
//				logger.info("Routing for home page.");
////				return "home";
//				return new RegisteredUser(false);
//			}
//		}
		
		logger.info("The GameUser bind result: " + gameUser);
		
		try{
			userSecurityService.registerNewUser(gameUser);
			logger.info("User was successfuly register.");
			logger.info("Routing for Lobby page.");
			
//			return "redirect:/lobby/";
			return new RegisteredUser(true);
		}
		catch(Exception ex){
			logger.info("User register failed.");
			logger.info("Routing for home page.");
			logger.error(ex.getMessage());
			logger.error(ex);
//			return "home";
			return new RegisteredUser(false);
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
				return "User name is not available.";
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
	
	@RequestMapping(value="/authenticate")
	@ResponseBody
	public AuthenticatedUser isAuthenticatedUser(){
		if(lobbyService.isHasLoggedInUser()){
			logger.info("User is Authenticated.");
			return new AuthenticatedUser(true, securityRepository.getLoggedInBasicUser().getUserName());
		}
		else{
			logger.info("User is NOT Authenticated.");
			return new AuthenticatedUser(false, "");
		}
	}
	
	@InitBinder("gameUser")
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new GameUserValidator());
	}
	
	private List<String> getSpeedOptions(){
		List<String> options = new ArrayList<>();
		options.add("High - 30 sec");
		options.add("Medium - 45 sec");
		options.add("Low - 60 sec");
		return options;
	}
	
	private List<String> getPrivateRoomOptions(){
		List<String> options = new ArrayList<>();
		options.add("No");
		options.add("Yes");
		return options;
	}
}
