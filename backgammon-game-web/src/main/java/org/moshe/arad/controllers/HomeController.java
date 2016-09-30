package org.moshe.arad.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	private final Logger logger = LogManager.getLogger(HomeController.class);
	
	@Autowired
	UserSecurityService userSecurityService;
	
	@RequestMapping(value={"/", "/home", "/login", "/register"}, method=RequestMethod.GET)
	public String goHome(){
		logger.info("Routing for home page.");
		return "home";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String doRegister(@ModelAttribute GameUser gameUser){
		logger.info("The GameUser bind result: " + gameUser);
		boolean isSuccessfulRegister = userSecurityService.registerNewUser(gameUser, "ROLE_USER");
		if(isSuccessfulRegister){
			logger.info("User was successfuly register.");
			logger.info("Routing for board page.");
			return "backgammon";
		}
		else{
			logger.info("User register failed.");
			logger.info("Routing for home page.");
			return "home";
		}
	}
	
	@RequestMapping(value="/user_name", method = RequestMethod.GET)
	@ResponseBody
	public String userNameAvailable(@RequestParam String userName){
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
	
	@RequestMapping(value="/email", method = RequestMethod.GET)
	@ResponseBody
	public String emailAvailable(@RequestParam String email){
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
}
