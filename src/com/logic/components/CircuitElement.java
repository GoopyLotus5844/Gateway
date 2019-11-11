package com.logic.components;

import java.awt.Graphics;

import com.logic.ui.CircuitPanel;

/**
 * The superclass for all objects that can be added to the CircuitPanel in creating a circuit
 * @author toddstennes
 *
 */
public abstract class CircuitElement {

	/**
	 * Tells if the element is in a selected state, which usually influences how it is rendered and how it can be manipulated by the user
	 */
	protected boolean selected;
	
	/**
	 * Creates a graphical representation of the element in the CircuitPanel
	 * @param g The Graphics object to use
	 * @param cp The CircuitPanel that is rendering this LComponent
	 */
	public abstract void render(Graphics g, CircuitPanel cp);
	
	/**
	 * Tells if the element is selected
	 * @return The selected state of the element
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Sets the selected state of the element
	 * @param selected The selected state to put the element in
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
