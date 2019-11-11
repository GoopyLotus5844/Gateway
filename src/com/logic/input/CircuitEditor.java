package com.logic.input;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.logic.components.Custom;
import com.logic.components.IComponent;
import com.logic.components.LComponent;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompProperties;
import com.logic.ui.CustomCreator;
import com.logic.ui.CustomViewer;
import com.logic.ui.InsertPanel;
import com.logic.ui.LToolBar;
import com.logic.util.CompSearch;

/**
 * The main class for editing the circuit through mouse events
 * @author toddstennes
 *
 */
public class CircuitEditor extends MouseAdapter {
		
	/**
	 * The minimum mouse drag distance that is interpreted by this CircuitEditor as a drag instead of a click
	 */
	public static final int DRAG_THRESH = 2;
	
	/**
	 * The interval to which all LComponents snap to if snap is enabled
	 */
	public static final int SNAP_DIST = CircuitPanel.GRID_SPACING;
	
	/**
	 * The offset added to a component or set of components when they are copied and pasted (in the right and down directions)
	 */
	public static final int COPY_OFFSET = 100;
	
	/**
	 * A dragMode that shows that the selection is being dragged
	 */
	public static final int DRAGGING_SELECTION = 0;
	
	/**
	 * A dragMode that shows that a wire is being dragged
	 */
	public static final int DRAGGING_WIRE = 1;
	
	/**
	 * A dragMode that shows that the user is dragging the highlight rectangle
	 */
	public static final int DRAGGING_HIGHLIGHT = 2;
	
	/**
	 * A boolean that tells whether this CircuitEditor is currently enabled. If set to false, all methods that change the state of the 
	 * program will do nothing.
	 */
	private boolean enabled = true;
	
	/**
	 * Tells whether the program should snap components to the intervals defined by SNAP_DIST (true), or place components at any integer 
	 * location in the CircuitPanel (false)
	 */
	private boolean snap = false; 
	
	/**
	 * The action that should be taken if mouseDragged is called
	 */
	private int dragMode;
	
	/**
	 * The last recorded mouse position
	 */
	private Point prevMouse;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The LToolBar
	 */
	private LToolBar toolbar;
	
	/**
	 * The WireBuilder, which handles user creation of wires
	 */
	private WireBuilder wireBuilder;
	
	/**
	 * The WireEditor, which allows for selection and deletion of a single wire
	 */
	private WireEditor wireEditor;
	
	/**
	 * The Inserter, which handles the insertion of new components into the CircuitPanel
	 */
	private Inserter inserter;
	
	/**
	 * The Highlight, which is used for selecting groups of components
	 */
	private Highlight highlight;
	
	/**
	 * The Selection, which allows the user to select components in order to edit them 
	 */
	private Selection selection;
	
	/**
	 * The CustomCreator, which handles the UI for creating custom components
	 */
	private CustomCreator customCreator;
	
	/**
	 * The CustomViewer, which allows the user to see inside custom components
	 */
	private CustomViewer customViewer;
	
	/**
	 * The CompProperties, which displays settings for the selected component(s)
	 */
	private CompProperties properties;
	
	/**
	 * The Clipboard, which handles copying, cutting, and pasting of components
	 */
	private Clipboard clipboard;
	
	/**
	 * The RevisionManager, which handles undo and redo functions
	 */
	private RevisionManager revision;
	
	/**
	 * Constructs a new CircuitEditor
	 * @param cp The CircuitPanel
	 * @param properties The CompProperties
	 * @param toolbar The LToolBar
	 * @param insertPanel The InsertPanel
	 */
	public CircuitEditor(CircuitPanel cp, CompProperties properties, LToolBar toolbar, InsertPanel insertPanel) {
		this.cp = cp;
		this.toolbar = toolbar;
		this.properties = properties;
		revision = new RevisionManager(this);
		revision.saveState(new CircuitState(cp));
		inserter = new Inserter(cp, this, insertPanel, revision);
		clipboard = new Clipboard(cp, this, revision);
		selection = new Selection(cp, this, clipboard, properties, revision);
		wireBuilder = new WireBuilder(cp, revision);
		wireEditor = new WireEditor(cp);
		highlight = new Highlight(cp, this);
		customCreator = new CustomCreator(cp);
		customViewer = new CustomViewer(cp);
	}
	
	/**
	 * Handles a mouse press in the CircuitPanel by:
	 * 1) Finding the location of the press in the CircuitPanel coordinate space
	 * 2) Recording the mouse point for future use as a previous mouse position
	 * 3) Using CompSearch to detect what the click was over
	 * 4) Calling clickSelectDecisionTree() if the tool bar is in select mode
	 * 5) Scheduling an insert if the click was in blank space and the tool bar is in insert mode, or changing to select mode and calling
	 * clickSelectDecisionTree() if the click is over a component, action, or connection
	 */
	public void onMousePressed(MouseEvent e) {
		Point p = cp.withTransform(e.getPoint());
		if(SwingUtilities.isLeftMouseButton(e)) {
			CompSearch cs = new CompSearch(cp);
			int result = cs.search(p);
			
			if(toolbar.getToolMode().equals("Select")) {
				clickSelectDecisionTree(cs, result, e.isShiftDown(), e.getClickCount() >= 2);
			}
			else if(toolbar.getToolMode().equals("Insert")) {
				if(result == CompSearch.TOUCHING_COMPONENT || 
						result == CompSearch.TOUCHING_CONNECTION || 
						result == CompSearch.TOUCHING_ACTION) {
					toolbar.changeToSelect(LToolBar.INTERNAL);
					clickSelectDecisionTree(cs, result, e.isShiftDown(), e.getClickCount() >= 2);
				}
				else {
					dragMode = DRAGGING_HIGHLIGHT;
					inserter.scheduleInsert(p.x, p.y);
				}
			}	
		}
		prevMouse = new Point(p.x, p.y);
		cp.repaint();
	}
	
	/**
	 * Handles a mouse release in the CircuitPanel by:
	 * 1) Calling Highlight.released()
	 * 2) Repainting the CircuitPanel
	 * 3) Attempting an insert if the tool bar is in insert mode (the insert may be rejected for reasons described in 
	 * Inserter.attempInsert())
	 * 4) Refreshing the properties panel
	 */
	public void onMouseReleased(MouseEvent e) {	
		if(dragMode == DRAGGING_SELECTION && (selection.getDisplacementX() > 0 || selection.getDisplacementY() > 0)) {
			revision.saveState(new CircuitState(cp));
		}
		
		CompSearch cs = new CompSearch(cp);
		int result = cs.search(cp.withTransform(e.getPoint()));
		if(result == CompSearch.TOUCHING_CONNECTION && wireBuilder.isWorking()) wireBuilder.endWire(cs.getConnection());
		else if(dragMode == DRAGGING_WIRE) wireBuilder.cancelWire();
		else if(dragMode == DRAGGING_HIGHLIGHT) highlight.release();
		
		if(toolbar.getToolMode().equals("Insert")) inserter.attemptInsert(e);
		properties.refresh();
		cp.repaint();
	}
	
	/**
	 * Handles a mouse drag in the CircuitPanel by:
	 * 1) Finding the new mouse point in the CircuitPanel coordinate space
	 * 2) Dragging the selection if in select mode and allowDrag is true (it is the responsibility of mousePressed(...) to set this value
	 *    based on what the click was over)
	 * 3) Changing to select mode and doing the previous step if the mouse drag qualifies as a real mouse drag based on DRAG_THRESH
	 */
	public void onMouseDragged(MouseEvent e) {
		Point p = cp.withTransform(e.getPoint());
		if(SwingUtilities.isLeftMouseButton(e)) {
			if(toolbar.getToolMode().equals("Select")) {
				if(dragMode == DRAGGING_HIGHLIGHT) highlight.drag(p.x, p.y);
				else if(dragMode == DRAGGING_SELECTION) selection.drag(p.x, p.y);
				else if(dragMode == DRAGGING_WIRE) wireBuilder.setMousePoint(cp.withTransform(e.getPoint()));
			}
			else if(toolbar.getToolMode().equals("Insert")) {
				if(Math.abs(p.x - prevMouse.getX()) >= DRAG_THRESH && Math.abs(p.y - prevMouse.getY()) >= DRAG_THRESH) {
					toolbar.changeToSelect(LToolBar.INTERNAL);
				 	if(dragMode == DRAGGING_HIGHLIGHT) highlight.drag(p.x, p.y);
				 	else if(dragMode == DRAGGING_SELECTION) selection.drag(p.x, p.y);
				 	else if(dragMode == DRAGGING_WIRE) wireBuilder.setMousePoint(cp.withTransform(e.getPoint()));
				}
			}
		}
		prevMouse = new Point(p.x, p.y);
		cp.repaint();
	}
	
	/**
	 * Repaints the CircuitPanel and sets the locationn of the mouse in the WireBuilder
	 */
	public void onMouseMoved(MouseEvent e) {
		wireBuilder.setMousePoint(cp.withTransform(e.getPoint()));
		cp.repaint();
	}
	
	/**
	 * Handles a mouse click if the tool bar is in select mode. This method contains all the operations that must be performed if the click
	 * is touching a component action, touching the bounds of a component, touching a connection, touching a wire, or clear or any click 
	 * sensitive elements
	 * @param cs The CompSearch used to obtain the given result
	 * @param result The result of the result of the CompSearch after calling CompSearch.search(Point coord)
	 * @param shiftDown A boolean telling whether the shift key was down on the mouse event, which influences how the selection is modified
	 * @param doubleClick A boolean telling whether the mouse event was a double click, which influences how custom components behave 
	 */
	private void clickSelectDecisionTree(CompSearch cs, int result, boolean shiftDown, boolean doubleClick) {
		dragMode = DRAGGING_HIGHLIGHT;
		if(result == CompSearch.TOUCHING_ACTION) {
			LComponent csComp = cs.getLComp();
			if(csComp instanceof IComponent) ((IComponent) csComp).clickAction();
		}
		else if(result == CompSearch.TOUCHING_COMPONENT) {
			LComponent lcomp = cs.getLComp();
			if(doubleClick && lcomp instanceof Custom) {
				customViewer.view((Custom) lcomp);
			}
			else {
				if(!selection.contains(lcomp)) {
					if(!shiftDown) selection.clear();
					selection.select(lcomp);
				}
				wireEditor.clear();
				dragMode = DRAGGING_SELECTION;
			}
		}
		else if(result == CompSearch.TOUCHING_CONNECTION) {
			if(wireBuilder.isWorking()) wireBuilder.endWire(cs.getConnection());
			else wireBuilder.startWire(cs.getConnection());
			dragMode = DRAGGING_WIRE;
		}
		else if(result == CompSearch.TOUCHING_WIRE) {
			wireEditor.select(cs.getWire());
			selection.clear();
			if(wireBuilder.isWorking()) {
				wireBuilder.cancelWire();
				wireEditor.clear();
			}
		}
		else {
			wireBuilder.cancelWire();
			selection.clear();
			wireEditor.clear();
		}
	}
	
	/**
	 * Deletes the selected wire if the WireEditor has a selected wire, or calls selection.delete if the WireEditor is empty
	 */
	public void deleteElements() {
		if(enabled) {
			if(wireEditor.hasSelectedWire()) wireEditor.deleteWire();
			else selection.deleteComponents();
		}
	}

	/**
	 * Clears the selection, pastes the selection, and repaints the CircuitPanel
	 */
	public void paste() {
		if(enabled) {
			selection.clear();
			clipboard.paste();	
			cp.repaint();	
		}
	}
	
	/**
	 * Responds to the press of the escape key by reseting the CustomCreator if it is active or exiting the CustomViewer if it is active
	 */
	public void escapeActions() {
		if(customCreator.isActive()) customCreator.reset();
		else if(customViewer.isActive()) customViewer.exit();
	}
	
	/**
	 * Responds to the press of the enter key by completing the custom component if one is currently being made
	 */
	public void enterActions() {
		if(customCreator.isActive()) customCreator.completeCustom();
	}
	
	/**
	 * Returns the Selection
	 * @return The Selection
	 */
	public Selection getSelection() {
		return selection;
	}
	
	/**
	 * Returns the Highlight
	 * @return The Highlight
	 */
	public Highlight getHighlight() {
		return highlight;
	}
	
	/**
	 * Returns the WireBuilder
	 * @return The WireBuilder
	 */
	public WireBuilder getWireBuilder() {
		return wireBuilder;
	}
	
	/**
	 * Returns the CustomCreator
	 * @return The CustomCreator
	 */
	public CustomCreator getCustomCreator() {
		return customCreator;
	}
	
	/**
	 * Returns the CustomViewer
	 * @return The CustomViewer
	 */
	public CustomViewer getCustomViewer() {
		return customViewer;
	}
	
	/**
	 * Returns the RevisionManager
	 * @return The RevisionManager
	 */
	public RevisionManager getRevision() {
		return revision;
	}
	
	/**
	 * Returns the last recorded mouse position
	 * @return The last recorded mouse position
	 */
	public Point getPrevMouse() {
		return prevMouse;
	}

	/**
	 * Returns the snap setting
	 * @return The snap setting
	 */
	public boolean isSnap() {
		return snap;
	}
	
	/**
	 * Sets the snap setting
	 * @param snap The new snap setting
	 */
	public void setSnap(boolean snap) {
		this.snap = snap;
	}
	
	/**
	 * Tells whether this CircuitEditor is enabled
	 * @return The enabled setting
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Sets the CircuitEditor to be enabled or not enabled (see CircuitEditor.enabled)
	 * @param enabled The new enabled state
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {if(enabled) onMousePressed(e);}	
	@Override
	public void mouseReleased(MouseEvent e) {if(enabled) onMouseReleased(e);}
	@Override
	public void mouseDragged(MouseEvent e) {if(enabled) onMouseDragged(e);}
	@Override
	public void mouseMoved(MouseEvent e) {if(enabled) onMouseMoved(e);}
}
