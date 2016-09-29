package org.moshe.arad.controllers;

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

	@Autowired
	UserSecurityService userSecurityService;
	
	@RequestMapping(value={"/", "/home", "/login", "/register"}, method=RequestMethod.GET)
	public String goHome(){
		return "home";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String doRegister(@ModelAttribute GameUser gameUser){
		boolean isSuccessfulRegister = userSecurityService.registerNewUser(gameUser, "ROLE_USER");
		if(isSuccessfulRegister) return "backgammon";
		else return "home";
	}
	
	@RequestMapping(value="/user_name", method = RequestMethod.POST)
	@ResponseBody
	public String userNameAvailable(@RequestParam String userName){
		System.out.println(userName);
		if(userSecurityService.isUserNameAvailable(userName)) return "";
		else return "User name is not availbale.";
	}
}
