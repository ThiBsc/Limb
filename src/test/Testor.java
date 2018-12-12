package test;

import java.awt.Dimension;
import java.awt.Toolkit;

import engine.Bot;

public class Testor {

	public static void main(String[] args) {
		Bot b = new Bot();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		b.moveCursor(screen.width/2, 400);
		//b.type("I'm LIMB, the Mimetic Bot");
		//b.doRightClick();
		/*File f = new File("robotcapture.bmp");
		try {
			ImageIO.write(b.doScreenCapture(rect), "bmp", f);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
