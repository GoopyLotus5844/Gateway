package com.logic.ui;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.logic.components.BasicGate;
import com.logic.components.LComponent;
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
	 * The amount by which the LogicImages from the IconLoader are scaled up before they are drawn
	 */
	//public static final float IMAGE_SCALE = 7;
	
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
		return LogicSimApp.iconLoader.logicImages[images[activeImageIndex]];
	}
	
	/**
	 * Draws the LComponent by displaying its image under the components current rotation and drawing a box around the component if it is
	 * selected
	 * @param g The Graphics object to use for painting
	 */
	public void draw(Graphics g) {
		/*
		BufferedImage currentImage = getActiveImage().getBufferedImage(lcomp.getRotator().getRotation());
		g.drawImage(currentImage, lcomp.getX(), lcomp.getY(),
				(int) IMAGE_SCALE * currentImage.getWidth(), (int) IMAGE_SCALE * currentImage.getHeight(), null);
		g.setColor(Selection.SELECT_COLOR);
		 */

		GeneralPath shape = new GeneralPath();
		shape.moveTo(lcomp.getX() - 8, lcomp.getY() + 3);
		shape.curveTo(lcomp.getX() + 5, lcomp.getY() + 30, lcomp.getX() + 5, lcomp.getY() + 50, lcomp.getX() - 8, lcomp.getY() + 77);
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.rotate(Math.PI / 2, lcomp.getX(), lcomp.getY());
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.draw(shape);

		g2d.drawLine(lcomp.getX() + 12, lcomp.getY() + 15, lcomp.getX() - 30, lcomp.getY() + 15);
		g2d.drawLine(lcomp.getX() + 12, lcomp.getY() + 65, lcomp.getX() - 30, lcomp.getY() + 65);
		//g2d.drawLine(lcomp.getX() + 12, lcomp.getY() + 90, lcomp.getX() - 30, lcomp.getY() + 90);
		g2d.drawLine(lcomp.getX() + 80, lcomp.getY() + 40, lcomp.getX() + 120, lcomp.getY() + 40);

		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.WHITE);
		g2d.fillOval(lcomp.getX() + 80, lcomp.getY() + 32, 14, 14);
		g2d.setColor(Color.BLACK);
		g2d.drawOval(lcomp.getX() + 80, lcomp.getY() + 32, 15, 15);
		g2d.setStroke(new BasicStroke(4));
		//g2d.drawLine(lcomp.getX() + 12, lcomp.getY() - 10, lcomp.getX() + 12, lcomp.getY() + 90);

		g2d.setColor(Selection.SELECT_COLOR);
		g2d.fillOval(lcomp.getX() - 37, lcomp.getY() + 6, 18, 18);
		g2d.fillOval(lcomp.getX() - 37, lcomp.getY() + 56, 18, 18);
		//g2d.fillOval(lcomp.getX() - 37, lcomp.getY() + 81, 18, 18);
		g2d.fillOval(lcomp.getX() + 111, lcomp.getY() + 31, 18, 18);

		Or or = new Or();
		or.setDimension(new Dimension(80, 80));
		//or.paintIcon(null, g2d, lcomp.getX(), lcomp.getY());

		g.drawImage(LogicSimApp.iconLoader.logicImages[0].getBufferedImage(0), lcomp.getX(), lcomp.getY(), 80, 80, null);

		g2d.setStroke(new BasicStroke(2));
		if(lcomp.isSelected()) ((Graphics2D) g).draw(new Rectangle(lcomp.getX(), lcomp.getY(), 64, 64));
		//g2d.rotate(-Math.PI / 2, lcomp.getX(), lcomp.getY());

		/*
		Conclusions:
		Image size 80x80
		Inputs spaced 50 apart
		For more than two inputs, draw vertical line at x + 12 to connect the inputs
		Draw not dot at x + 80
		exclusive curve starts and ends at x - 8
		 */
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
