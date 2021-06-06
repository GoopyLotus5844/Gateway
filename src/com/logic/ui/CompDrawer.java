package com.logic.ui;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

import com.logic.components.*;
import com.logic.input.Selection;
import com.logic.main.LogicSimApp;
import com.logic.test.And;
import com.logic.test.AndOptimized;
import com.logic.test.Or;

/**
 * A class possessed by every LComponent that provides the code for drawing the component
 * @author toddstennes
 *
 */
public class CompDrawer implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The amount by which LogicImages are scaled down when they are rendered.
	 * This value should be the same as Camera.maxZoom because the images must still be of sufficient resolution when completely zoomed in.
 	 */
	public static final int RENDER_SCALE = 3;

	public static final int BASIC_CONNECTION_SPACING = 50;

	/**
	 * The indexes of the images that this component uses (in the IconLoader)
	 */
	private int[] images;
	
	/**
	 * The index of the image that is currently active, (in the images array) which means that it will be the image that is drawn when 
	 * draw(...) is called
	 */
	private int activeImageIndex;
	
	/**
	 * The LComponent that uses this CompDrawer
	 */
	private LComponent lcomp;
	
	/**
	 * Constructs a new CompDrawer
	 * @param lcomp The LComponent
	 */
	public CompDrawer(LComponent lcomp) {
		this.lcomp = lcomp;
	}
	
	/**
	 * Returns the active image
	 * @return The active image index
	 */
	public int getActiveImageIndex() {
		return activeImageIndex;  
	}
	
	/**
	 * Sets the active image
	 * @param activeImageIndex The new active image index
	 */
	public void setActiveImageIndex(int activeImageIndex) {
		this.activeImageIndex = activeImageIndex;
	}
	
	/**
	 * Returns the active image as specified by activeImageIndex
	 * @return The active image
	 */
	public LogicImage getActiveImage() {
		System.out.println(activeImageIndex);
		return LogicSimApp.iconLoader.logicImages[images[activeImageIndex]];
	}
	
	/**
	 * Draws the LComponent by displaying its image under the components current rotation and drawing a box around the component if it is
	 * selected
	 * @param g The Graphics object to use for painting
	 */
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		CompType type = lcomp.getType();
		IOManager io = lcomp.getIO();
		int x = lcomp.getX(), y = lcomp.getY();

		for(int i = 0; i < io.getNumInputs(); i++) {
			drawConnection(io.connectionAt(i, Connection.INPUT), g2d);
		}

		for(int i = 0; i < io.getNumOutputs(); i++) {
			drawConnection(io.connectionAt(i, Connection.OUTPUT), g2d);
		}

		if(lcomp instanceof BasicGate && io.getNumInputs() > 2) {
			Point p1 = io.connectionAt(0, Connection.INPUT).getCoord();
			Point p2 = io.connectionAt(io.getNumInputs() - 1, Connection.INPUT).getCoord();
			g2d.setColor(Color.BLACK);
			g2d.drawLine(x + 12, p1.y, x + 12, p2.y);
		}

		if(type == CompType.XOR || type == CompType.XNOR) {
			GeneralPath shape = new GeneralPath();
			shape.moveTo(x - 8, y + 3);
			shape.curveTo(x + 5, y + 30, x + 5, y + 50, x - 8, y + 77);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.draw(shape);
		}

		BufferedImage currentImage = getActiveImage().getBufferedImage(lcomp.getRotator().getRotation());
		g.drawImage(currentImage, x, y,currentImage.getWidth() / RENDER_SCALE, currentImage.getHeight() / RENDER_SCALE, null);

		if(type == CompType.NOT || type == CompType.NAND || type == CompType.NOR || type == CompType.XNOR){
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.WHITE);
			g2d.fillOval(x + 75, y + 32, 14, 14);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x + 75, y + 32, 15, 15);
		}

		if(lcomp.isSelected()) {
			g.setColor(Selection.SELECT_COLOR);
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(lcomp.getBounds());
		}
	}

	private void drawConnection(Connection c, Graphics2D g2d){
		Point p = c.getCoord();
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3));
		int direction = c.getAbsoluteDirection();
		if(direction == CompRotator.RIGHT) g2d.drawLine(p.x, p.y, p.x - 35, p.y);
		if(direction == CompRotator.UP) g2d.drawLine(p.x, p.y, p.x, p.y + 35);
		if(direction == CompRotator.LEFT) g2d.drawLine(p.x, p.y, p.x + 35, p.y);
		if(direction == CompRotator.DOWN) g2d.drawLine(p.x, p.y, p.x, p.y - 35);
		g2d.setColor(Selection.SELECT_COLOR);
		g2d.fillOval(p.x - 9, p.y - 9, 18, 18);
	}
	
	/**
	 * Sets the array of LogicImages that this component uses. This method is intended for use by subclasses of LComponent to define what
	 * images they are going to draw. The indexes of the images that have been placed in the given array will match the indexes given when 
	 * calling setActiveImage(...)
	 * @param images The array of LogicImages that the component will use
	 */
	public void setImages(int[] images) {
		this.images = images;
	}
}
