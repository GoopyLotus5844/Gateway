package com.logic.engine;

import java.util.ArrayList;

import com.logic.components.LComponent;

/**
 * This class uses an iterative algorithm to sequentially update all components that are influenced by the list of starting components
 * @author toddstennes
 *
 */
public class LogicEngine {

	/**
	 * The list of components that must be updated on the next iteration
	 */
	private ArrayList<LComponent> activeComps;
	
	/**
	 * Constructs a new LogicEngine
	 * @param startingComps The list of LComponents that start out as marked
	 */
	public LogicEngine(ArrayList<LComponent> startingComps) {
		activeComps = startingComps;
	}
	
	/**
	 * Performs the logic based on the activeComps list and terminates when the list is empty
	 * @Return The number of iterations it took to complete the logic
	 */
	public int doLogic() {
		int iterations = 0;
		while(activeComps.size() > 0) {
			ArrayList<LComponent> lcomps = activeComps;
			activeComps = new ArrayList<LComponent>();
			for(int i = 0; i < lcomps.size(); i++) {
				lcomps.get(i).update(this);
			}
			iterations++;
		}
		return iterations;
	}
	
	/**
	 * Marks a component to be updated on the next cycle of the doInBackground method. The call to this method should only be written in one
	 * place in the entire program - the IOManager.setOutput method, which is responsible for changing the output of a component (it then 
	 * marks the next component(s))
	 * @param lcomp The LComponent to mark
	 */
	public void mark(LComponent lcomp) {
		activeComps.add(lcomp);
	}
	
}
