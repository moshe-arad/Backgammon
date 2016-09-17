package org.moshe.arad;


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
    	ApplicationContext context = new ClassPathXmlApplicationContext("backgammon-context.xml");
    	Backgammon game = context.getBean(Backgammon.class);
    	
    	game.initGame();
    	game.play();
    	
        ((ClassPathXmlApplicationContext)context).close();
    }
}
