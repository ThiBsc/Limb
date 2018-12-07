package engine;

/**
 * public void doLeftClick();
 * public void doDoubleLeftCLick();
 * public void doRightClick();
 * public void selectAll();
 * public void copy();
 * public void paste();
 * public void copyPaste();
 * public void type(String text);
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

