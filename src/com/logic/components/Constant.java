package com.logic.components;

import java.awt.Graphics;

import com.logic.engine.LogicEngine;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompRotator;

/**
 * A component that constantly outputs either a high or low signal, depending on its type
 * @author toddstennes
 *
 */
public class Constant extends LComponent {
	
	private static final long serialVersionUID = 1L;
		
	/**
	 * Constructs a new Constant 
	 * @param x The x position of the constant
	 * @param y The y position of the constant
	 * @param type The type of constant (CompType.ZERO for low signal, CompType.ONE for high signal)
	 */
	public Constant(int x, int y, CompType type) {
		super(x, y, type);
		if(type == CompType.ONE) drawer.setImages(new int[] {10});
		else if(type == CompType.ZERO) drawer.setImages(new int[] {9});
		io.addConnection(80, 40, Connection.OUTPUT, CompRotator.RIGHT);
	}
	
	@Override
	public void update(LogicEngine engine) {
		if(type == CompType.ONE) io.setOutput(0, true, engine);
		else io.setOutput(0, false, engine);
	}
	
	@Override
	public void render(Graphics g, CircuitPanel cp) {
		drawer.draw(g);
	}
	
	@Override
	public LComponent makeCopy() {
		Constant result = new Constant(x, y, type);
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		return result;
	}
}
