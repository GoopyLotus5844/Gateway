package com.logic.util;

import java.awt.Point;

import com.logic.ui.CompRotator;

/**
 * Expresses a certain layout of input or output connections for an IOManager
 * @author toddstennes
 *
 */
public class ConnectionLayout {

	/**
	 * The points of the connections
	 */
	private Point[] layout;
	
	/**
	 * The directions of the connections
	 */
	private int[] directions;
	
	/**
	 * The type of connection that this ConnectionLayout refers to
	 */
	private int type;
	
	/**
	 * Constructs a new ConnectionLayout where components can be facing different directions based on the direction array 
	 * @param layout The points that each connection is to be placed at
	 * @param directions The directions of each connection, in the same order as the points
	 * @param type The type of connection that this ConnectionLayout will refer to
	 */
	public ConnectionLayout(Point[] layout, int[] directions, int type) {
		this.layout = layout;
		this.directions = directions;
		this.type = type;
	}
	
	/**
	 * Constructs a new ConnectionLayout where all connections are facing the same direction
	 * @param layout The points that each connection is to be placed at
	 * @param direction The direction that every connection will be facing
	 * @param type The type of connection that this ConnectionLayout will refer to
	 */
	public ConnectionLayout(Point[] layout, int direction, int type) {
		this.layout = layout;
		this.type = type;
		directions = new int[layout.length];
		for(int i = 0; i < directions.length; i++) {
			directions[i] = direction;
		}
	}
	
	/**
	 * Returns the point of the connection at the specified index
	 * @param index The index in the array of points
	 * @return The point of the connection at the specified index
	 */
	public Point getPoint(int index) {
		if(index >= 0 && index < layout.length) return layout[index];
		return null;
	}
	
	/**
	 * Returns the direction of the connection at the specified index
	 * @param index The index in the array of directions (which corresponds in order to the array of points)
	 * @return The direction of the connection at the specified index
	 */
	public int getDirection(int index) {
		if(index >= 0 && index < layout.length) return directions[index];
		return CompRotator.RIGHT;
	}
	
	/**
	 * Returns the number of connections that this ConnectionLayout refers to
	 * @return The number of connections
	 */
	public int getNumConnections() {
		return layout.length;
	}
	
	/**
	 * Returns the type of connection that this ConnectionLayout refers to
	 * @return The type of connection
	 */
	public int getType() {
		return type;
	}
	
}
