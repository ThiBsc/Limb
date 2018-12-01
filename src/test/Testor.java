package test;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.Bot;
import engine.ImageProcessing;

public class Testor {

	public static void main(String[] args) {
		Bot b = new Bot();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		b.moveCursor(screen.width/2, 400);
		//b.type("I'm LIMB, the Mimetic Bot");
		//b.doRightClick();
		Rectangle rect = new Rectangle(10,10,200,200);
		ImageProcessing ip = new ImageProcessing();
		ip.FFT(b.doScreenCapture(rect));
		/*File f = new File("robotcapture.bmp");
		try {
			ImageIO.write(b.doScreenCapture(rect), "bmp", f);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
