package com.logic.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import com.logic.components.BasicGate;
import com.logic.components.Button;
import com.logic.components.Clock;
import com.logic.components.CompType;
import com.logic.components.Constant;
import com.logic.components.Display;
import com.logic.components.Light;
import com.logic.components.SingleInputGate;
import com.logic.components.Switch;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompDrawer;
import com.logic.ui.InsertPanel;

/**
 * This class holds the code for adding components to the CircuitPanel
 * @author toddstennes
 *
 */
public class Inserter {

	/**
	 * The position of a scheduled insert
	 */
	private int scheduleX, scheduleY;
	
	/**
	 * A boolean telling whether an insert is scheduled
	 */
	private boolean insertScheduled;
	
	/**
	 * An array who's values are constant after the constructor that shows the offsets necessary to place components with the mouse at their
	 * center
	 */
	private HashMap<String, Point> offsets;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The CircuitEditor
	 */
	private CircuitEditor editor;
	
	/**
	 * The InsertPanel
	 */
	private InsertPanel insertPanel;
	
	/**
	 * The RevisionManager
	 */
	private RevisionManager revision;
	
	/**
	 * Constructs a new Inserter
	 * @param cp The CircuitPanel
	 * @param editor The CircuitEditor
	 * @param insertPanel The InsertPanel
	 */
	public Inserter(CircuitPanel cp, CircuitEditor editor, InsertPanel insertPanel, RevisionManager revision) {
		this.cp = cp;
		this.editor = editor;
		this.insertPanel = insertPanel;
		this.revision = revision;
		offsets = new HashMap<String, Point>();
		float scale = CompDrawer.IMAGE_SCALE;
		offsets.put("Buffer", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Not", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("And", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Nand", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Or", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Nor", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Xor", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Xnor", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Clock", new Point((int) (5.5 * scale), (int) (3.5 * scale)));
		offsets.put("Light", new Point((int) (3.5 * scale), (int) (5.5 * scale)));
		offsets.put("Switch", new Point((int) (4.5 * scale), (int) (4.5 * scale)));
		offsets.put("Zero", new Point((int) (4.5 * scale), (int) (4.5 * scale)));
		offsets.put("One", new Point((int) (4.5 * scale), (int) (4.5 * scale)));
		offsets.put("Button", new Point((int) (4.5 * scale), (int) (5.5 * scale)));
		offsets.put("Display", new Point((int) (8.5 * scale), (int) (5.5 * scale)));
	}
	
	/**
	 * Schedules an insert so that it can be performed when attemptInsert() is called. This method is usually called within a mouse pressed
	 * method
	 * @param scheduleX The x value of the CircuitPanel coordinate
	 * @param scheduleY The y value of the CircuitPanel coordinate
	 */
	public void scheduleInsert(int scheduleX, int scheduleY) {
		this.scheduleX = scheduleX;
		this.scheduleY = scheduleY;
		insertScheduled = true;
	}
	
	/**
	 * Attempts an insert. The insert will occur as long as an insert has been scheduled and the mouse has not moved more than CircuitEditor
	 * .DRAG_THRESH in the x or y direction
	 * @param e The MouseEvent that is causing this method to be called
	 */
	public void attemptInsert(MouseEvent e) {
		if(insertScheduled) {
			insertScheduled = false;
			Point location = cp.withTransform(e.getPoint());
			if(Math.abs(location.x - scheduleX) < CircuitEditor.DRAG_THRESH && Math.abs(location.y - scheduleY) < CircuitEditor.DRAG_THRESH) {
				String name = insertPanel.getSelectedComponent();
				if(name != null) doInsert(name, location);
			}
		}
	}
	
	/**
	 * Inserts a new LComponent of the type represented by the given name at the specified location. If editor.isSnap() returns true, then
	 * the coordinates that the component is placed at are certain to be multiples of CircuitEditor.SNAP_DIST. Otherwise, the component
	 * is placed so that is center appears at the mouse's location based on the offsets HashMap. This method also saves a CircuitState
	 * so that the insertion can be undone.
	 * @param name The name of the LComponent (the CompType with only the first letter capitalized)
	 * @param location The location of the mouse
	 */
	private void doInsert(String name, Point location) {
		int x = location.x;
		int y = location.y;
		if(editor.isSnap()) {
			x = (x - offsets.get(name).x) / CircuitEditor.SNAP_DIST * CircuitEditor.SNAP_DIST;
			y = (y - offsets.get(name).y) / CircuitEditor.SNAP_DIST * CircuitEditor.SNAP_DIST;
		}
		else {
			x -= offsets.get(name).x;
			y -= offsets.get(name).y;
		}              
		if(name.equals("Buffer")) cp.addLComp(new SingleInputGate(x, y, CompType.BUFFER));
		else if(name.equals("Not")) cp.addLComp(new SingleInputGate(x, y, CompType.NOT));
		else if(name.equals("And")) cp.addLComp(new BasicGate(x, y, CompType.AND));
		else if(name.equals("Nand")) cp.addLComp(new BasicGate(x, y, CompType.NAND));
		else if(name.equals("Or")) cp.addLComp(new BasicGate(x, y, CompType.OR));
		else if(name.equals("Nor")) cp.addLComp(new BasicGate(x, y, CompType.NOR));
		else if(name.equals("Xor")) cp.addLComp(new BasicGate(x, y, CompType.XOR));
		else if(name.equals("Xnor")) cp.addLComp(new BasicGate(x, y, CompType.XNOR));
		else if(name.equals("Clock")) cp.addLComp(new Clock(x, y));
		else if(name.equals("Light")) cp.addLComp(new Light(x, y));
		else if(name.equals("Switch")) cp.addLComp(new Switch(x, y));
		else if(name.equals("Zero")) cp.addLComp(new Constant(x, y, CompType.ZERO));
		else if(name.equals("One")) cp.addLComp(new Constant(x, y, CompType.ONE));
		else if(name.equals("Button")) cp.addLComp(new Button(x, y));
		else if(name.equals("Display")) cp.addLComp(new Display(x, y));
		revision.saveState(new CircuitState(cp));
	}
}
