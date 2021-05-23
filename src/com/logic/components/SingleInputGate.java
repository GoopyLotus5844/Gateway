package com.logic.components;

import java.awt.Graphics;

import com.logic.engine.LogicEngine;
import com.logic.engine.LogicFunctions;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompRotator;

/**
 * This class represents not gates and buffers
 * @author toddstennes
 *
 */
public class SingleInputGate extends LComponent {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The index of the function that either inverts its input or returns its input unchanged depending on the type of component
	 */
	private int function;
	
	/**
	 * Constructs a new SingleInputGate
	 * @param x The x position
	 * @param y The y position
	 * @param type The type (CompType.NOT, CompType.BUFFER)
	 */
	public SingleInputGate(int x, int y, CompType type) {
		super(x, y, type);
		if(type == CompType.BUFFER) {
			drawer.setImages(new int[] {0});
			function = 1;
		}
		else if(type == CompType.NOT) {
			drawer.setImages(new int[] {1});
			function = 0;
		}
		io.addConnection(0, 3, Connection.INPUT, CompRotator.LEFT);
		io.addConnection(10, 3, Connection.OUTPUT, CompRotator.RIGHT);
	}
	
	@Override
	public void update(LogicEngine engine) {
		io.setOutput(0, LogicFunctions.func1s.get(function).apply(io.getInput(0)), engine);
	}

	@Override
	public void render(Graphics g, CircuitPanel cp) {
		drawer.draw(g);
	}
	
	@Override
	public LComponent makeCopy() {
		SingleInputGate result = new SingleInputGate(x, y, type);
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		return result;
	}

	@Override
	public void increaseInputs() { }
	
	@Override 
	public void decreaseInputs() { }
}