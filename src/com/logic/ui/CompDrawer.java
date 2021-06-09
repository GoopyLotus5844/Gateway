package com.logic.ui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.logic.components.*;
import com.logic.input.Selection;
import com.logic.main.LogicSimApp;
import org.apache.batik.ext.awt.image.renderable.AffineRable;
import org.apache.batik.gvt.GraphicsNode;

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
	 * Choose between using SVG GraphicsNodes and pre-rendered high resolution BufferedImages for component drawing
	 */
	private boolean useSVG;

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
	 * The last recorded position and rotation values, used to determine if the rotation transform needs to be recalculated
	 */
	private int prevX, prevY, prevRotation;

	/**
	 * The most recently computed graphics transformation
	 */
	private AffineTransform at;
	
	/**
	 * Constructs a new CompDrawer
	 * @param lcomp The LComponent
	 */
	public CompDrawer(LComponent lcomp) {
		this.lcomp = lcomp;
		useSVG = false;
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
	 * Returns the active image (as an SVG) as specified by activeImageIndex
	 * @return The active SVG image
	 */
	public GraphicsNode getActiveSVG(){
		return LogicSimApp.iconLoader.logicSVGs[images[activeImageIndex]];
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
		BufferedImage currentImage = getActiveImage().getBufferedImage(CompRotator.RIGHT);
		Rectangle b = lcomp.getBoundsRight();

		applyTransform(lcomp.getRotator().getRotation(), b.x, b.y, b.width, b.height, g2d);
		if(useSVG) {
			GraphicsNode svgIcon = getActiveSVG();
			int size = Math.max(b.width, b.height);
			float difference = Math.abs(b.width - b.height);
			if(b.width <= b.height)
				svgIcon.setTransform(new AffineTransform(size, 0, 0, size, b.x - difference / 2, b.y));
			else
				svgIcon.setTransform(new AffineTransform(size, 0, 0, size, b.x, b.y - difference / 2));
			svgIcon.paint(g2d);
		}
		else g.drawImage(currentImage, b.x, b.y, b.width, b.height, null);
		reverseTransform(g2d);

		if(lcomp.isSelected()) {
			g.setColor(Selection.SELECT_COLOR);
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(lcomp.getBounds());
		}
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
	 * Draws a curved line to show that a gate is an XOR or an XNOR
	 * @param g2d The Graphics2D object to use
	 */
	public void drawExclusive(Graphics2D g2d){
		Rectangle b = lcomp.getBoundsRight();
		applyTransform(lcomp.getRotator().getRotation(), b.x, b.y, b.width, b.height, g2d);

		GeneralPath shape = new GeneralPath();
		shape.moveTo(b.x - 8, b.y + 3);
		shape.curveTo(b.x + 5, b.y + 30, b.x + 5, b.y + 50, b.x - 8, b.y + 77);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.draw(shape);
		reverseTransform(g2d);
	}

	/**
	 * Draws a dot to indicate that the gate is a "not" variant
	 * @param g2d The Graphics2D object to use
	 */
	public void drawInverted(Graphics2D g2d){
		Rectangle b = lcomp.getBoundsRight();
		applyTransform(lcomp.getRotator().getRotation(), b.x, b.y, b.width, b.height, g2d);

		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.WHITE);
		g2d.fillOval(b.x + 75, b.y + 33, 14, 14);
		g2d.setColor(Color.BLACK);
		g2d.drawOval(b.x + 75, b.y + 33, 15, 15);
		reverseTransform(g2d);
	}

	/**
	 * Calculates the transformation to be applied to the graphics object when rendering the body of the component
	 * @param rotation The direction the component is facing
	 * @param x The x position of the component
	 * @param y The y position of the component
	 * @param width The width of the component body
	 * @param height The height of the component body
	 * @return The transformation
	 */
	private AffineTransform getTransform(int rotation, int x, int y, int width, int height){
		double theta = 0;
		int tx = 0, ty = 0;
		if(rotation == CompRotator.UP) {
			theta = -Math.PI / 2;
			tx = -width;
		}
		else if(rotation == CompRotator.LEFT) {
			theta = Math.PI;
			tx = -width;
			ty = -height;
		}
		else if(rotation == CompRotator.DOWN) {
			theta = Math.PI / 2;
			ty = -height;
		}
		AffineTransform at = new AffineTransform();
		at.rotate(theta, x, y);
		at.translate(tx, ty);
		return at;
	}

	/**
	 * Applies the graphics transformation needed for the component to appear correctly when rotated. If the position and rotation have
	 * changed since this method was last called, a new AffineTransform is computed, otherwise the old one is used. Used for all rendering
	 * besides drawing connections.
	 * @param rotation The current direction the component is facing
	 * @param x The x position
	 * @param y The y position
	 * @param width The width of the component (right facing)
	 * @param height The height of the component (right facing)
	 * @param g The Graphics2D object
	 */
	private void applyTransform(int rotation, int x, int y, int width, int height, Graphics2D g){
		if(x != prevX || y != prevY || rotation != prevRotation || at == null){
			prevX = x;
			prevY = y;
			prevRotation = rotation;
			at = getTransform(rotation, x, y, width, height);
		}
		g.transform(at);
	}

	/**
	 * Applies the current AffineTransform in reverse.  This method will not recompute the transform even if values have changed.
	 * @param g The Graphics2D object
	 */
	private void reverseTransform(Graphics2D g){
		try {
			g.transform(at.createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
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

	/**
	 * Sets the component to either render an SVG (slow) or use pre-calculated high resolution BufferedImages (less than ideal scaling).  This option
	 * is set to false by default (BufferedImages)
	 * @param useSVG The SVG flag
	 */
	public void setUseSVG(boolean useSVG){
		this.useSVG = useSVG;
	}
}