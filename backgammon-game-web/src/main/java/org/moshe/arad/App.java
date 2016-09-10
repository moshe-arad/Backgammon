package org.moshe.arad;

import org.moshe.arad.backgammon.Board;
import org.moshe.arad.backgammon.Dice;
import org.moshe.arad.backgammon.Game;
import org.moshe.arad.backgammon.Player;
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
    	Game game = context.getBean(Game.class);
        
    	System.out.println("First player - " + game.getFirstPlayer());
    	System.out.println("Second player - " + game.getSecondPlayer());
    	
    	game.getBoard().initBoard();
    	
    	game.getBoard().print();
    	
    	Dice d1 = game.getFirstDice();
    	Dice d2 = game.getSecondDice();
    	
    	d1.rollDice();
    	d2.rollDice();
    	System.out.println("First dice roll - " + d1.getValue());
    	System.out.println("Second dice roll - " + d2.getValue());
    
        ((ClassPathXmlApplicationContext)context).close();
    }
}
