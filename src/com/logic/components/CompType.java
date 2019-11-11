package com.logic.components;

/**
 * An enumeration of all of the types of components in Gateway
 * @author toddstennes
 *
 */
public enum CompType {

	AND(),
	OR(),
	NOT(),
	NAND(),
	NOR(),
	XOR(),
	XNOR(),
	BUFFER(),
	SWITCH(),
	LIGHT(),
	CLOCK(),
	BUTTON(),
	ZERO(),
	ONE(),
	DISPLAY(),
	CUSTOM();
	
}
