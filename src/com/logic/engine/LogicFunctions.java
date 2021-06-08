package com.logic.engine;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Defines all Functions that can be used by LComponents in their update() methods. 
 * @author toddstennes
 *
 */
public class LogicFunctions {
	
	/**
	 * The list of all functions that accept one boolean input. See setFunctions() for info.
	 */
	public static ArrayList<Function<Boolean, Boolean>> func1s = new ArrayList<Function<Boolean, Boolean>>();
	
	/**
	 * The list of all functions that accept two boolean inputs. See setFunctions() for info.
	 */
	public static ArrayList<BiFunction<Boolean, Boolean, Boolean>> func2s = new ArrayList<BiFunction<Boolean, Boolean, Boolean>>();
	
	/**
	 * Initializes all logic functions. Single input functions are loaded in the order NOT, BUFFER, and multi-input functions are loaded in 
	 * the order AND, NAND, OR, NOR, XOR, XNOR. This is the order that the functions appear in their respective arrays.
	 */
	public static void setFunctions() {
		func1s.add(a -> !a);
		func1s.add(a -> a);
		func2s.add((a, b) -> a & b);
		func2s.add((a, b) -> a | b);
		func2s.add((a, b) -> a ^ b);
	}
}
