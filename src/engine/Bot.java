package engine;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;
import java.util.Locale;

/**
 * @author thibdev
 * LIMB Is a Mimetic Bot
 */
public class Bot {
	
	public static int DELAY_ITERATION = 250, DELAY_ACTION = 50, DELAY_KEYBOARD = 50;
	
	//private Robot limb;
	private MouseCorrectRobot limb;
	
	public Bot() {
		try {
			limb = new MouseCorrectRobot();
		} catch (AWTException e) {
			System.err.println("Unable to start LIMB, reason:\n"+e.getMessage());
		}
	}
	
	public void delay(int ms){
		limb.delay(ms);
	}
	
	public void moveCursor(int x, int y) {
		limb.moveMouseControlled(x, y);
	}
	
	public BufferedImage doScreenCapture(Rectangle rect) {
		BufferedImage capture = limb.createScreenCapture(rect);
		return capture;
	}
	
	public void doLeftClick() {
		limb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		limb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void doDoubleLeftCLick() {
		doLeftClick();
		doLeftClick();
	}
	
	public void doRightClick() {
		limb.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		limb.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	public void down() {
		limb.keyPress(KeyEvent.VK_DOWN);
		limb.keyPress(KeyEvent.VK_DOWN);
	}
	
	public void altTab() {
		limb.keyPress(KeyEvent.VK_ALT);
		limb.keyPress(KeyEvent.VK_TAB);
		limb.keyRelease(KeyEvent.VK_TAB);
		limb.keyRelease(KeyEvent.VK_ALT);
	}
	
	public void ctrlEnd() {
		limb.keyPress(KeyEvent.VK_CONTROL);
		limb.keyPress(KeyEvent.VK_END);
		limb.keyRelease(KeyEvent.VK_END);
		limb.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public void selectAll() {
		limb.keyPress(KeyEvent.VK_CONTROL);
		limb.keyPress(KeyEvent.VK_A);
		limb.keyRelease(KeyEvent.VK_A);
		limb.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public void copy() {
		limb.keyPress(KeyEvent.VK_CONTROL);
		limb.keyPress(KeyEvent.VK_C);
		limb.keyRelease(KeyEvent.VK_C);
		limb.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public void paste() {
		limb.keyPress(KeyEvent.VK_CONTROL);
		limb.keyPress(KeyEvent.VK_V);
		limb.keyRelease(KeyEvent.VK_V);
		limb.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public void copyPaste() {
		copy();
		paste();
	}
	
	public void enter() {
		limb.keyPress(KeyEvent.VK_ENTER);
		limb.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void type(String text) {
		for (char c : text.toCharArray()) {
			limb.delay(DELAY_KEYBOARD);
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (keyCode != KeyEvent.CHAR_UNDEFINED) {
				try {
					if (Character.isUpperCase(c)) {
						limb.keyPress(KeyEvent.VK_SHIFT);
					}
					// Manage AZERTY keyboard
					Locale l = InputContext.getInstance().getLocale();
					if (l.equals(Locale.FRANCE)) {
						keyCode = mapKeyboardToAzerty(c, keyCode);
					}
					
					limb.keyPress(keyCode);
					limb.keyRelease(keyCode);
				} catch (Exception e) {
					System.err.println(c+" | "+e.getMessage());
				} finally {
					if (Character.isUpperCase(c)) {
						limb.keyRelease(KeyEvent.VK_SHIFT);
					}
				}
			}
		}
	}
	
	private int mapKeyboardToAzerty(char c, int extendedKeyCode) {
		int mappedKeycode = extendedKeyCode;
		/* https://stackoverflow.com/questions/18599939/java-awt-event-keyevent-not-capable-of-fully-mappin-azerty-keyboard */
		// Manage AZERTY keyboard
		switch (c) {
		case 'é':
			mappedKeycode = 50;
			break;
		case 'è':
			mappedKeycode = 55;
			break;
		case 'à':
			mappedKeycode = 48;
			break;
		case '\'':
			mappedKeycode = 52;
			break;
		case '.':
			mappedKeycode = KeyEvent.VK_DECIMAL;
			break;
		default:
			break;
		}
		return mappedKeycode;
	}
	
	public void execute(Action a){
		//System.out.println(a.getAction());
		Point pos = a.getPos();
		//System.out.println(pos);
		if (a.getAction().equals(ActionEnum.LEFT_CLICK.toString())) {
			moveCursor(pos.x, pos.y);
			doLeftClick();
		} else if (a.getAction().equals(ActionEnum.DOUBLE_LEFT_CLICK.toString())) {
			moveCursor(pos.x, pos.y);
			doDoubleLeftCLick();
		} else if (a.getAction().equals(ActionEnum.RIGHT_CLICK.toString())) {
			moveCursor(pos.x, pos.y);
			doRightClick();
		} else if (a.getAction().equals(ActionEnum.SELECT_ALL.toString())) {
			selectAll();
		}else if (a.getAction().equals(ActionEnum.COPY.toString())) {
			copy();
		} else if (a.getAction().equals(ActionEnum.DOWN.toString())) {
			down();
		} else if (a.getAction().equals(ActionEnum.ALT_TAB.toString())) {
			altTab();
		} else if (a.getAction().equals(ActionEnum.CTRL_END.toString())) {
			ctrlEnd();
		} else if (a.getAction().equals(ActionEnum.PASTE.toString())) {
			paste();
		} else if (a.getAction().equals(ActionEnum.COPY_PASTE.toString())) {
			copyPaste();
		} else if (a.getAction().equals(ActionEnum.ENTER.toString())) {
			enter();
		} else {
			moveCursor(pos.x, pos.y);
			doLeftClick();
			limb.delay(DELAY_ACTION);
			type(a.getAction());
			//System.out.println(cur_a.getAction());
		}
	}
	
	public void execute(Action[] a) {
		for (Action cur_a : a) {
			execute(cur_a);
			limb.delay(DELAY_ACTION);
		}
	}

}
