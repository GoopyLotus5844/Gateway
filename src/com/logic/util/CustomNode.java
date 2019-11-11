package com.logic.util;

import java.io.Serializable;

import com.logic.components.LComponent;
import com.logic.components.Light;
import com.logic.components.Switch;

/**
 * A class that manages the internal information required for each connection that a custom component has
 * @author toddstennes
 *
 */
public class CustomNode implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The string that is set as the label for this CustomNode if the given LComponent is not a Light or a Switch
	 */
	private final String errorString = "error";

	/**
	 * The label that should be displayed on the connection of a CustomComponent that corresponds to this CustomNode
	 */
	private String label;
	
	/**
	 * Constructs a new custom node by setting its label according to the type and name of the given component. If the component has a name
	 * other than "Untitled component", then its name is set as the label. Otherwise, the label is set to "in" if the component is a light
	 * or to "out" if the component is a Switch. If the component is neither a Light nor a Switch, then the label is set to the error string.
	 * @param lcomp
	 */
	public CustomNode(LComponent lcomp) {
		String name = lcomp.getName();
		if(!name.equals("Untitled component")) label = name;
		else if(lcomp instanceof Switch) label = "in";
		else if(lcomp instanceof Light) label = "out";
		else label = errorString;
	}
	
	/**
	 * Returns the label for this CustomNode
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
}
