package com.logic.components;

import java.awt.Graphics;

import com.logic.engine.LogicEngine;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompRotator;

/**
 * An input component that toggles its state when its clickAction is fired
 * @author toddstennes
 *
 */
public class Switch extends IComponent {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new switch
	 * @param cp The CircuitPanel
	 * @param x The x position
	 * @param y The y position
	 */
	public Switch(int x, int y) {
		super(x, y, CompType.SWITCH);
		drawer.setImages(new int[] {3, 4});
		io.addConnection(80, 40, Connection.OUTPUT, CompRotator.RIGHT);
		setClickAction(15, 15, 30, 50);
	}
	
	@Override
	public void render(Graphics g, CircuitPanel cp) {
		if(getState()) drawer.setActiveImageIndex(1);
		else drawer.setActiveImageIndex(0);
		drawer.draw(g);
	}
	
	@Override
	public void update(LogicEngine engine) {
		io.setOutput(0, getState(), engine);
	}

	@Override
	public void clickAction() {
		setState(!getState());
		LogicWorker.startLogic(this);
	}

	@Override
	public void notification(int type) { }
	
	@Override
	public LComponent makeCopy() {
		Switch result = new Switch(x, y);
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		result.setState(getState());
		return result;
	}

	@Override
	public void increaseInputs() { }
	
	@Override 
	public void decreaseInputs() { }

}
