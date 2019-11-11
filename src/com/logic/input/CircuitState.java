package com.logic.input;

import java.util.ArrayList;

import com.logic.components.LComponent;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;
import com.logic.util.CompUtils;

/**
 * This class saves the CircuitPanel to be used for undo and redo functions
 * @author toddstennes
 *
 */
public class CircuitState {

	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The LComponents returned by CompCloner.duplicate(...)
	 */
	private ArrayList<LComponent> lcomps;
	
	/**
	 * Constructs a new CircuitState, which serves as a snapshot of the CircuitPanel at the time of construction
	 * @param cp The CircuitPanel
	 */
	public CircuitState(CircuitPanel cp) {
		lcomps = CompUtils.duplicate(cp.lcomps);
		this.cp = cp;
	}
	
	/**
	 * Changes the CircuitPanel back to the state it was in when this CircuitState was created
	 */
	public void revertState() { 
		for(int i = 0; i < cp.lcomps.size(); i++) cp.lcomps.get(i).delete();
		cp.lcomps.clear();
		cp.wires.clear(); 
		cp.getEditor().getSelection().clear();
		cp.addLComps(CompUtils.duplicate(lcomps));
		LogicWorker.startLogic(cp);
		cp.repaint();
	}

	/**
	 * Returns the LComponents array
	 * @return The array of LComponents
	 */
	public ArrayList<LComponent> getLcomps() {
		return lcomps;
	}
}
