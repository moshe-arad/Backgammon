package org.moshe.arad.backgammon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.moshe.arad.game.instrument.Color;

public class ColorTest {

	@Test
	public void getColorByIntGetWhiteTest(){
		Color actual = Color.getColorByInt(0);
		assertEquals("get Color By Int Get White Test failed.", Color.white, actual);
	}
	
	@Test
	public void getColorByIntGetBlackTest(){
		Color actual = Color.getColorByInt(1);
		assertEquals("get Color By Int Get black Test failed.", Color.black, actual);
	}
	
	@Test
	public void getOppositeBlackTest(){
		Color actual = Color.getOpposite(Color.black);
		assertEquals("get opposite Color black Test failed.", Color.white, actual);
	}
	
	@Test
	public void getOppositeWhiteTest(){
		Color actual = Color.getOpposite(Color.white);
		assertEquals("get opposite Color black Test failed.", Color.black, actual);
	}
}
