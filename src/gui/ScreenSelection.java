package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class ScreenSelection extends JPanel implements MouseListener, MouseMotionListener {
	
	private Image image;
	private boolean isInSelection;
	private Point startSelPoint, endSelPoint;
	
	public ScreenSelection() {
		image = null;
		isInSelection = false;
		startSelPoint = endSelPoint = null;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void setImage(Image image) {
		this.image = image;
		setSize(image.getWidth(null), image.getHeight(null));
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

	@Override
	public void mouseClicked(MouseEvent e) {
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
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isInSelection = false;
		}
	}
	
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
