package com.logic.input;

import com.logic.components.Connection;
import com.logic.components.Wire;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;

/**
 * This class manages the selecting and deleting of wires. There is currently only support for selection one wire at a time
 * @author toddstennes
 *
 */
public class WireEditor {

	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The wire that is currently selected
	 */
	private Wire selectedWire;
	
	/**
	 * Constructs a new WireEditor
	 * @param cp The CircuitPanel
	 */
	public WireEditor(CircuitPanel cp) {
		this.cp = cp;
	}
	
	/**
	 * Selects the given wire
	 * @param wire The wire to select
	 */
	public void select(Wire wire) {
		if(selectedWire != null) selectedWire.setSelected(false);
		wire.setSelected(true);
		selectedWire = wire;
	}
	
	/**
	 * Deletes the selected wire, if there is one, and updates the logic of the circuit accordingly
	 */
	public void deleteWire() {
		Connection dest = selectedWire.getDestConnection();
		if(dest != null) LogicWorker.startLogic(dest.getLcomp());
		if(selectedWire != null) {
			selectedWire.delete();
			cp.removeWire(selectedWire);
			selectedWire = null;
		}
		cp.repaint();
	}
	
	/**
	 * Deselects the selected wire, if there is one
	 */
	public void clear() {
		if(selectedWire != null) {
			selectedWire.setSelected(false);
			selectedWire = null;
		}
	}
	
	/**
	 * Tells whether there is a selected wire
	 * @return A boolean telling whether there is a selected wire
	 */
	public boolean hasSelectedWire() {
		return selectedWire != null;
	}
	
}
