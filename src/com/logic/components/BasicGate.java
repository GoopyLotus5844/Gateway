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
	 * The maximum, minimum, and default number of inputs a BasicGate can have
	 */
	public static final int MIN_INPUTS = 2, MAX_INPUTS = 10, DEFAULT_INPUTS = 2;

	/**
	 * The index of the Function that will be used to evaluate the output of this gate
	 */
	private int function;

	/**
	 * Constructs a new BasicGate with the default number of inputs
	 * @param x The x position of the new gate
	 * @param y The y position of the new gate
	 * @param type The type of gate (see other constructor)
	 */
	public BasicGate(int x, int y, CompType type){
		this(x, y, DEFAULT_INPUTS, type);
	}

	/**
	 * Constructs a new BasicGate
	 * @param x The x position of the new gate
	 * @param y The y position of the new gate
	 * @param numInputs The number of inputs, will be rounded to max / min if out of bounds
	 * @param type The type of gate (valid values are CompType.AND, CompType.NAND, CompType.OR, CompType.NOR, CompType.XOR, and CompType.XNOR)
	 */
	public BasicGate(int x, int y, int numInputs, CompType type) {
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

		if(numInputs > MAX_INPUTS) numInputs = MAX_INPUTS;
		else if(numInputs < MIN_INPUTS) numInputs = MIN_INPUTS;
		Point[] connectionPositions = calcInputPositions(-25, numInputs);
		for(Point p : connectionPositions){
			io.addConnection(p.x, p.y, Connection.INPUT, CompRotator.LEFT);
		}
		io.addConnection(110, 40, Connection.OUTPUT, CompRotator.RIGHT);
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

	/**
	 * Sets the number of inputs.  Unlike the constructor, the method will do nothing and exit if the number of inputs is out of bounds
	 * @param numInputs The number of inputs
	 */
	public void setNumInputs(int numInputs){
		if(numInputs < MIN_INPUTS || numInputs > MAX_INPUTS) return;
		int currentNum = io.getNumInputs();
		if(numInputs > currentNum) {
			for(int i = 0; i < numInputs - currentNum; i++){
				io.addConnection(0, 0, Connection.INPUT, CompRotator.LEFT);
			}
		}
		else if(numInputs < currentNum){
			for(int i = 0; i < currentNum - numInputs; i++){
				io.removeConnection(io.connectionAt(io.getNumInputs() - 1, Connection.INPUT));
			}
		}
		Point[] positions = calcInputPositions(-25, numInputs);
		io.setConnectionLayout(new ConnectionLayout(positions, CompRotator.LEFT, Connection.INPUT));
	}

	/**
	 * Calculates the positions of each input so that they are centered and equally spaced in the y direction
	 * @param xPos The x position that all of the inputs will have
	 * @param numConnections The number of inputs the component will have
	 * @return An array of points showing the input positions
	 */
	private Point[] calcInputPositions(int xPos, int numConnections){
		int start = 80 / 2 - CompDrawer.BASIC_INPUT_SPACING / 2 * (numConnections - 1);
		Point[] positions = new Point[numConnections];
		for(int i = 0; i < numConnections; i++){
			positions[i] = new Point(xPos, start + i * CompDrawer.BASIC_INPUT_SPACING);
		}
		return positions;
	}

	@Override
	public LComponent makeCopy() {
		BasicGate result = new BasicGate(x, y, io.getNumInputs(), type);
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		return result;
	}
}
