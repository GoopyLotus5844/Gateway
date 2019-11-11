package com.logic.ui;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.logic.components.Custom;
import com.logic.components.LComponent;
import com.logic.input.Camera;
import com.logic.util.CompUtils;

/**
 * A class that displays the components that make up custom components
 * @author toddstennes
 *
 */
public class CustomViewer {

	/**
	 * A boolean that tells whether a custom component is currently being viewed
	 */
	private boolean active;
	
	/**
	 * The list of LComponents that were in the CircuitPanel before they were replaced with those making up the custom component. This list
	 * is used for restoring the CircuitPanel to its previous state when exit() is called
	 */
	private ArrayList<LComponent> oldComps;
	
	/**
	 * The zoom of the camera before this CustomViewer became active
	 */
	private double oldCamZoom;
	
	/**
	 * The position of the camera before this CustomViewer became active
	 */
	private double oldCamX, oldCamY;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * Constructs a new CustomViewer
	 * @param cp The CircuitPanel
	 */
	public CustomViewer(CircuitPanel cp) {
		this.cp = cp;
		oldComps = new ArrayList<LComponent>();
	}
	
	/**
	 * Displays the inner components in the given custom component using its dispComps list. This method stores the current CircuitPanel
	 * state, clears the CircuitPanel and selection, disables the CircuitEditor, adds the inner components to the the CircuitPanel, 
	 * repositions the camera, and displays the custom component message.
	 * @param c The Custom component
	 */
	public void view(Custom c) {
		ArrayList<LComponent> dispComps = c.getInnerComps();
		for(int i = 0; i < cp.lcomps.size(); i++) oldComps.add(cp.lcomps.get(i));
		cp.lcomps.clear();
		cp.wires.clear();
		cp.getEditor().getSelection().clear();
		cp.getEditor().setEnabled(false);
		cp.addLComps(dispComps);
		
		Camera cam = cp.getCamera();
		oldCamZoom = cam.getZoom();
		oldCamX = cam.getX();
		oldCamY = cam.getY();
		cam.setZoom(1);
		Rectangle boundingRect = CompUtils.getBoundingRectangle(dispComps);
		cam.setX((int) -(boundingRect.getX() - (cp.getWidth() - boundingRect.getWidth()) / 2));
		cam.setY((int) -(boundingRect.getY() - (cp.getHeight() - boundingRect.getHeight()) / 2));
		
		cp.getProperties().refresh();
		cp.dispMessage(new UserMessage(cp, "Viewing custom component, press ESC to exit"));
		active = true;
	}
	
	/**
	 * This method exits the custom component view by clearing the CircuitPanel, adding the oldComps list to the CircuitPanel, enabling
	 * the CircuitEditor, and repositioning the camera.
	 */
	public void exit() {
		cp.lcomps.clear();
		cp.wires.clear();
		cp.addLComps(oldComps);
		cp.getEditor().setEnabled(true);
		
		Camera cam = cp.getCamera();
		cam.setZoom(oldCamZoom);
		cam.setX(oldCamX);
		cam.setY(oldCamY);
		
		cp.getProperties().refresh();
		cp.clearMessage();
		cp.repaint();
		active = false;
		oldComps.clear();
	}
	
	/**
	 * Tells whether this CustomViewer is currently active
	 * @return The active state
	 */
	public boolean isActive() {
		return active;
	}
	
}
