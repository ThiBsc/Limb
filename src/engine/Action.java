package engine;

import java.awt.Point;

public class Action {
	
	private String action;
	private Point pos;

	public Action(ActionEnum action, Point pos) {
		this.action = action.toString();
		this.pos = pos;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	

}
