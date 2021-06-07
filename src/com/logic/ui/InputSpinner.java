package com.logic.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.logic.components.BasicGate;
import com.logic.components.IOManager;
import com.logic.components.LComponent;
import com.logic.input.CircuitState;
import com.logic.input.Selection;

/**
 * A spinner for changing the number of inputs of a component
 * @author toddstennes
 *
 */
public class InputSpinner extends LabeledSpinner implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The Selection
	 */
	private Selection selection;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * Constructs a new InputSpinner
	 */
	public InputSpinner() {
		super("Inputs: ");
		spinner.addChangeListener(this);
	}

	/**
	 * Changes the number of inputs of the selected LComponent
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(selection.size() == 1){
			LComponent lcomp = selection.get(0);
			if(lcomp instanceof BasicGate) {
				BasicGate gate = (BasicGate) lcomp;
				int value = (int) spinner.getValue();
				if(value > BasicGate.MAX_INPUTS) value = BasicGate.MAX_INPUTS;
				else if(value < BasicGate.MIN_INPUTS) value = BasicGate.MIN_INPUTS;
				spinner.setValue(value);
				gate.setNumInputs(value);
				cp.getEditor().getRevision().saveState(new CircuitState(cp));
				cp.repaint();
			}
		}
	}

	/**
	 * Sets the CircuitPanel
	 * @param cp The CircuitPanel
	 */
	public void setCircuitPanel(CircuitPanel cp) {
		this.cp = cp;
		selection = cp.getEditor().getSelection();
	}

}
