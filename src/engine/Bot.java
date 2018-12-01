package engine;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * @author thibdev
 * LIMB Is a Mimetic Bot
 */
public class Bot {
	
	private Robot limb;
	
	public Bot() {
		try {
			limb = new Robot();
		} catch (AWTException e) {
			System.err.println("Unable to start LIMB, reason:\n"+e.getMessage());
		}
	}
	
	public void moveCursor(int x, int y) {
		limb.mouseMove(x, y);
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
	
	public void type(String text) {
		for (char c : text.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (keyCode != KeyEvent.CHAR_UNDEFINED) {
				try {
					if (Character.isUpperCase(c)) {
						limb.keyPress(KeyEvent.VK_SHIFT);
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

}
