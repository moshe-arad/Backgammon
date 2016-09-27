package org.moshe.arad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BackgammonController {

	@RequestMapping(value = {"/backgammon", "/backgammon/"})
	public String goGame(){
		return "backgammon";
	}
}
