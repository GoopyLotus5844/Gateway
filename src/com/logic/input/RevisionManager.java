package com.logic.input;

import java.util.ArrayList;

/**
 * A class for handling undo and redo functions
 * What counts as an action that can be undone:
 * Paste of a nonempty clip board
 * Insert of a component through the use of the insert panel
 * The completion of a wire
 * The dragging of a selection from one place to another
 * The rotation of a component
 * The adding or subtracting of inputs from a component
 * The deletion of a component
 * 
 * @author toddstennes
 *
 */
public class RevisionManager {

	/**
	 * The maximum number of states that will be saved
	 */
	private final int maxStates = 20; 
	
	/**
	 * The list of states of the CircuitPanel that can be restored
	 */
	private ArrayList<CircuitState> states;
	
	/**
	 * The index of the current state
	 */
	private int index = -1;
	
	/**
	 * The CircuitEditor
	 */
	private CircuitEditor editor;
	
	/**
	 * Constructs a new RevisionManager
	 * @param editor The CircuitEditor, used for checking if the editor is enabled
	 */
	public RevisionManager(CircuitEditor editor) {
		states = new ArrayList<CircuitState>(maxStates);
		this.editor = editor;
	}
	
	/**
	 * Saves the state of the CircuitPanel so that it can be restored if the user uses the undo or redo function
	 * @param state The CircuitState to save
	 */
	public void saveState(CircuitState state) {
		//Called by Clipboard.paste, Inserter.attemptInsert, WireBuilder.endWire, Selection.delete, RotationSpinner.stateChanged, 
		//InputSpinner.stateChanged, CircuitEditor.mouseReleased, FileManager.openFile
		for(int i = states.size() - 1; i > index; i--) {
			states.remove(i);
		}
		states.add(state);
		index++;
		removeOld();
	}
	
	/**
	 * Clears the states list so that undo and redo cannot be performed
	 */
	public void clearStates() {
		index = -1;
		states.clear();
	}
	
	/**
	 * Undoes the last action in the ArrayList of CircuitStates
	 */
	public void undo() {
		if(editor.isEnabled() && index > 0) {
			states.get(index - 1).revertState();
			index--;
		}
	}
	
	/**
	 * Redoes the last undone action in the ArrayList of CircuitStates
	 */
	public void redo() {
		if(editor.isEnabled() && index < states.size() - 1) {
			states.get(index + 1).revertState();
			index++;
		}
	}
	
	/**
	 * Removes the oldest CircuitStates to keep the size of the ArrayList within the maxStates range
	 */
	private void removeOld() {
		while(states.size() > maxStates) {
			states.remove(0);
			index--;
		}
	}
}
