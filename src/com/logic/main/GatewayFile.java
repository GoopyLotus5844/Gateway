package com.logic.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.logic.components.LComponent;
import com.logic.input.Camera;
import com.logic.ui.CircuitPanel;

/**
 * The class that is serialized to save a gateway circuit. This class holds an array of LComponents, a camera zoom value, and a camera 
 * position so that the program can be restored to its previous state when a file is loaded.
 * @author toddstennes
 *
 */
public class GatewayFile implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The LComponents that were in the CircuitPanel when the file was saved
	 */
	private LComponent[] lcomps;
	
	/**
	 * The zoom value that the camera had when the file was saved
	 */
	private double camZoom;
	
	/**
	 * The translate position that the camera was at when the file was saved
	 */
	private double camX, camY;
	
	/**
	 * Constructs a new GatewayFile that can be serialized to save a circuit
	 * @param lcomps The LComponents to save
	 * @param camera The Camera, which will be used to save the CircuitPanel transformation
	 */
	public GatewayFile(ArrayList<LComponent> lcomps, Camera camera) {
		this.lcomps = lcomps.toArray(new LComponent[0]);
		camZoom = camera.getZoom();
		camX = camera.getX();
		camY = camera.getY();
	}
	
	/**
	 * Clears the given CircuitPanel and adds the components that were in the CircuitPanel from the constructor
	 * @param cp The CircuitPanel to edit
	 */
	public void setupCircuitPanel(CircuitPanel cp) {
		List<LComponent> list = Arrays.asList(lcomps);
		cp.lcomps.clear();
		cp.wires.clear();
		cp.addLComps(list);
		cp.repaint();
	}
	
	/**
	 * Sets the zoom and position of the camera to match that of the camera that was given when this GatewayFile was constructed
	 * @param cam The camera to edit
	 */
	public void setupCamera(Camera cam) {
		cam.setZoom(camZoom);
		cam.setX(camX);
		cam.setY(camY);	
	}
	
}
