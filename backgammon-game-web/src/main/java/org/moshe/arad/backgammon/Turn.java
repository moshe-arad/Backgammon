package org.moshe.arad.backgammon;

public class Turn {

	private static Turn instance;
	
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
