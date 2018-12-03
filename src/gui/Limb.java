package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import engine.Bot;

public class Limb extends JFrame {
	
	private Rectangle screenRect;
	private Bot bot;
	private ScreenSelection screenSelection;
	private JScrollPane scrollPane;
	private TableAction tableAction;
	private TableModelAction modelAction;
	
	public Limb() {
		super("Limb Is a Mimetic Bot !");

		// Action table
		modelAction = new TableModelAction();
		
		// Screen selection
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenRect = new Rectangle(dim);
		screenSelection = new ScreenSelection(modelAction);
		bot = new Bot(this, screenSelection);
		scrollPane = new JScrollPane(screenSelection);
		screenSelection.setImage(bot.doScreenCapture(screenRect));
		
		// Action model
		tableAction = new TableAction(bot, modelAction);
		
		add(scrollPane, BorderLayout.CENTER);
		add(tableAction, BorderLayout.EAST);

		setSize(700, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Limb limb = new Limb();
		limb.setLocationRelativeTo(null); // center
		limb.setVisible(true);
	}

}
