package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import engine.Bot;

public class Limb extends JFrame {
	
	private Rectangle screenRect;
	private Bot bot;
	private ScreenSelection screenSelection;
	private JScrollPane scrollPane;
	
	public Limb() {
		super("Limb Is a Mimetic Bot !");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenRect = new Rectangle(dim);
		bot = new Bot();
		screenSelection = new ScreenSelection();
		screenSelection.setImage(bot.doScreenCapture(screenRect));
		scrollPane = new JScrollPane(screenSelection);
		add(scrollPane, BorderLayout.CENTER);

		setSize(700, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Limb limb = new Limb();
		limb.setLocationRelativeTo(null); // center
		limb.setVisible(true);
	}

}
