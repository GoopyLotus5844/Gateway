package com.logic.components;

import java.awt.Graphics;

import com.logic.engine.LogicEngine;
import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;
import com.logic.ui.CompRotator;

/**
 * This class represents a button that outputs HIGH when pressed and LOW when not pressed, and requires the mouse to remain down in order to 
 * stay pressed
 * @author toddstennes
 *
 */
public class Button extends IComponent {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new Button
	 * @param cp The CircuitPanel instance being used
	 * @param x The x position of the Button
	 * @param y The y position of the button
	 */
	public Button(int x, int y) {
		super(x, y, CompType.BUTTON);
		drawer.setImages(new int[] {5, 6});
		io.addConnection(4, 10, Connection.OUTPUT, CompRotator.DOWN);
		setClickAction(2, 2, 5, 5);
		setNotificationType(RELEASED);
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
		setState(true);
		LogicWorker.startLogic(this);
	}

	@Override
	public void notification(int type) {
		if(type == RELEASED) {
			if(getState()) {
				setState(false);
				LogicWorker.startLogic(this);
			}
		}
	}
	
	@Override
	public LComponent makeCopy() {
		Button result = new Button(x, y);
		result.getRotator().setRotation(rotator.getRotation());
		result.setName(getName());
		return result;
	}
	
	@Override
	public void increaseInputs() { }
	
	@Override 
	public void decreaseInputs() { }

}
