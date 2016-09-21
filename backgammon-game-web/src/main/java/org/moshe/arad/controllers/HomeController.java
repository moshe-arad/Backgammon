package org.moshe.arad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String goHome(){
		return "home";
	}
	
	@RequestMapping(value = "/game")
	public String goGame(){
		return "backgammon";
	}
}
