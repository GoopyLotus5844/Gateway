package com.logic.ui;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.logic.input.Camera;

/**
 * A JSlider for changing the zoom of the CircuitPanel
 * @author toddstennes
 *
 */
public class ZoomSlider extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The text label for the slider
	 */
	private JLabel label;
	
	/**
	 * The JSlider
	 */
	private JSlider slider;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;

	/**
	 * Shows if the slider was most recently updated internally by the program rather than being dragged by the user
	 */
	private boolean internalUpdate = false;
	
	/**
	 * Constructs a new ZoomSlider
	 * @param cp The CircuitPanel
	 */
	public ZoomSlider(CircuitPanel cp) {
		this.cp = cp;
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		label = new JLabel("+/-");
		add(label);
		slider = new JSlider();
		slider.addChangeListener(this);
		add(slider);
	}
	
	/**
	 * Updates the displayed position of the slider based on the actual zoom of the CircuitPanel
	 */
	public void updatePosition() {
		internalUpdate = true;
		Camera cam = cp.getCamera(); 
		double zoom = cam.getZoom() - cam.minZoom;
		double range = cam.maxZoom - cam.minZoom;
		slider.setValue((int) ((100 / range) * zoom));
	}
	
	/**
	 * Updates the zoom of the CircuitPanel when the position of the slider is changes
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(internalUpdate){
			internalUpdate = false;
			return;
		}
		Camera cam = cp.getCamera();
		JSlider source = (JSlider) e.getSource();
		int value = source.getValue();
		double range = cam.maxZoom - cam.minZoom;
		cam.setZoom((range / 100) * value + cam.minZoom);
	}

}
