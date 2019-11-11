package com.logic.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;
import java.io.Serializable;

import com.logic.input.Selection;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompRotator;
import com.logic.util.Deletable;

/**
 * A CircuitElement that holds a boolean signal and connects an output connection to an input connection
 * @author toddstennes
 *
 */
public class Wire extends CircuitElement implements Deletable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constant that represents how curved the wire is by specifying how far the second and third points on the bezier curve are from 
	 * the first and fourth points, respectively
	 */
	private final int curveFactor = 6;
	
	/**
	 * The boolean state of the wire
	 */
	private boolean signal;
	
	/**
	 * The path that the wire follows in the CircuitPanel
	 */
	private CubicCurve2D curve;
	
	/**
	 * The two connections that the wire is attached to
	 */
	private Connection source, dest;
	
	@Override
	public void render(Graphics g, CircuitPanel cp) {
		Connection connectOne;
		Connection connectTwo;
		if(dest != null && source == null) {
			connectOne = dest;
			connectTwo = source;
		}
		else {
			connectOne = source;
			connectTwo = dest;
		}
		if(connectOne != null) {
			Point p1 = connectOne.getCoord();
			Point p4;
			if(connectTwo != null) p4 = connectTwo.getCoord();
			else p4 = cp.getEditor().getWireBuilder().getMousePoint();
			
			int offset = (int) (Math.sqrt(calculateDist(p1, p4)) * curveFactor);
			Point p2 = offsetInDirection(p1, offset, connectOne.getAbsoluteDirection());
			
			Point p3;
			if(connectTwo == null) p3 = cp.getEditor().getWireBuilder().getMousePoint();
			else p3 = offsetInDirection(p4, offset, connectTwo.getAbsoluteDirection());
			drawCurve(g, p1, p2, p3, p4);
		}
	}
	
	/**
	 * Draws a curve using the specified points as control points
	 * @param g The Graphics object
	 * @param p1 The first control point (start of curve)
	 * @param p2 The second control point (changes shape)
	 * @param p3 The third control point (changes shape)
	 * @param p4 The fourth control point (end of curve)
	 */
	private void drawCurve(Graphics g, Point p1, Point p2, Point p3, Point p4) {
		curve = new CubicCurve2D.Double(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);
		Graphics2D g2d = (Graphics2D) g;
		if(selected) {
			g2d.setColor(Selection.SELECT_COLOR);
			g2d.setStroke(new BasicStroke(10));
		}
		else {
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(7));
		}
		
		g2d.draw(curve);
		if(signal) g2d.setColor(Color.ORANGE);
		else g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(3));
		g2d.draw(curve);
		g2d.setStroke(new BasicStroke(1));
	}
	
	/**
	 * Calculates the distance between the two points
	 * @param p1 The first point
	 * @param p2 The second point
	 * @return The Euclidian distance between the points in double precision
	 */
	private double calculateDist(Point p1, Point p2) {
		int dx = p1.x - p2.x;
		int dy = p1.y - p2.y;
		double dist = Math.sqrt(dx * dx + dy * dy);
		return dist;
	}
	
	/**
	 * Returns the point that is the specified distance away from the given point in the given CompRotator direction
	 * @param p The original point
	 * @param offset The offset to apply
	 * @param direction The direction of the offset
	 * @return
	 */
	private Point offsetInDirection(Point p, int offset, int direction) {
		if(direction == CompRotator.UP) return new Point(p.x, p.y - offset);
		else if(direction == CompRotator.RIGHT) return new Point(p.x + offset, p.y);
		else if(direction == CompRotator.DOWN) return new Point(p.x, p.y + offset);
		else if(direction == CompRotator.LEFT) return new Point(p.x - offset, p.y);
		return new Point(p);
	}
	
	/**
	 * Returns the wire's signal
	 * @return The wire's signal
	 */
	public synchronized boolean getSignal() {
		return signal;
	}
	
	/**
	 * Changes the wire's signal. This method is safe to call from the EDT and LogicWorker thread
	 * @param signal The new boolean signal
	 */
	public synchronized void setSignal(boolean signal) {
		this.signal = signal;
	}
	
	/**
	 * Returns the Connection that can set this wire's signal
	 * @return The source connection
	 */
	public Connection getSourceConnection() {
		return source;
	}
	
	/**
	 * Returns the Connection that uses this wire's signal as input
	 * @return The destination connection
	 */
	public Connection getDestConnection() {
		return dest;
	}
	
	/**
	 * Sets a null connection to the given connection, if one or both connections are null
	 * @param connect The connection to add
	 */
	public void fillConnection(Connection connect) {
		if(source == null && connect.getType() == Connection.OUTPUT) source = connect;
		else if(dest == null && connect.getType() == Connection.INPUT) dest = connect;
	}
	
	/**
	 * Returns the curve last drawn by this wire
	 * @return The wire's curve
	 */
	public CubicCurve2D getCurve() {
		return curve;
	}
	
	/**
	 * Tells whether the wire is complete (when it has two references to Connections)
	 * @return The completion state of the wire
	 */
	public boolean isComplete() {
		return source != null && dest != null;
	}
	
	@Override
	public void delete() {
		if(source != null) {
			source.removeWire(this);
			source = null;
		}
		if(dest != null) {
			dest.removeWire(this);
			dest = null;
		}
	}
}
