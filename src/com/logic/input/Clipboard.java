package com.logic.input;

import java.awt.Point;
import java.util.ArrayList;

import com.logic.components.LComponent;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;
import com.logic.util.CompUtils;

/**
 * A class for handling copying and pasting of LComponents
 * @author toddstennes
 *
 */
public class Clipboard {
	
	/**
	 * The value that is added to the x and y positions of each LComponent when a selection pasted
	 */
	private static final int COPY_OFFSET = 100;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The CircuitEditor
	 */
	private CircuitEditor editor;
	
	/**
	 * The RevisionManager
	 */
	private RevisionManager revision;
	
	/**
	 * The data currently on the clip board
	 */
	private ArrayList<LComponent> copy;

	/**
	 * Constructs a new Clipboard
	 * @param cp The CircuitPanel
	 * @param editor The CircuitEditor
	 * @param revision The RevisionManager
	 */
	public Clipboard(CircuitPanel cp, CircuitEditor editor, RevisionManager revision) {
		this.cp = cp;
		this.editor = editor;
		this.revision = revision;
	}
	
	/**
	 * Saves the given selection to the clipboard
	 * @param selection The selection to copy
	 */
	public void copy(ArrayList<LComponent> selection) {	
		if(selection.size() > 0) copy = CompUtils.duplicate(selection);
	}
	 
	/**
	 * Makes a duplicate of the data that is currently on the clip board and adds the LComponents to the CircuitPanel
	 */
	public void paste() {
		if(copy != null) {
			ArrayList<LComponent> compsToPaste = CompUtils.duplicate(copy, new Point(COPY_OFFSET, COPY_OFFSET));
			cp.addLComps(compsToPaste);
			editor.getSelection().select(compsToPaste);
		}
		revision.saveState(new CircuitState(cp));
		LogicWorker.startLogic(cp);
	}
	
}
