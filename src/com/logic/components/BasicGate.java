package com.logic.components;

import java.awt.Graphics;
import java.awt.Point;

import com.logic.engine.LogicEngine;
import com.logic.engine.LogicFunctions;
import com.logic.ui.CircuitPanel;
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
	 * This value represents the difference in image indexes (in IconLoader) between any two input gate image and its corresponding three
	 * input image
	 */
	private final int threeInputImageAdd = 8;
	
	/**
	 * This value represents the difference in image indexes (in IconLoader) between any two input gate image and its corresponding four
	 * input image
	 */
	private final int fourInputImageAdd = 14;
	
	/**
	 * Constructs a new BasicGate
	 * @param x The x position of the new gate
	 * @param y The y position of the new gate
	 * @param type The type of gate (valid values are CompType.AND, CompType.NAND, CompType.OR, CompType.NOR, CompType.XOR, and CompType.XNOR)
	 */
	public BasicGate(int x, int y, CompType type) {
		super(x, y, type);
		int[] images = null;
		if(type == CompType.AND) {
			images = loadImages(2);
			function = 0;
		}
		else if(type == CompType.NAND) {
			images = loadImages(3);
			function = 1;
		}
		else if(type == CompType.OR) {
			images = loadImages(4);
			function = 2;
		}
		else if(type == CompType.NOR) {
			images = loadImages(5);
			function = 3;
		}
		else if(type == CompType.XOR) {
			images = loadImages(6);
			function = 4;
		}
		else if(type == CompType.XNOR) {
			images = loadImages(7);
			function = 5;
		}
		drawer.setImages(images);
		drawer.setActiveImageIndex(0);
		io.addConnection(0, 1, Connection.INPUT, CompRotator.LEFT);
		io.addConnection(0, 5, Connection.INPUT, CompRotator.LEFT);
		io.addConnection(10, 3, Connection.OUTPUT, CompRotator.RIGHT);
		io.setInputFlexible(true);
		io.setMaxInputs(4);
		io.setMinInputs(2);
	}
	
	/**
	 * Gets a set of logic gate images from the IconLoader
	 * @param twoInputImageIndex The image index of the two input gate image
	 * @return An array of BufferedImages of length 3 where the first image has two inputs, the second has three, and the third has four
	 */
	private int[] loadImages(int twoInputImageIndex) {
		int[] images = new int[3];
		images[0] = twoInputImageIndex;
		images[1] = twoInputImageIndex + threeInputImageAdd;
		images[2] = twoInputImageIndex + fourInputImageAdd;
		return images;
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
	
	@Override 
	public void increaseInputs() {
		if(io.getNumInputs() == 2) {
			drawer.setActiveImageIndex(1);
			Point[] layoutPoints = new Point[] {new Point(0, 0), new Point(0, 3)};
			io.setConnectionLayout(new ConnectionLayout(layoutPoints, CompRotator.LEFT, Connection.INPUT));
			io.addConnection(0, 6, Connection.INPUT, CompRotator.LEFT);
		}
		else if(io.getNumInputs() == 3) {
			drawer.setActiveImageIndex(2);
			Point[] layoutPoints = new Point[] {new Point(0, 0), new Point(0, 3), new Point(0, 7)};
			io.setConnectionLayout(new ConnectionLayout(layoutPoints, CompRotator.LEFT,Connection.INPUT));
			io.addConnection(0, 10, Connection.INPUT, CompRotator.LEFT);
			layoutPoints = new Point[] {new Point(10, 5)};
			io.setConnectionLayout(new ConnectionLayout(layoutPoints, CompRotator.RIGHT,Connection.OUTPUT));	
		}
	}
	
	@Override
	public void decreaseInputs() {
		if(io.getNumInputs() == 3) {
			drawer.setActiveImageIndex(0);
			io.removeConnection(io.connectionAt(2, Connection.INPUT));
			Point[] layoutPoints = new Point[] {new Point(0, 1), new Point(0, 5)};
			io.setConnectionLayout(new ConnectionLayout(layoutPoints, CompRotator.LEFT,Connection.INPUT));
		}
		else if(io.getNumInputs() == 4) {
			drawer.setActiveImageIndex(1);
			io.removeConnection(io.connectionAt(3, Connection.INPUT));
			Point[] layoutPoints = new Point[] {new Point(0, 0), new Point(0, 3), new Point(0, 6)};
			io.setConnectionLayout(new ConnectionLayout(layoutPoints, CompRotator.LEFT,Connection.INPUT));
			layoutPoints = new Point[] {new Point(10, 3)};
			io.setConnectionLayout(new ConnectionLayout(layoutPoints, CompRotator.RIGHT,Connection.OUTPUT));
		}
	}

	public void setNumInputs(){

	}

	@Override
	public LComponent makeCopy() {
		BasicGate result = new BasicGate(x, y, type);
		int numInputs = io.getNumInputs();
		if(numInputs == 3) result.increaseInputs();
		else if(numInputs == 4) {
			result.increaseInputs();
			result.increaseInputs();
		}
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		return result;
	}
	
}
