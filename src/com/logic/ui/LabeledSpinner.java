package com.logic.ui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import com.logic.util.GraphicsUtils;

/**
 * A JPanel that holds a JSpinner and a JLabel
 * @author toddstennes
 *
 */
public class LabeledSpinner extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The JLabel
	 */
	private JLabel label;
	
	/**
	 * The JSpinner
	 */
	protected JSpinner spinner;
	
	/**
	 * Constructs a new LabeledSpinner
	 * @param labelText The text of the label
	 */
	public LabeledSpinner(String labelText) {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setVgap(0);
		layout.setHgap(0);
		setLayout(layout);
		
		spinner = new JSpinner();
		
		label = new JLabel(labelText);
		GraphicsUtils.makeBold(label);
		label.setLabelFor(spinner);
		
		add(label);
		add(spinner);
	}
	
	/**
	 * Returns the JSpinner
	 * @return the JSpinner
	 */
	public JSpinner getSpinner() {
		return spinner;
	}

}
