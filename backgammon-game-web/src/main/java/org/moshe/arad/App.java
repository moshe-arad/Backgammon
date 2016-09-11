package org.moshe.arad;


import org.moshe.arad.game.classic_board.backgammon.Backgammon;
import org.moshe.arad.game.instrument.Dice;
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
