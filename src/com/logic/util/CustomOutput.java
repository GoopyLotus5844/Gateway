package com.logic.util;

import com.logic.components.Light;

/**
 * A CustomNode subclass for managing an output
 * @author toddstennes
 *
 */
public class CustomOutput extends CustomNode {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The internal light that corresponds to an output in the Custom component
	 */
	private Light lt;
	
	/**
	 * Constructs a new CustomOutput by calling the super constructor and saving the given Light
	 * @param lt The internal Light
	 */
	public CustomOutput(Light lt) {
		super(lt);
		this.lt = lt;
	}
	
	/**
	 * Returns the state of the light, which should be set as the output of the corresponding connection in the CustomComponent
	 * @return The state of the light (its input at index 0)
	 */
	public boolean getState() {
		return lt.getIO().getInput(0);
	}
	
}
