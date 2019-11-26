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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.Bot;

/**
 * @author thibsc
 */
public class ToolBar extends JToolBar implements ActionListener {
	
	/**
	 * Icons made from "https://www.flaticon.com/" is licensed by CC 3.0 BY
	 */
	private JButton btnAddAction, btnRefreshScreenCapture, btnRemove, btnRun, btnSettings;
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
		btnSettings = new JButton();

		addButton(btnRefreshScreenCapture, getClass().getResource("/icons/refresh.png"), "Refresh screen capture", "Refresh screen", "refresh");
		addButton(btnSettings, getClass().getResource("/icons/settings.png"), "Configure the bot", "Configure the bot", "settings");
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
				int niter = Integer.parseInt(JOptionPane.showInputDialog(this.getParent(), "Select n iteration"));
				tableAction.executeAction(niter);
			} catch (Exception e2) {
				System.err.println(e2.getMessage());
			}
			break;
		case "refresh":
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			screenSelection.setImage(limb.doScreenCapture(new Rectangle(dim)));
			break;
		case "settings":
			String format = "Delay %s (%d ms)";
			JLabel lblIteration = new JLabel(String.format(format, "iteration", Bot.DELAY_ITERATION));
			JLabel lblAction = new JLabel(String.format(format, "action", Bot.DELAY_ACTION));
			JLabel lblKeyboard = new JLabel(String.format(format, "keyboard", Bot.DELAY_KEYBOARD));
			
			JSlider slideIteration = new JSlider(50, 1000, Bot.DELAY_ITERATION);
			JSlider slideAction = new JSlider(20, 1000, Bot.DELAY_ACTION);
			JSlider slideKeyboard = new JSlider(20, 1000, Bot.DELAY_KEYBOARD);

			//slideIteration.setExtent(5);
			//slideAction.setExtent(5);
			//slideKeyboard.setExtent(5);
			
			slideIteration.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					lblIteration.setText(String.format(format, "iteration", slideIteration.getValue()));
				}
			});
			slideAction.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					lblAction.setText(String.format(format, "action", slideAction.getValue()));
				}
			});
			slideKeyboard.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					lblKeyboard.setText(String.format(format, "keyboard", slideKeyboard.getValue()));
				}
			});

			JComponent[] settings = new JComponent[] {
			        lblIteration,
			        slideIteration,
			        lblAction,
			        slideAction,
			        lblKeyboard,
			        slideKeyboard
			};
			int result = JOptionPane.showConfirmDialog(this.getParent(), settings, "Bot delay settings", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
			    Bot.DELAY_ITERATION = slideIteration.getValue();
			    Bot.DELAY_ACTION = slideAction.getValue();
			    Bot.DELAY_KEYBOARD = slideKeyboard.getValue();
			}
			break;
		default:
			break;
		}
	}

}
