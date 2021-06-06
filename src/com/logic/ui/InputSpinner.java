package com.logic.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		/*
		if(selection.size() == 1) {
			LComponent lcomp = selection.get(0);
			IOManager io = lcomp.getIO();
			if(io.isInputFlexible()) {
				int value = (int) spinner.getValue();
				int maxInputs = io.getMaxInputs();
				int minInputs = io.getMinInputs();
				if(value > maxInputs) value = maxInputs;
				else if(value < minInputs) value = minInputs;
				spinner.setValue(value);
				if(value > lcomp.getIO().getNumInputs()) {
					lcomp.increaseInputs();
					cp.getEditor().getRevision().saveState(new CircuitState(cp));
				}
				else if(value < lcomp.getIO().getNumInputs()) {
					lcomp.decreaseInputs();
					cp.getEditor().getRevision().saveState(new CircuitState(cp));
				}
				cp.repaint();
			}
		}
		*/
		//TODO implement input spinner
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
