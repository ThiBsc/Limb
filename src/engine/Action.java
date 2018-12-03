package engine;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Action {
	
	private ImageIcon image;
	private String action;
	private Rectangle area;

	public Action(Image image, ActionEnum action, Rectangle area) {
		this.image = new ImageIcon(image);
		this.action = action.toString();
		this.area = area;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = new ImageIcon(image);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Rectangle getArea() {
		return area;
	}

	public void setArea(Rectangle area) {
		this.area = area;
	}
	
	

}
