package org.moshe.arad.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.backgammon_dispatcher.confirm.ConfirmArriveQueueManager;
import org.moshe.arad.backgammon_dispatcher.request.BackgammonUserQueue;
import org.moshe.arad.backgammon_dispatcher.request.BackgammonUsersQueuesManager;
import org.moshe.arad.components.GameRooms;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.entities.GameRoom;
import org.moshe.arad.repositories.entities.GameUser;
import org.moshe.arad.services.BackgammonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BackgammonInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LogManager.getLogger(BackgammonInterceptor.class);
	@Autowired
	private BackgammonService backgammonService;
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private BackgammonUsersQueuesManager userMoveQueues;
	@Autowired
	private GameRooms gameRooms;
	@Autowired
	private ConfirmArriveQueueManager confirmArriveQueueManager;
	
	private ApplicationContext gameContext = new ClassPathXmlApplicationContext("backgammon-web-context.xml");
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
		if(request.getRequestURI().endsWith("/backgammon/") && modelAndView.getModel().containsKey("gameRoomId")){
			
			Long gameRoomId = (Long)modelAndView.getModel().get("gameRoomId");
			logger.info("Creating new game with id " + gameRoomId);
			GameRoom gameRoom = gameRooms.getGameRoomById(gameRoomId);
			
			String color = modelAndView.getModel().get("player").toString();
			
			if(color.equals("white")){
				gameRoom.setGame(gameContext.getBean(Backgammon.class));
				gameRoom.getGame().setConfirmArriveQueueManager(confirmArriveQueueManager);
				GameUser white = securityRepository.getGameUserByGameUserId(gameRoom.getWhite());
				BackgammonUserQueue whiteQueue = userMoveQueues.createNewQueueForUser(white.getBasicUser());
				gameRoom.getGame().setWhiteQueue(whiteQueue);
				gameRooms.addGameRoom(gameRoom);
				
			}
			if(gameRoom.getGame() != null && color.equals("black")){
				GameUser black = securityRepository.getGameUserByGameUserId(gameRoom.getBlack());
				BackgammonUserQueue blackQueue = userMoveQueues.createNewQueueForUser(black.getBasicUser());
				gameRoom.getGame().setBlackQueue(blackQueue);
				gameRooms.addGameRoom(gameRoom);
				logger.info("New game created.");
				backgammonService.initAndStartGame(gameRoomId);
			}		
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
