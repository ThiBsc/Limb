package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import engine.Bot;

public class Limb extends JFrame {
	
	private Bot bot;
	private ScreenSelection screenSelection;
	private ToolBar toolBar;
	private JSplitPane splitPane;
	private JScrollPane scrollPane, scrollPaneTable;
	private TableAction tableAction;
	private TableModelAction modelAction;
	
	public Limb() {
		super("Limb Is a Mimetic Bot !");

		// Bot
		bot = new Bot();
		
		// Action table
		modelAction = new TableModelAction();
		tableAction = new TableAction(bot, modelAction);
		
		// Screen selection
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenSelection = new ScreenSelection(modelAction);
		screenSelection.setImage(bot.doScreenCapture(new Rectangle(dim)));
		scrollPane = new JScrollPane(screenSelection);
		scrollPaneTable = new JScrollPane(tableAction);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, scrollPaneTable);
		splitPane.setResizeWeight(0.8);
		
		// ToolBar
		toolBar = new ToolBar(bot, screenSelection, JToolBar.HORIZONTAL);
		
		add(toolBar, BorderLayout.NORTH);
		add(splitPane, BorderLayout.CENTER);

		setSize(700, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Limb limb = new Limb();
		limb.setLocationRelativeTo(null); // center
		limb.setVisible(true);
	}

}
