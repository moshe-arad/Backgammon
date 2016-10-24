package org.moshe.arad.controllers;

import org.moshe.arad.backgammon_dispatcher.entities.BasicDetailsAndGameRoomId;
import org.moshe.arad.backgammon_dispatcher.entities.DispatchableEntity;
import org.moshe.arad.services.BackgammonDispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/backgammon_dispatcher")
public class BackgammonDispatcherController {

	@Autowired
	private BackgammonDispatcherService backgammonDispatcherService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
	@ResponseBody
	public DispatchableEntity registerAndDispatch(){		
		return backgammonDispatcherService.respondToUser();	
	}
	
	@RequestMapping(value = "/confirm", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
	@ResponseBody
	public DispatchableEntity confirmDispatchedMessage(@RequestBody BasicDetailsAndGameRoomId basicDetails){		
		return backgammonDispatcherService.confirmMessage(basicDetails);
	}
}
