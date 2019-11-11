package com.logic.util;

import java.util.HashMap;

/**
 * Used for performance testing
 * @author toddstennes
 *
 */
public class Debug {

	/**
	 * A list of times that each debug entry was started and a name for each entry
	 */
	private static HashMap<String, Long> times = new HashMap<String, Long>();
	
	/**
	 * Starts a new debug entry
	 * @param name The name of the entry
	 */
	public static void start(String name) {
		times.put(name, System.currentTimeMillis());
	}
	
	/**
	 * Ends the debug entry with the given name and prints the time between the start and end
	 * @param name The name of the entry to end
	 */
	public static void end(String name) {
		System.out.println(name + ": " + (System.currentTimeMillis() - times.get(name)) + "ms");
		times.remove(name);	
	}

}
