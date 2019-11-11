package com.logic.util;

import java.util.ArrayList;

import com.logic.components.LComponent;
import com.logic.components.Switch;

/**
 * A CustomNode for managing an input
 * @author toddstennes
 *
 */
public class CustomInput extends CustomNode {

	private static final long serialVersionUID = 1L;

	/**
	 * The state that was given to this CustomInput the last time addIfNecessary(...) was called
	 */
	private boolean prevState;
	
	/**
	 * The internal Switch that corresponds to an input to the Custom component
	 */
	private Switch sw;
	
	/**
	 * Constructs a new custom input by calling the super constructor and saving the given switch
	 * @param sw The internal Switch
	 */
	public CustomInput(Switch sw) {
		super(sw);
		this.sw = sw;
		prevState = sw.getState();
	}
	
	/**
	 * This method adds this CustomInput's Switch to the given list if the state is different than the previous state
	 * @param state The state of the input that corresponds to this CustomInput
	 * @param startingComps The list of components that will be run through a LogicEngine to update the Custom component
	 */
	public void addIfNecessary(boolean state, ArrayList<LComponent> startingComps) {
		if(state != prevState) {
			sw.setState(state);
			startingComps.add(sw);
			prevState = state;
		}
	}
	
}
