package org.moshe.arad.controllers;

import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

import org.moshe.arad.backgammon_dispatcher.UserMove;
import org.moshe.arad.backgammon_dispatcher.UserMoveQueuesManager;
import org.moshe.arad.game.move.Move;
import org.moshe.arad.repositories.entities.BasicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class BackgammonController {

	@Autowired
	private UserMoveQueuesManager userMoveQueues;
	
	@RequestMapping(value = {"/backgammon", "/backgammon/"})
	public String goGame(){
		return "backgammon";
	}
	
	@RequestMapping(value = "/backgammon/identifyMe")
	@ResponseBody
	public Map<String, Object> identifyUser(@ModelAttribute BasicUser basicUser){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> jsonNodes = new HashMap<>();
		
		jsonNodes.put("basicUser", basicUser);
		jsonNodes.put("loggedUserName", userName);
		
		return jsonNodes;
	}
	
	@RequestMapping(value = "/backgammon/sendUserMove", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
	@ResponseBody
	public UserMove putMoveForUser(@RequestBody UserMove userMove){
		userMoveQueues.putMoveIntoQueue(userMove.getUser(), userMove);		
		return userMove;
	}
}














