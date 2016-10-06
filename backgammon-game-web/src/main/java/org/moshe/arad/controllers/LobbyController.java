package org.moshe.arad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LobbyController {

	@RequestMapping(value="/lobby")
	public String goLobby(){
		return "lobby";
	}
}
