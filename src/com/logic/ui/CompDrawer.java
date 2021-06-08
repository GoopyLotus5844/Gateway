package com.logic.ui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
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

	/**
	 * The amount of space between consecutive inputs on a BasicGate
	 */
	public static final int BASIC_INPUT_SPACING = 50;

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
	 * A convenience method that draws the connections and component body
	 * @param g The graphics object to use for painting
	 */
	public void draw(Graphics g){
		drawConnections(g);
		drawComponentBody(g);
	}

	/**
	 * Draws the body of the component. This method requires the component to have an active image with a valid index.
	 * @param g The graphics object to use
	 */
	public void drawComponentBody(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		CompType type = lcomp.getType();
		BufferedImage currentImage = getActiveImage().getBufferedImage(CompRotator.RIGHT);
		int x = lcomp.getX(), y = lcomp.getY();
		int width = currentImage.getWidth() / RENDER_SCALE, height = currentImage.getHeight() / RENDER_SCALE;
		int rotation = lcomp.getRotator().getRotation();

		//Rotate the graphics object so that the body of the component is always aligned with the upper right corner of its bounds rectangle
		AffineTransform at = getTransform(rotation, x, y, width, height);
		g2d.transform(at);

		//Render curved bar for XOR and XNOR
		if(type == CompType.XOR || type == CompType.XNOR) {
			GeneralPath shape = new GeneralPath();
			shape.moveTo(x - 8, y + 3);
			shape.curveTo(x + 5, y + 30, x + 5, y + 50, x - 8, y + 77);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.draw(shape);
		}

		//Render the body of the gate
		g.drawImage(currentImage, x, y, width, height, null);

		//Render a dot to show if the gate is inverted
		if(type == CompType.NOT || type == CompType.NAND || type == CompType.NOR || type == CompType.XNOR){
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.WHITE);
			g2d.fillOval(x + 75, y + 33, 14, 14);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x + 75, y + 33, 15, 15);
		}

		//Undo the graphics transformation
		try {
			at.invert();
			g2d.transform(at);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		//Render a blue box around the body of the component if it is selected
		if(lcomp.isSelected()) {
			g.setColor(Selection.SELECT_COLOR);
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(lcomp.getBounds());
		}
		//TODO refactor this mess somehow idk
	}

	/**
	 * Draws the connections (with lines) for the component as specified by its IOManager
	 * @param g The graphics object to use
	 */
	public void drawConnections(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		IOManager io = lcomp.getIO();
		Point barStart = null, barStop = null;
		for(int i = 0; i < io.getNumInputs(); i++) {
			Point result = drawConnection(io.connectionAt(i, Connection.INPUT), g2d);
			if(i == 0) barStart = result;
			if(i == io.getNumInputs() - 1) barStop = result;
		}
		for(int i = 0; i < io.getNumOutputs(); i++) {
			drawConnection(io.connectionAt(i, Connection.OUTPUT), g2d);
		}

		if(lcomp instanceof BasicGate && io.getNumInputs() > 2) {
			g2d.setColor(Color.BLACK);
			g2d.drawLine(barStart.x, barStart.y, barStop.x, barStop.y);
		}
	}

	/**
	 * Draws a line with a dot to represent a connection, taking the direction of the connection into account.  Returns a point showing
	 * the other end of the line, which is used for drawing another connecting line on a BasicGate when there are many inputs.
	 * @param c The connection to render
	 * @param g2d The Graphics2D object to use
	 * @return The endpoint of the line opposite the connection.
	 */
	private Point drawConnection(Connection c, Graphics2D g2d){
		Point p = c.getCoord();
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3));
		int direction = c.getAbsoluteDirection();
		Point connectEnd = null;
		if(direction == CompRotator.RIGHT) {
			g2d.drawLine(p.x, p.y, p.x - 37, p.y);
			connectEnd = new Point(p.x - 37, p.y);
		}
		if(direction == CompRotator.UP) {
			g2d.drawLine(p.x, p.y, p.x, p.y + 37);
			connectEnd = new Point(p.x, p.y + 37);
		}
		if(direction == CompRotator.LEFT) {
			g2d.drawLine(p.x, p.y, p.x + 37, p.y);
			connectEnd = new Point(p.x + 37, p.y);
		}
		if(direction == CompRotator.DOWN) {
			g2d.drawLine(p.x, p.y, p.x, p.y - 37);
			connectEnd = new Point(p.x, p.y - 37);
		}
		g2d.setColor(Selection.SELECT_COLOR);
		g2d.fillOval(p.x - 9, p.y - 9, 18, 18);
		return connectEnd;
	}

	/**
	 * Calculates the transformation to be applied to the graphics object when rendering the body of the component
	 * @param direction The direction the component is facing
	 * @param x The x position of the component
	 * @param y The y position of the component
	 * @param width The width of the component body
	 * @param height The height of the component body
	 * @return The transformation
	 */
	private AffineTransform getTransform(int direction, int x, int y, int width, int height){
		double theta = 0;
		int tx = 0, ty = 0;
		if(direction == CompRotator.UP) {
			theta = -Math.PI / 2;
			tx = -width;
		}
		else if(direction == CompRotator.LEFT) {
			theta = Math.PI;
			tx = -width;
			ty = -height;
		}
		else if(direction == CompRotator.DOWN) {
			theta = Math.PI / 2;
			ty = -height;
		}
		AffineTransform at = new AffineTransform();
		at.rotate(theta, x, y);
		at.translate(tx, ty);
		return at;
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
