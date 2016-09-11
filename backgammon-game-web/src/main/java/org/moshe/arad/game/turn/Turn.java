package org.moshe.arad.game.turn;

public class Turn {

	private static Turn instance;
	
	private Turn(){
		
	}
	
	public static Turn getInstance(){
		
		if(instance == null){
			synchronized (Turn.class) {
				if(instance == null){
					instance = new Turn();
				}
			}
		}
		return instance;
	}
}
