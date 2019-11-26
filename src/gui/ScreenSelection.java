package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import engine.Action;
import engine.ActionEnum;

/**
 * @author thibsc
 */
public class ScreenSelection extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	private Image image;
	private Point selectionPos, curActionPos;
	private JTable tableAction;
	private TableModelAction modelAction;
	
	public ScreenSelection(JTable tableAction) {
		image = null;
		selectionPos = new Point(-1, -1);
		curActionPos = new Point(-1, -1);
		this.tableAction = tableAction;
		this.modelAction = (TableModelAction) tableAction.getModel();
		addKeyListener(this);
		setFocusable(true); // For KeyListener
		setRequestFocusEnabled(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		// To draw a circle at the position of the selected action
		tableAction.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					Action a = modelAction.getAction(tableAction.getSelectedRow());
					curActionPos = a.getPos();
				} catch (IndexOutOfBoundsException e) {
					curActionPos = new Point(-1, -1);
				} finally {
					repaint();
				}
			}
	    });
	}
	
	public void setImage(Image image) {
		this.image = image;
		setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
		repaint();
	}
	
	public void addAction() {
		if (selectionPos.getX() != -1 && selectionPos.getY() != -1) {
			Action a  = new Action(ActionEnum.LEFT_CLICK, selectionPos);
			modelAction.addAction(a);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		// Draw the last mouse click position
		if (selectionPos.getX() != -1 && selectionPos.getY() != -1) {
			g.setColor(Color.red);
			g.fillOval(selectionPos.x-6, selectionPos.y-6, 10, 10);
		}
		// Draw the current position of the selected action in the table 
		if (curActionPos.getX() != -1 && curActionPos.getY() != -1) {
			g.setColor(Color.green);
			g.fillOval(curActionPos.x-6, curActionPos.y-6, 10, 10);
		}
	}
	
	/** KeyListener */
	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_N) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
			addAction();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	/** MouseListener */
	@Override
	public void mouseClicked(MouseEvent e) {
		requestFocus();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			// Remove the old location
			Rectangle dirty_rect = new Rectangle((int)selectionPos.getX()-10, (int)selectionPos.getY()-10, 20, 20);
			repaint(dirty_rect);
			selectionPos = e.getPoint();
			// Draw the new location
			dirty_rect.setLocation((int)selectionPos.getX()-10, (int)selectionPos.getY()-10);
			repaint(dirty_rect);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			Rectangle dirty_rect = new Rectangle((int)selectionPos.getX()-10, (int)selectionPos.getY()-10, 20, 20);
			repaint(dirty_rect);
			selectionPos.setLocation(-1, -1);
		}
	}
	
	/** MouseMotionListener */
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
