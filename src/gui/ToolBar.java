package gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import engine.Bot;

public class ToolBar extends JToolBar implements ActionListener {
	
	/**
	 * Icons made by "https://www.flaticon.com/authors/smashicons" from "https://www.flaticon.com/" is licensed by CC 3.0 BY
	 * Icons made by "https://www.freepik.com/" from "https://www.flaticon.com/" is licensed by CC 3.0 BY
	 */
	private JButton btnAddAction, btnRefreshScreenCapture;
	private Bot limb;
	private ScreenSelection screenSelection;

	public ToolBar(Bot bot, ScreenSelection screenSelection) {
		super("ToolBar");
		this.limb = bot;
		this.screenSelection = screenSelection;
		init();
	}

	public ToolBar(Bot bot, ScreenSelection screenSelection, int orientation) {
		super("ToolBar", orientation);
		this.limb = bot;
		this.screenSelection = screenSelection;
		init();
	}
	
	private void init() {
		setFloatable(false);
		
		btnAddAction = new JButton();
		btnRefreshScreenCapture = new JButton();

		addButton(btnRefreshScreenCapture, getClass().getResource("/icons/refresh.png"), "Refresh screen capture", "Refresh screen", "refresh");
		addButton(btnAddAction, getClass().getResource("/icons/add.png"), "Add action", "Add action", "add");
	}
	
	private void addButton(JButton btn, URL icon_url, String tooltip, String text, String actionCommand) {
		try {
			ImageIcon icon = new ImageIcon(new ImageIcon(icon_url).getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
			btn.setIcon(icon);
			btn.setToolTipText(tooltip);
			btn.setBorder(null);
		} catch (Exception e) {
			btn.setText(text);
		} finally {
			btn.setActionCommand(actionCommand);
			btn.addActionListener(this);
			add(btn);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "add":
			screenSelection.addAction();
			break;
		case "refresh":
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			screenSelection.setImage(limb.doScreenCapture(new Rectangle(dim)));
			break;
		default:
			break;
		}
	}

}
