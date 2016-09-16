package org.moshe.arad.game.instrument;

@Deprecated
public enum Color {
	white(0),
	black(1);
	
	private int innerValue;
	
	private Color(int innerValue) {
		this.innerValue = innerValue;
	}

	public int getInnerValue() {
		return innerValue;
	}

	public void setInnerValue(int innerValue) {
		this.innerValue = innerValue;
	}
	
	public static Color getColorByInt(int innerValue){
		if(innerValue == 0) return Color.white;
		else if(innerValue == 1) return Color.black;
		else return null;
	}
	
	public static Color getOpposite(Color color){
		return color.equals(black) ? Color.white : Color.black;
	}
}
