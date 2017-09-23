package me.mudkiper202.MasterscapeFrame.utils.area;

public enum AreaRule {
	
	PVP(false), BLOCKBREAK(false);
	
	private boolean state;
	
	AreaRule(boolean state) {
		this.state = state;
	}

	public boolean getState() {
		return state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
}
