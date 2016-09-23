package org.moshe.arad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping(value={"/", "/home"}, method=RequestMethod.GET)
	public String goHome(){
		return "home";
	}
	
//	@RequestMapping(value={"/", "/home"}, method=RequestMethod.POST)
//	public String goHome(){
//		return "home";
//	}
	
	@RequestMapping(value = {"/game", "/game/"})
	public String goGame(){
		return "backgammon";
	}
}
