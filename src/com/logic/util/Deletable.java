package com.logic.util;

/**
 * An interface that specifies any object which must perform an action at the time after which it will not be used
 * @author toddstennes
 *
 */
public interface Deletable {

	/**
	 * Removes this object from the program so that it will no longer influence any other objects.  Methods should not be called on this
	 * object after delete() is called as they will have unpredictable results.
	 */
	public abstract void delete();
	
}
