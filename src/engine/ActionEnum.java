package engine;

/**
 * @author thibsc
 * Add a value when you add method in Bot
 */
public enum ActionEnum {
	
	LEFT_CLICK("leftClick"),
	DOUBLE_LEFT_CLICK("doubleLeftClick"),
	RIGHT_CLICK("rightClick"),
	SELECT_ALL("selectAll"),
	DOWN("down"),
	ALT_TAB("altTab"),
	CTRL_END("ctrlEnd"),
	COPY("copy"),
	PASTE("paste"),
	COPY_PASTE("copyPaste"),
	ENTER("enter");
	
	private String action;
	
	private ActionEnum(String a) {
		this.action = a;
	}
	
	public String toString() {
		return action;
	}

}

