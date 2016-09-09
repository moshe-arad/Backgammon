package org.moshe.arad;

import org.moshe.arad.backgammon.Board;
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
    	ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
    	Board board = context.getBean(Board.class);
        
    	board.initBoard();
    	
    	board.print();
    	
        ((ClassPathXmlApplicationContext)context).close();
    }
}
