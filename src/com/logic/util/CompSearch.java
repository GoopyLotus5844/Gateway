package com.logic.util;

import java.awt.Point;
import java.awt.geom.CubicCurve2D;

import com.logic.components.Connection;
import com.logic.components.IComponent;
import com.logic.components.LComponent;
import com.logic.components.Wire;
import com.logic.ui.CircuitPanel;

/**
 * A class for searching through all of the components in the CircuitPanel to determine what a mouse press is touching
 * @author toddstennes
 *
 */
public class CompSearch {

	/**
	 * Used when the mouse is touching a clickAction that is defined in an IComponent
	 */
	public static int TOUCHING_ACTION = 3;
	
	/**
	 * Used when the mouse is touching the bounds of a component but not a clickAction
	 */
	public static int TOUCHING_COMPONENT = 2;
	
	/**
	 * Used when the mouse is touching a wire
	 */
	public static int TOUCHING_WIRE = 4;
	
	/**
	 * Used when the mouse is touching a connection
	 */
	public static int TOUCHING_CONNECTION = 1;
	
	/**
	 * Used when none of the other constants apply
	 */
	public static int CLEAR = 0;
	
	/**
	 * The radius around the mouse that can intersect a wire to return a TOUCHING_WIRE value
	 */
	private final int wireDetectRadius = 3;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The LComponent that was touched if a TOUCHING_COMPONENT or TOUCHING_ACTION value was returned
	 */
	private LComponent lcomp;
	
	/**
	 * The Wire that was touched if TOUCHING_WIRE was returned
	 */
	private Wire wire;
	
	/**
	 * The Connection that was touched if TOUCHING_CONNECTION was returned
	 */
	private Connection connection;
	
	/**
	 * Constructs a new CompSearch
	 * @param cp The CircuitPanel
	 */
	public CompSearch(CircuitPanel cp) {
		this.cp = cp;
	}
	
	/**
	 * Searches through all LComponents, wires, and connections to determine what the given point is touching
	 * @param coord The coordinate of the mouse in the CircuitPanel coordinate system (meaning after withTransform(...) has been called)
	 * @return An integer specifying what the coordinate is touching
	 */
	public int search(Point coord) {
		lcomp = null;
		connection = null;
		wire = null;
		for(int i = 0; i < cp.lcomps.size(); i++) {
			LComponent tempComp = cp.lcomps.get(i);
			Connection tempConnection = tempComp.getIO().connectionAt(coord);
			if(tempComp instanceof IComponent && ((IComponent) tempComp).getClickActionBounds().contains(coord)) {
				lcomp = tempComp;
				return TOUCHING_ACTION;
			}
			if(tempComp.getBounds().contains(coord) && tempConnection == null) {
				lcomp = tempComp;
				return TOUCHING_COMPONENT;
			}
			else if(tempConnection != null) {
				connection = tempConnection;
				return TOUCHING_CONNECTION;
			}
		}
		for(int i = 0; i < cp.wires.size(); i++) {
			Wire tempWire = cp.wires.get(i);
			CubicCurve2D tempCurve = tempWire.getCurve();
			if(tempCurve != null && tempCurve.intersects(coord.x - wireDetectRadius, coord.y - wireDetectRadius, 
					2 * wireDetectRadius, 2 * wireDetectRadius)) {
				wire = tempWire;
				return TOUCHING_WIRE;
			}
		}
		return CLEAR;
	}
	
	/**
	 * Returns the LComponent that was touched if TOUCHING_COMPONENT or TOUCHING_ACTION was returned by search, null otherwise
	 * @return The LComponent
	 */
	public LComponent getLComp() {
		return lcomp;
	}
	
	/**
	 * Returns the Wire that was touched if TOUCHING_WIRE was returned by search, null otherwise
	 * @return The Wire
	 */
	public Wire getWire() {
		return wire;
	}
	
	/**
	 * Returns the Connection that was touched if TOUCHING_CONNECTION was returned by search, null otherwise
	 * @return The Connection
	 */
	public Connection getConnection() {
		return connection;
	}
}
