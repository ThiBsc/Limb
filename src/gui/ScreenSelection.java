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
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import engine.Action;
import engine.ActionEnum;

public class ScreenSelection extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	private Image image;
	private boolean isInSelection;
	private Point startSelPoint, endSelPoint;
	private TableModelAction modelAction;
	
	public ScreenSelection(TableModelAction modelAction) {
		image = null;
		isInSelection = false;
		startSelPoint = endSelPoint = null;
		this.modelAction = modelAction;
		addKeyListener(this);
		setFocusable(true); // For KeyListener
		setRequestFocusEnabled(true);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void setImage(Image image) {
		this.image = image;
		setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		if (isInSelection) {
			g.setColor(Color.red);
			if (startSelPoint != null && endSelPoint != null) {
				int x = Math.min(startSelPoint.x, endSelPoint.x);
				int y = Math.min(startSelPoint.y, endSelPoint.y);;
				int width = startSelPoint.x < endSelPoint.x ? endSelPoint.x-startSelPoint.x : startSelPoint.x-endSelPoint.x;
				int height = startSelPoint.y < endSelPoint.y ? endSelPoint.y-startSelPoint.y : startSelPoint.y-endSelPoint.y;
				g.drawRect(x, y, width, height);
			}
		}
	}
	
	private Image getSelectionImage() {
		Image selection = null;
		Rectangle area = getSelectionArea();
		if (area != null) {
			selection = ((BufferedImage)image).getSubimage(area.x, area.y, area.width, area.height);
		}
		return selection;
	}
	
	private Rectangle getSelectionArea() {
		Rectangle area = null;
		if (startSelPoint != null && endSelPoint != null) {
			int x = Math.min(startSelPoint.x, endSelPoint.x);
			int y = Math.min(startSelPoint.y, endSelPoint.y);;
			int width = startSelPoint.x < endSelPoint.x ? endSelPoint.x-startSelPoint.x : startSelPoint.x-endSelPoint.x;
			int height = startSelPoint.y < endSelPoint.y ? endSelPoint.y-startSelPoint.y : startSelPoint.y-endSelPoint.y;
			area = new Rectangle(x, y, width, height);
		}
		return area;
	}
	
	private void addAction() {
		Rectangle area = getSelectionArea();
		if (area != null) {
			Action a  = new Action(getSelectionImage(), ActionEnum.LEFT_CLICK, area);
			modelAction.addAction(a);
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
			isInSelection = true;
			startSelPoint = e.getPoint();
			endSelPoint = null;
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isInSelection = false;
		}
	}
	
	/** MouseMotionListener */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (isInSelection) {
			endSelPoint = e.getPoint();
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
