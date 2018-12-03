package engine;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import gui.ScreenSelection;

/**
 * @author thibdev
 * LIMB Is a Mimetic Bot
 */
public class Bot {
	
	private Robot limb;
	private ScreenSelection screenSelection;
	private JFrame mainWindow;
	
	public Bot(JFrame window, ScreenSelection ssel) {
		try {
			limb = new Robot();
			mainWindow = window;
			screenSelection = ssel;
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
	
	public void enter() {
		limb.keyPress(KeyEvent.VK_ENTER);
		limb.keyRelease(KeyEvent.VK_ENTER);
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
	
	public void execute(Action[] a) {
		for (Action cur_a : a) {
			System.out.println(cur_a.getAction());
			Rectangle area = cur_a.getArea();
			Point pos = new Point(area.x+area.width/2, area.y+area.height/2);
			//System.out.println(pos);
			moveCursor(pos.x, pos.y);
			if (mainWindow != null) {
				mainWindow.setState(Frame.ICONIFIED);	
			}
			if (cur_a.getAction().equals(ActionEnum.LEFT_CLICK.toString())) {
				doLeftClick();
			} else if (cur_a.getAction().equals(ActionEnum.DOUBLE_LEFT_CLICK.toString())) {
				doDoubleLeftCLick();
			} else if (cur_a.getAction().equals(ActionEnum.RIGHT_CLICK.toString())) {
				doRightClick();
			} else if (cur_a.getAction().equals(ActionEnum.SELECT_ALL.toString())) {
				selectAll();
			} else if (cur_a.getAction().equals(ActionEnum.COPY.toString())) {
				copy();
			} else if (cur_a.getAction().equals(ActionEnum.PASTE.toString())) {
				paste();
			} else if (cur_a.getAction().equals(ActionEnum.COPY_PASTE.toString())) {
				copyPaste();
			} else if (cur_a.getAction().equals(ActionEnum.ENTER.toString())) {
				enter();
			} else {
				doLeftClick();
				limb.delay(100);
				type(cur_a.getAction());
				//System.out.println(cur_a.getAction());
			}
		}
		if (screenSelection != null) {
			limb.delay(500);
			Rectangle screen_rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			screenSelection.setImage(doScreenCapture(screen_rect));
		}
		if (mainWindow != null) {
			mainWindow.setState(Frame.NORMAL);	
		}
	}

}
