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
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import engine.Bot;

public class ToolBar extends JToolBar implements ActionListener {
	
	/**
	 * Icons made by "https://www.flaticon.com/authors/smashicons" from "https://www.flaticon.com/" is licensed by CC 3.0 BY
	 * Icons made by "https://www.freepik.com/" from "https://www.flaticon.com/" is licensed by CC 3.0 BY
	 */
	private JButton btnAddAction, btnRefreshScreenCapture, btnRemove, btnRun;
	private Bot limb;
	private ScreenSelection screenSelection;
	private TableAction tableAction;

	public ToolBar(Bot bot, ScreenSelection screenSelection, TableAction tableAction) {
		super("ToolBar");
		this.limb = bot;
		this.screenSelection = screenSelection;
		this.tableAction = tableAction;
		init();
	}

	public ToolBar(Bot bot, ScreenSelection screenSelection, TableAction tableAction, int orientation) {
		super("ToolBar", orientation);
		this.limb = bot;
		this.screenSelection = screenSelection;
		this.tableAction = tableAction;
		init();
	}
	
	private void init() {
		setFloatable(false);
		
		btnAddAction = new JButton();
		btnRefreshScreenCapture = new JButton();
		btnRemove = new JButton();
		btnRun = new JButton();

		addButton(btnRefreshScreenCapture, getClass().getResource("/icons/refresh.png"), "Refresh screen capture", "Refresh screen", "refresh");
		addSeparator();
		addButton(btnAddAction, getClass().getResource("/icons/add.png"), "Add action", "Add action", "add");
		addButton(btnRemove, getClass().getResource("/icons/remove.png"), "Remove action", "Remove action", "remove");
		addButton(btnRun, getClass().getResource("/icons/run.png"), "Run selected actions", "Run selected actions", "run");
	}
	
	private void addButton(JButton btn, URL icon_url, String tooltip, String text, String actionCommand) {
		try {
			ImageIcon icon = new ImageIcon(new ImageIcon(icon_url).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
			btn.setIcon(icon);
			btn.setToolTipText(tooltip);
			//btn.setBorderPainted(false);
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
		case "remove":
			tableAction.deleteAction();
			break;
		case "run":
			try {
				int niter = Integer.parseInt(JOptionPane.showInputDialog("Select n iteration"));
				tableAction.executeAction(niter);
			} catch (Exception e2) {
				System.err.println(e2.getMessage());
			}
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
