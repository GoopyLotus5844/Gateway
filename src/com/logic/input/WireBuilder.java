package com.logic.input;

import java.awt.Point;

import com.logic.components.Connection;
import com.logic.components.Wire;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;

/**
 * Manages construction of wires by the user
 * @author toddstennes
 *
 */
public class WireBuilder {

	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The RevisionManager
	 */
	private RevisionManager revision;
	
	/**
	 * The wire that is currently being built
	 */
	private Wire workingWire;
	
	/**
	 * The current location of the mouse in the coordinate system of the CircuitPanel
	 */
	private Point mousePoint;
	
	/**
	 * The type of connection that workingWire was first attached to (Connection.INPUT or Connection.OUTPUT)
	 */
	private int prevConnectionType;
	
	/**
	 * Constructs a new WireBuilder
	 * @param cp The CircuitPanel
	 */
	public WireBuilder(CircuitPanel cp, RevisionManager revision) {
		this.cp = cp;
		this.revision = revision;
	}
	
	/**
	 * Starts building a wire that is connected to the given connection and appears to be connected to the mouse
	 * @param connection The Connection to connect the wire to
	 */
	public void startWire(Connection connection) {
		workingWire = new Wire();
		prevConnectionType = connection.getType();
		boolean successful = connection.addWire(workingWire);
		if(!successful) workingWire = null;
		else {
			cp.addWire(workingWire);
			LogicWorker.startLogic(connection.getLcomp());
		}
	}
	
	/**
	 * Completes the wire that is currently being built, if there is one
	 * @param connection The Connection to connect the wire to
	 */
	public void endWire(Connection connection) {
		if(workingWire != null && prevConnectionType != connection.getType()) {
			connection.addWire(workingWire);
			LogicWorker.startLogic(connection.getLcomp());
			workingWire = null;
			revision.saveState(new CircuitState(cp));
		}
	}
	
	/**
	 * Deletes the wire that is currently being built, if there is one
	 */
	public void cancelWire() {
		if(workingWire != null) {
			workingWire.delete();
			cp.removeWire(workingWire);
			workingWire = null;
		}
	}
	
	/**
	 * Returns the current location of the mouse in the CircuitPanel's coordinate space
	 * @return The current location of the mouse
	 */
	public Point getMousePoint() {
		return mousePoint;
	}
	
	/**
	 * Sets the stored mouse location that a working wire will be attached to
	 * @param mousePoint The new mousePoint
	 */
	public void setMousePoint(Point mousePoint) {
		this.mousePoint = mousePoint;
	}
	
	/**
	 * Tells whether a wire is currently being built
	 * @return A boolean that tells whether a wire is being built
	 */
	public boolean isWorking() {
		return workingWire != null;
	}
	
}
