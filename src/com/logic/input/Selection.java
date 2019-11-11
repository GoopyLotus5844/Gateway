package com.logic.input;

import java.awt.Color;
import java.util.ArrayList;

import com.logic.components.LComponent;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompProperties;
import com.logic.util.CompUtils;

/**
 * This class manages selected components and provides means for manipulating them
 * @author toddstennes
 *
 */
public class Selection extends ArrayList<LComponent> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The Color of the box around a component when it is selected
	 */
	public static final Color SELECT_COLOR = new Color(66, 82, 255);
	
	/**
	 * Position values necessary for the dragging algorithm when snapping is enabled
	 */
	private int snapX, snapY;
	
	/**
	 * Position values necessary for deciding weather to save a CircuitState when mouseReleased is called in the CircuitEditor
	 */
	private int displacementX, displacementY;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The CircuitEditor
	 */
	private CircuitEditor editor;
	
	/**
	 * The Clipboard
	 */
	private Clipboard clipboard;
	
	/**
	 * The CompProperties
	 */
	private CompProperties compProperties;
	
	/**
	 * The RevisionManager
	 */
	private RevisionManager revision;
	
	/**
	 * Constructs a new Selection
	 * @param cp The CircuitPanel
	 * @param editor The CircuitEditor
	 * @param clipboard The Clipboard 
	 * @param compProperties The CompProperties
	 */
	public Selection(CircuitPanel cp, CircuitEditor editor, Clipboard clipboard, CompProperties compProperties, RevisionManager revision) {
		this.cp = cp;
		this.editor = editor;
		this.clipboard = clipboard;
		this.compProperties = compProperties;
		this.revision = revision;
	}
	
	/**
	 * Selects the given component. DO NOT EVER EVER EVER call this method in a loop, because it refreshes CompProperties every time it is 
	 * called, which is very slow.  If more than one component is being selected, use select(ArrayList<LComponent> lcomps)
	 * @param lcomp The LComponent to select
	 */
	public void select(LComponent lcomp) {
		lcomp.setSelected(true);
		add(lcomp);
		compProperties.refresh();
		displacementX = 0;
		displacementY = 0;
	}
	
	/**
	 * Selects all of the LComponents in the ArrayList, and refreshes CompProperties once
	 * @param lcomps The LComponents to select
	 */
	public void select(ArrayList<LComponent> lcomps) {
		for(int i = 0; i < lcomps.size(); i++) {
			LComponent lcomp = lcomps.get(i);
			lcomp.setSelected(true);
		}
		addAll(lcomps);
		compProperties.refresh();
		displacementX = 0;
		displacementY = 0;
	}

	/**
	 * Drags the selection so that its offset from its previous location is equal to the offset between the given point and the previous 
	 * mouse location
	 * @param newX The new x position
	 * @param newY The new y position
	 */
	public void drag(int newX, int newY) {
		int prevDragX = editor.getPrevMouse().x;
		int prevDragY = editor.getPrevMouse().y;
		if(size() > 0) {
			int moveX = 0; 
			int moveY = 0;
			if(editor.isSnap()) {
				snapX += newX - prevDragX;
				snapY += newY - prevDragY;		
				moveX = (snapX / CircuitEditor.SNAP_DIST) * CircuitEditor.SNAP_DIST;
				snapX -= (snapX / CircuitEditor.SNAP_DIST) * CircuitEditor.SNAP_DIST;
				moveY = (snapY / CircuitEditor.SNAP_DIST) * CircuitEditor.SNAP_DIST;
				snapY -= (snapY / CircuitEditor.SNAP_DIST) * CircuitEditor.SNAP_DIST;
			}
			else {
				moveX = newX - prevDragX;
				moveY = newY - prevDragY;
			}
			for(int i = 0; i < size(); i++) {
				LComponent lcomp = get(i);
				lcomp.setX(lcomp.getX() + moveX);
				lcomp.setY(lcomp.getY() + moveY);
			}
			displacementX += moveX;
			displacementY += moveY;
		}
	}
	
	/**
	 * Rotates all selected components in the given direction (CompRotator.CLOCKWISE or CompRotator.COUNTER_CLOCKWISE)
	 * @param direction The direction to rotate the components
	 */
	public void rotate(boolean direction) {
		CompUtils.rotateAll(this, direction);
		cp.repaint();
		compProperties.refresh();
	}
	
	/**
	 * Deletes all of the LComponents that are selected
	 */
	public void deleteComponents() {
		for(int i = 0; i < size(); i++) {
			LComponent lcomp = get(i);
			get(i).delete();
			cp.removeLComp(lcomp);
		}
		clear();
		revision.saveState(new CircuitState(cp));
		LogicWorker.startLogic(cp);
		cp.cleanWires();
		cp.repaint();
	}
	
	/**
	 * Copies the Selection to the Clipboard
	 */
	public void copy() {
		clipboard.copy(this);
	}
	
	/**
	 * Cuts the Selection by copying it and deleting it
	 */
	public void cut() {
		copy();
		deleteComponents();
		cp.repaint();
	}
	
	/**
	 * Clears this selection by de-selecting all selected components, calling ArrayList.clear(), and refreshing the CompProperties
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size(); i++) {
			get(i).setSelected(false);
		}
		super.clear();
		compProperties.refresh();
	}
	
	/**
	 * Returns the total amount by which the current selection has been displaced in the x direction through the drag(...) method. This value
	 * is reset to 0 if any components are added to the selection (but not when the selection is cleared).
	 * @return The displacement in the x direction
	 */
	public int getDisplacementX() {
		return displacementX;
	}
	
	/**
	 * Returns the total amount by which the current selection has been displaced in the y direction through the drag(...) method. This value
	 * is reset to 0 if any components are added to the selection (but not when the selection is cleared).
	 * @return The displacement in the y direction
	 */
	public int getDisplacementY() {
		return displacementY;
	}
}
