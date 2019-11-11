package com.logic.components;

import com.logic.engine.LogicWorker;
import com.logic.ui.CircuitPanel;

/**
 * STANDS FOR SPONTANEOUS COMPONENT
 * A superclass for any component that is able to Spontaneously change its state. This means that the state of the component can change 
 * without the user interacting with the component and without being updated.  This class is necessary because of the requirements for 
 * spontaneous components (such as clocks) to work when placed inside of custom components.
 * @author toddstennes
 *
 */
public abstract class SComponent extends LComponent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The custom component that this SComponent is contained within, if there is one (can be null)
	 */
	private Custom custom;
	
	/**
	 * Constructs a new SComponent
	 * @param x The x position
	 * @param y The y position
	 * @param type The type of component
	 */
	public SComponent(int x, int y, CompType type) {
		super(x, y, type);
	}

	/**
	 * Performs additional initialization steps on this SComponent. The only rule for the implementation of this method is that the 
	 * component must not be able to generate spontaneous changes until this method is called, at which point spontaneous changes may begin
	 * (see SComponent class description)
	 * @param cp The CircuitPanel associated with this SComponent
	 */
	public abstract void start(CircuitPanel cp);
	
	/**
	 * If this component is not contained within another custom component, this method simply starts logic on the component. Otherwise, the
	 * method calls innerChange() on the containing component. This method should be called whenever there is a change inside of the custom
	 * component that did not occur as a result of the component being updated.  This method should be called on the event dispatch thread
	 * only
	 */
	public void innerChange() {
		if(custom == null) LogicWorker.startLogic(this);
		else custom.innerChange();
	}
	
	/**
	 * Updates this component's reference to the custom component that it is contained within. This reference must be correct for custom 
	 * components with SComponents inside of them to work properly
	 * @param custom The custom component
	 */
	public void setCustom(Custom custom) {
		this.custom = custom;
	}
	
}
