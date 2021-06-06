package com.logic.components;

import java.awt.Graphics;
import java.awt.Point;

import com.logic.engine.LogicEngine;
import com.logic.engine.LogicFunctions;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompDrawer;
import com.logic.ui.CompRotator;
import com.logic.util.ConnectionLayout;

/**
 * The class that represents all basic logic gates, which include and, nand, or, nor, xor, and xnor
 * @author toddstennes
 *
 */
public class BasicGate extends LComponent {
		
	private static final long serialVersionUID = 1L;

	/**
	 * The index of the Function that will be used to evaluate the output of this gate
	 */
	private int function;
	
	/**
	 * Constructs a new BasicGate
	 * @param x The x position of the new gate
	 * @param y The y position of the new gate
	 * @param type The type of gate (valid values are CompType.AND, CompType.NAND, CompType.OR, CompType.NOR, CompType.XOR, and CompType.XNOR)
	 */
	public BasicGate(int x, int y, CompType type) {
		super(x, y, type);
		if(type == CompType.AND) {
			drawer.setImages(new int[]{1});
			function = 0;
		}
		else if(type == CompType.NAND) {
			drawer.setImages(new int[]{1});
			function = 1;
		}
		else if(type == CompType.OR) {
			drawer.setImages(new int[]{2});
			function = 2;
		}
		else if(type == CompType.NOR) {
			drawer.setImages(new int[]{2});
			function = 3;
		}
		else if(type == CompType.XOR) {
			drawer.setImages(new int[]{2});
			function = 4;
		}
		else if(type == CompType.XNOR) {
			drawer.setImages(new int[]{2});
			function = 5;
		}
		drawer.setActiveImageIndex(0);

		int[] connectionPositions = calcConnectionYPositions(3);
		for(int cy : connectionPositions){
			io.addConnection(-25, cy, Connection.INPUT, CompRotator.LEFT);
		}
		io.addConnection(110, 40, Connection.OUTPUT, CompRotator.RIGHT);
		io.setMaxInputs(4);
		io.setMinInputs(2);
	}
	                      
	@Override
	public void render(Graphics g, CircuitPanel cp) {
		drawer.draw(g);
	}

	@Override
	public void update(LogicEngine engine) {
		int inputs = io.getNumInputs();
		boolean output = false;
		if(inputs == 2) output = LogicFunctions.func2s.get(function).apply(io.getInput(0), io.getInput(1));
		else if(inputs == 3) output = LogicFunctions.func3s.get(function).apply(io.getInput(0), io.getInput(1), io.getInput(2));
		else if(inputs == 4) output = LogicFunctions.func4s.get(function).apply(io.getInput(0), io.getInput(1), io.getInput(2), io.getInput(3));
		io.setOutput(0, output, engine);
	}

	private int[] calcConnectionYPositions(int numConnections){
		int start = 80 / 2 - CompDrawer.BASIC_INPUT_SPACING / 2 * (numConnections - 1);
		int[] positions = new int[numConnections];
		for(int i = 0; i < numConnections; i++){
			positions[i] = start + i * CompDrawer.BASIC_INPUT_SPACING;
		}
		return positions;
	}

	@Override
	public LComponent makeCopy() {
		BasicGate result = new BasicGate(x, y, type);
		int numInputs = io.getNumInputs();
		//TODO correctly copy the number of inputs to the new component
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		return result;
	}
	
}
