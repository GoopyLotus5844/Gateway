package com.logic.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.logic.input.CircuitState;
import com.logic.input.Selection;
import com.logic.util.NameConverter;

/**
 * A LabeledSpinner designed for editing the rotation of an LComponent
 * @author toddstennes
 *
 */
public class RotationSpinner extends LabeledSpinner implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The strings representing each rotation direction
	 */
	private final String[] directionStrings = new String[] {"Right", "Down", "Left", "Up"};
	
	/**
	 * The Selection
	 */
	private Selection selection;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * Constructs a new RotationSpinner
	 */
	public RotationSpinner() {
		super("Rotation: ");
		spinner.setModel(new CyclingSpinnerListModel(directionStrings));
		spinner.addChangeListener(this);
	}

	/**
	 * Rotates the selected component to match the rotation displayed on the spinner
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(selection.size() == 1) {
			selection.get(0).getRotator().setRotation(NameConverter.rotationFromName((String) spinner.getValue()));
			cp.repaint();
			cp.getEditor().getRevision().saveState(new CircuitState(cp));
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
