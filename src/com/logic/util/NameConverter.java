package com.logic.util;

import com.logic.components.CompType;
import com.logic.ui.CompRotator;

/**
 * A class that holds static helper methods for converting between different ways of expressing values (a lot of else if statements all 
 * dumped in one place)
 * @author toddstennes
 *
 */
public class NameConverter {

	/**
	 * Converts from CompType to conventionally used component name (ex: CompType.AND -> "And gate")
	 * @param type The CompType
	 * @return The name
	 */
	public static String nameFromType(CompType type) {
		if(type == CompType.AND) return "And gate";
		else if(type == CompType.OR) return "Or gate";
		else if(type == CompType.NOT) return "Not gate";
		else if(type == CompType.NAND) return "Nand gate";
		else if(type == CompType.NOR) return "Nor gate";
		else if(type == CompType.XOR) return "Xor gate";
		else if(type == CompType.XNOR) return "Xnor gate";
		else if(type == CompType.BUFFER) return "Buffer";
		else if(type == CompType.SWITCH) return "Switch";
		else if(type == CompType.LIGHT) return "Light";
		else if(type == CompType.CLOCK) return "Clock";
		else if(type == CompType.BUTTON) return "Button";
		else if(type == CompType.ZERO) return "Zero constant";
		else if(type == CompType.ONE) return "One constant";
		else if(type == CompType.DISPLAY) return "4-bit display";
		else if(type == CompType.CUSTOM) return "Custom component";
		return "Unknown name";
	}
	
	/**
	 * Converts from a CompRotator rotation constant to the name of the direction (ex: CompRotator.RIGHT, "Right")
	 * @param rotation The CompRotator rotation constant
	 * @return The name of the rotation
	 */
	public static String rotationFromValue(int rotation) {
		if(rotation == CompRotator.RIGHT) return "Right";
		else if(rotation == CompRotator.DOWN) return "Down";
		else if(rotation == CompRotator.LEFT) return "Left";
		else if(rotation == CompRotator.UP) return "Up";
		return "Invalid rotation";
	}
	
	/**
	 * Converts from the name of a direction to the CompRotator rotation constant (ex: "Right", CompRotator.RIGHT)
	 * @param name The name of the rotation
	 * @return The CompRotator rotation constant
	 */
	public static int rotationFromName(String name) {
		if(name.equals("Down")) return CompRotator.DOWN;
		else if(name.equals("Left")) return CompRotator.LEFT;
		else if(name.equals("Up")) return CompRotator.UP;
		else if(name.equals("Right")) return CompRotator.RIGHT;
		return -1;
	}
	
	/**
	 * Converts from a boolean signal value to the name of that signal (true -> "High", false -> "Low")
	 * @param signal The boolean signal
	 * @return The name of the signal
	 */
	public static String nameFromSignal(boolean signal) {
		if(signal) return "High";
		return "Low";
	}
	
}
