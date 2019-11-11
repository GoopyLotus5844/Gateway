package com.logic.ui;

import java.awt.Point;
import java.io.Serializable;

/**
 * This class is responsible for performing operations relating to rotating LComponents by 90 degrees
 * @author toddstennes
 *
 */
public class CompRotator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * A constant representing the right (default) direction
	 */
	public static final int RIGHT = 0;
	
	/**
	 * A constant representing the down direction
	 */
	public static final int DOWN = 1;
	
	/**
	 * A constant representing the left direction
	 */
	public static final int LEFT = 2;
	
	/**
	 * A constant representing the up direction
	 */
	public static final int UP = 3;
	
	/**
	 * A constant representing the clockwise rotation modification
	 */
	public static final boolean CLOCKWISE = true;
	
	/**
	 * A constant representing the counter clickwise rotation modification
	 */
	public static final boolean COUNTER_CLOCKWISE = false;
	
	/**
	 * The current rotation
	 */
	private int rotation;
	
	/**
	 * Constructs a new CompRotator
	 * @param cp The CircuitPanel 
	 */
	public CompRotator() {
		rotation = RIGHT; 
	}
	
	/**
	 * Calculates the location of the given point in a rectangle of the given dimensions if the Rectangle (which starts with a RIGHT rotation)
	 * is rotated to the specified rotation
	 * @param x The x position of the point
	 * @param y The y position of the point
	 * @param width The width of the rectangle that contains the point
	 * @param height The height of the rectangle that contains the point
	 * @param rotation The new rotation of the rectangle
	 * @return The rotated point
	 */
	public static Point withRotation(int x, int y, int width, int height, int rotation) {
		if(rotation == DOWN) return new Point(height - y - 1, x);
		else if(rotation == UP) return new Point(y, width - x - 1);
		else if(rotation == LEFT) return new Point(width - x - 1, height - y - 1);
		else return new Point(x, y);
	}
	
	/**
	 * Normalizes the given rotation to be an equivalent value that is between 0 and 360
	 * @param rotation The rotation to fix
	 * @return The normalized rotation
	 */
	public static int fixRotation(int rotation) {
		if(rotation < 0) rotation += 4 * (Math.abs(rotation) / 4 + 1);
		rotation %= 4;
		return rotation;
	}
	
	/**
	 * Returns the current rotation of the component
	 * @return The current rotation, expressed as one of the four rotation constants
	 */
	public int getRotation() {
		return rotation;
	}
	
	/**
	 * Sets the rotation of the component
	 * @param rotation The new rotation 
	 */
	public void setRotation(int rotation) {
		this.rotation = fixRotation(rotation);
	}
	
}
