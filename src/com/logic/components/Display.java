package com.logic.components;

import java.awt.Graphics;

import com.logic.engine.LogicEngine;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompRotator;

/**
 * A digit display component that shows a decimal value based on four bits of input
 * @author toddstennes
 *
 */
public class Display extends LComponent {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new Display
	 * @param cp The CircuitPanel instance being used
	 * @param x The x position of the display
	 * @param y The y position of the display
	 */
	public Display(int x, int y) {
		super(x, y, CompType.DISPLAY);
		int[] images = new int[16];
		for(int i = 0; i < images.length; i++) {
			images[i] = i + 30;
		}
		drawer.setImages(images);
		io.addConnection(0, 0, Connection.INPUT, CompRotator.LEFT);
		io.addConnection(0, 3, Connection.INPUT, CompRotator.LEFT);
		io.addConnection(0, 7, Connection.INPUT, CompRotator.LEFT);
		io.addConnection(0, 10, Connection.INPUT, CompRotator.LEFT);
	}
	
	@Override
	public void update(LogicEngine engine) { }

	@Override
	public void render(Graphics g, CircuitPanel cp) {
		int val = calcValue(io.getInput(0), io.getInput(1), io.getInput(2), io.getInput(3));
		drawer.setActiveImageIndex(val);
		drawer.draw(g);
	}
	
	/**
	 * Returns an integer value in base ten that is equal to the given binary value
	 * @param i1 The one's digit of the binary number
	 * @param i2 The two's digit of the binary number
	 * @param i4 The four's digit of the binary number
	 * @param i8 The eight's digit of the binary number
	 * @return The base ten equivalent of the binary value
	 */
	private int calcValue(boolean i1, boolean i2, boolean i4, boolean i8) {
		return boolToInt(i1) + 2 * boolToInt(i2) + 4 * boolToInt(i4) + 8 * boolToInt(i8);
	}
	
	/**
	 * Converts a boolean to an integer in an intuitive way
	 * @param b The boolean to convert
	 * @return 1 if b is true, 0, if b is false
	 */
	public int boolToInt(boolean b) {
		if(b) return 1;
		return 0;
	}
	
	@Override
	public LComponent makeCopy() {
		Display result = new Display(x, y);
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		return result;
	}
}
