package org.moshe.arad;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	final Logger logger  = LogManager.getLogger("org.moshe.arad");
    	
    	logger.info("Start initialize the game...");
    	ApplicationContext context = new ClassPathXmlApplicationContext("backgammon-context.xml");
    	Backgammon game = context.getBean(Backgammon.class);
   
    	game.startGame();
    	
        ((ClassPathXmlApplicationContext)context).close();
    }
}
