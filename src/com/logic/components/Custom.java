package com.logic.components;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;

import com.logic.engine.LogicEngine;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompDrawer;
import com.logic.ui.CompRotator;
import com.logic.util.CompUtils;
import com.logic.util.CustomHelper;
import com.logic.util.CustomInput;
import com.logic.util.CustomOutput;
import com.logic.util.GraphicsUtils;

/**
 * An LComponent that combines multiple other components into one (ex: a full adder)
 * @author toddstennes
 *
 */
public class Custom extends SComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The font used to display the label of the component
	 */
	private final Font labelFont = new Font("Arial", Font.PLAIN, 15);
	
	/**
	 * Maps input indexes to input nodes 
	 */
	private HashMap<Integer, CustomInput> inputs;
	
	/**
	 * Maps output indexes to output nodes
	 */
	private HashMap<Integer, CustomOutput> outputs;
	
	/**
	 * The content map that is given when the component is constructed, used for copying the component
	 */
	private HashMap<Integer, LComponent[]> content;
	
	/**
	 * The list of all LComponents contained within this component, used for displaying the inside of the component, copying the component,
	 * and ensuring that this component functions properly if it happens to contain SComponents
	 */
	private ArrayList<LComponent> innerComps;
	
	/**
	 * The width and height of the body of the component in pixels
	 */
	private int width, height;
	
	/**
	 * The label that appears on the custom component (ex: "Adder")
	 */
	private String label;
	
	/**
	 * @param x The x position
	 * @param y The y position
	 * @param label The label that will be drawn on the component
	 * @param content The lights and switches that will be used to create this component, organized by the side of the component their 
	 * connections will appear on. To populate this map, use map.put(CompRotator.SOME_ROTATION, arrayOfLightsAndSwitches)
	 * @param innerComps The list of all LComponents that are contained in this component
	 */
	public Custom(int x, int y, String label, HashMap<Integer, LComponent[]> content, ArrayList<LComponent> innerComps) {
		super(x, y, CompType.CUSTOM);
		this.label = label;
		this.content = content;
		this.innerComps = innerComps;
		inputs = new HashMap<Integer, CustomInput>();
		outputs = new HashMap<Integer, CustomOutput>();
		CustomHelper helper = new CustomHelper(content);
		width = helper.chooseWidth(label, labelFont);
		height = helper.chooseHeight();
		
		for(int s  = CompRotator.RIGHT; s <= CompRotator.UP; s++) {
			LComponent[] side = content.get(s);
			if(side != null) initSide(side, s, helper);
		}
	}
	
	/**
	 * Sets the references of all inner SComponents to this component and starts all inner SComponents. This method also starts logic on 
	 * this component to ensure that it has a consistent state.
	 */
	@Override
	public void start(CircuitPanel cp) {
		for(int i = 0; i < innerComps.size(); i++) {
			LComponent lcomp = innerComps.get(i);
			if(lcomp instanceof SComponent) {
				SComponent scomp = (SComponent) lcomp;
				scomp.setCustom(this);
				scomp.start(cp);
			}
		}
		LogicWorker.startLogic(this);
	}

	/**
	 * Renders this component by drawing a Rectangle of the appropriate size and using drawer.drawConnections to display connections
	 */
	@Override
	public void render(Graphics g, CircuitPanel cp) {
		drawer.drawConnections(g);
		for(int i = 0; i < io.getNumInputs(); i++) io.connectionAt(i, Connection.INPUT).renderLabel(g, inputs.get(i).getLabel());
		for(int i = 0; i < io.getNumOutputs(); i++) io.connectionAt(i, Connection.OUTPUT).renderLabel(g, outputs.get(i).getLabel());
		Rectangle bounds = getBounds();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fill(bounds);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(bounds);
		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(2));
		if(selected) g2d.drawRect(bounds.x - 1, bounds.y - 1, bounds.width + 2, bounds.height + 2);
		drawLabel(g2d);
	}
	
	/**
	 * Updates this component by running a nested LogicEngine on this component's switches and clocks, and then setting the outputs to match 
	 * the output lights.
	 */
	@Override
	public void update(LogicEngine engine) {
		ArrayList<LComponent> startingComps = new ArrayList<LComponent>();
		for(int i = 0; i < io.getNumInputs(); i++) inputs.get(i).addIfNecessary(io.getInput(i), startingComps);
		for(int i = 0; i < innerComps.size(); i++) {
			LComponent lcomp = innerComps.get(i);
			if(lcomp instanceof SComponent) startingComps.add(lcomp);
		}
		
		LogicEngine le = new LogicEngine(startingComps);
		le.doLogic();
		for(int i = 0; i < io.getNumOutputs(); i++) io.setOutput(i, outputs.get(i).getState(), engine);                             
	}
	
	/**
	 * Returns a bounding box for this component based on its connections, which determine the shape of a custom component
	 * @return A bounding box for the component
	 */
	@Override
	public Rectangle getBounds() {
		int rotation = rotator.getRotation();
		if(rotation == CompRotator.UP || rotation == CompRotator.DOWN) return new Rectangle(x, y, height, width);
		else return new Rectangle(x, y, width, height);
		//TODO fix this and the following method
	}
	
	/**
	 * Returns a bounding box for this component when it is facing in a rightward direction
	 * @return A bounding box for the component
	 */
	@Override
	public Rectangle getBoundsRight() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Copies this Custom component by duplicating its inner component list and content map so that no interference occurs between the 
	 * components
	 */
	@Override
	public LComponent makeCopy() {
		return CompUtils.duplicateCustom(this);
	}
	
	/**
	 * Deletes all inner components and calls super.delete()
	 */
	@Override
	public void delete() {
		for(int i = 0; i < innerComps.size(); i++) {
			innerComps.get(i).delete();
		}
		super.delete();
	}
	
	/**
	 * Draws the label of this component at the center of the component
	 * @param g2d The Graphics object to use
	 */
	private void drawLabel(Graphics2D g2d) {
		g2d.setFont(labelFont);
		g2d.setColor(Color.BLACK);
		FontMetrics metrics = g2d.getFontMetrics(labelFont);
		int stringWidth = metrics.stringWidth(label);
		int stringHeight = metrics.getHeight();
		Rectangle bounds = getBounds();
		AffineTransform orig = g2d.getTransform();
		
		if(rotator.getRotation() == CompRotator.UP) g2d.rotate(Math.PI * 3 / 2, bounds.width / 2 + x, bounds.height / 2 + y);
		else if(rotator.getRotation() == CompRotator.LEFT) g2d.rotate(Math.PI, bounds.width / 2 + x, bounds.height / 2 + y);
		else if(rotator.getRotation() == CompRotator.DOWN) g2d.rotate(Math.PI / 2, bounds.width / 2 + x, bounds.height / 2 + y);
		
		g2d.drawString(label, (bounds.width - stringWidth) / 2 + x, (bounds.height - stringHeight + 25) / 2 + y);
		g2d.setTransform(orig);
	}

	/**
	 * Arranges the connections for the specified side of the component using the given array of Lights and Switches, which will 
	 * translate into connections
	 * @param side The Lights and/or switches that will be represented on a given side of the component
	 * @param sideNum The side of the component (CompRotator constant)
	 */
	private void initSide(LComponent[] side, int sideNum, CustomHelper helper) {
		Point[] connectionPoints = helper.getConnectionPoints(sideNum, width, height);
		for(int i = 0; i < side.length; i++) {
			initConnection(side[i], connectionPoints[i], sideNum);
		}
	}
	
	/**
	 * Creates a connection for this component based on the given information
	 * @param lcomp The LComponent (Light or Switch) that this connection will represent
	 * @param connectionPoint The pixel point to place the connection at
	 * @param sideNum The CompRotator constant that represents the side of the component to place the connection on
	 */
	private void initConnection(LComponent lcomp, Point connectionPoint, int sideNum) {
		if(lcomp instanceof Switch) {
			int connectionIndex = io.addConnection(connectionPoint.x, connectionPoint.y, Connection.INPUT, sideNum);
			inputs.put(connectionIndex, new CustomInput((Switch) lcomp));
		}
		else if(lcomp instanceof Light) {
			int connectionIndex = io.addConnection(connectionPoint.x, connectionPoint.y, Connection.OUTPUT, sideNum);
			outputs.put(connectionIndex, new CustomOutput((Light) lcomp));
		}
	}
	
	/**
	 * Return the content HashMap of this custom component
	 * @return The content HashMap
	 */
	public HashMap<Integer, LComponent[]> getContent(){
		return content;
	}
	
	/**
	 * Returns the list of LComponents that comprise this custom component
	 * @return The LComponents that comprise this custom component
	 */
	public ArrayList<LComponent> getInnerComps() {
		return innerComps;
	}
	
	/**
	 * Returns the components label
	 * @return The label of this Custom component
	 */
	public String getLabel() {
		return label;
	}
}
