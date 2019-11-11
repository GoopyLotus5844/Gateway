package com.logic.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.logic.components.Clock;
import com.logic.components.LComponent;
import com.logic.input.Selection;

/**
 * A LabeledSpinner subclass for editing the delay value of a Clock
 * @author toddstennes
 *
 */
public class DelaySpinner extends LabeledSpinner implements ChangeListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The minimum delay value that a clock can be set to
	 */
	private final int min = 50;
	
	/**
	 * The maximum delay value that a clock can be set to
	 */
	private final int max = 5000;
	
	/**
	 * The amount by which the delay changes when pressing the up or down button on the spinner
	 */
	private final int step = 50;
	
	/**
	 * The selection
	 */
	private Selection selection;
	
	/**
	 * Constructs a new DelaySpinner with built in padding so that it looks nice with other JComponents
	 */
	public DelaySpinner() {
		super("Delay (ms): ");
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(0);
		layout.setVgap(10);
		setLayout(layout);
		SpinnerNumberModel model = new SpinnerNumberModel(Clock.DEFAULT_DELAY, min, max, step);
		spinner.setModel(model);
		spinner.addChangeListener(this);
		spinner.setPreferredSize(new Dimension(65, 20));
	}
	
	/**
	 * Sets the delay of a clock to the value currently shown by this spinner if a single clock is selected
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(selection.size() == 1) {
			LComponent lcomp = selection.get(0);
			if(lcomp instanceof Clock) ((Clock) lcomp).setDelay((int) spinner.getValue());
		}
	}
	
	/**
	 * Sets the displayed value of this LabeledSpinner to the given value so that it matches a clock
	 * @param val The value to display
	 */
	public void setDelayText(int val) {
		spinner.setValue(val);
	}
	
	/**
	 * Updates this DelaySpinner's reference to the selection using the given CircuitPanel
	 * @param cp The CircuitPanel
	 */
	public void setCircuitPanel(CircuitPanel cp) {
		selection = cp.getEditor().getSelection();
	}
	
}
