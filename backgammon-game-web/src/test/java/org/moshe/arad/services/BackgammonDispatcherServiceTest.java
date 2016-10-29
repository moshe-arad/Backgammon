package org.moshe.arad.services;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moshe.arad.backgammon_dispatcher.entities.BasicDetails;
import org.moshe.arad.backgammon_dispatcher.entities.EmptyMessage;
import org.moshe.arad.backgammon_dispatcher.request.BackgammonUserQueue;
import org.moshe.arad.backgammon_dispatcher.request.BackgammonUsersQueuesManager;
import org.moshe.arad.repositories.SecurityRepository;
import org.moshe.arad.repositories.dao.data.BasicUserRepository;
import org.moshe.arad.repositories.dao.data.GameUserRepository;
import org.moshe.arad.repositories.entities.BasicUser;
import org.moshe.arad.repositories.entities.GameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:persistence-context-test.xml"),
	@ContextConfiguration("classpath:backgammon-context-test.xml"),
	@ContextConfiguration("classpath:security-context-test.xml")
})
@WithAnonymousUser
public class BackgammonDispatcherServiceTest {

	@Autowired
	private BackgammonDispatcherService backgammonDispatcherService;
	@Autowired
	private BackgammonUsersQueuesManager backgammonUsersQueuesManager;
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private BasicUserRepository basicUserRepository;
	@Autowired
	private GameUserRepository gameUserRepository;
	
	@Resource
	GameUser gameUser1;
	
	@Before
	public void setup(){
		clearDB();
	}
	
	@After
	public void cleanup(){
		clearDB();
	}
	
	private void clearDB() {
		gameUserRepository.deleteAllInBatch();
		basicUserRepository.deleteAllInBatch();
	}
	
	@Test
	@WithMockUser
	public void respondToUser(){
		BasicUser basicUser = new BasicUser("user", "password", true);
		basicUser.setGameUser(gameUser1);
		
		securityRepository.saveNewUser(gameUser1, basicUser);
		
		BackgammonUserQueue backgammonUserQueue = backgammonUsersQueuesManager.createNewQueueForUser(basicUser);
		backgammonUserQueue.putMoveIntoQueue(new BasicDetails(2, "white", true));
		
		BasicDetails actual = (BasicDetails) backgammonDispatcherService.respondToUser();				
		assertEquals(new BasicDetails(2, "white", true), actual);
	}
}
