package com.logic.engine;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import com.logic.components.Connection;
import com.logic.components.IOManager;
import com.logic.components.LComponent;
import com.logic.ui.CircuitPanel;

/**
 * A SwingWorker subclass for running a LogicEngine on its own thread
 * @author toddstennes
 *
 */
public class LogicWorker extends SwingWorker<Integer, Void>{
	
	/**
	 * A constant that represents a logic process that originates from a given component
	 */
	private final int singularProcess = 0;
	
	/**
	 * A constant that represents a logic process that originates from all components that either have no input connections or have at least
	 * one empty input connection, meaning that the entire circuit will be brought to a logically consistent state
	 */
	private final int fullProcess = 1;
	
	/**
	 * The type of logic process (singular process or full process)
	 */
	private int processType;
	
	/**
	 * The name of the thread that this LogicWorker is running on
	 */
	private String threadName;
	
	/**
	 * The LogicEngine to run on the SwingWorker thread
	 */
	private LogicEngine engine;
	
	/**
	 * Constructs a new LogicWorker that starts its LogicEngine with the given component
	 * @param circuitPanel The CircuitPanel
	 * @param startingComp The Component to start with in the activeComps list
	 */
	private LogicWorker(LComponent startingComp) {
		//Currently used by Button.clickAction, Button.notification, Clock.actionPerformed, Switch.clickAction, WireBuilder.startWire, 
		//WireBuilder.endWire, WireEditor.deleteWire, Custom.start()
		processType = singularProcess;
		ArrayList<LComponent> startingComps = new ArrayList<LComponent>();
		startingComps.add(startingComp);
		engine = new LogicEngine(startingComps);
	}
	
	/**
	 * Constructs a new LogicWorker that is guaranteed to bring the circuit to a logically consistent state. This is done by starting 
	 * the LogicEngine with all components that either have open input connections or 0 input connections
	 * @param circuitPanel The CircuitPanel
	 */
	private LogicWorker(CircuitPanel cp) {
		//Currently used by Selection.deleteSelection, Clipboard.paste, CircuitState.editState
		processType = fullProcess;
		ArrayList<LComponent> startingComps = new ArrayList<LComponent>();
		for(int i = 0; i < cp.lcomps.size(); i++) {
			LComponent lcomp = cp.lcomps.get(i);
			IOManager io = lcomp.getIO();
		
			//add component to active components if it has any open input connections or it has no input connections
			if(io.getNumInputs() == 0) startingComps.add(lcomp);
			else {
				for(int c = 0; c < io.getNumInputs(); c++) {
					if(io.connectionAt(c, Connection.INPUT).numWires() == 0) {
						startingComps.add(lcomp);
						break;
					}
				}
			}
		}
		engine = new LogicEngine(startingComps);
	}
	
	/**
	 * Prints a message to indicate that this LogicWorker has started and runs the LogicEngine.doLogic() method
	 */
	@Override
	protected Integer doInBackground() throws Exception {
		threadName = Thread.currentThread().getName();
		if(processType == singularProcess) System.out.println("Starting singular logic process on thread " + threadName);
		else System.out.println("Starting full logic process on thread " + threadName);
		return engine.doLogic();
	}
	
	/**
	 * Runs when the logic process is complete and prints a message to the console. 
	 */
	@Override
	public void done() {
		try {
			if(processType == singularProcess) System.out.println("Completed singular logic process on thread " + threadName + 
					" in " + get() + " itertations");
			else System.out.println("Completed full logic process on thread " + threadName + " in " + get() + " iterations");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new logic worker as specified by new LogicWorker(LComponent) and executes it
	 * @param lcomp The LComponent to pass to the constructor
	 */
	public static void startLogic(LComponent lcomp) {
		LogicWorker worker = new LogicWorker(lcomp);
		worker.execute();
	}
	
	/**
	 * Creates a new logic worker as specified by new LogicWorker(CircuitPanel)
	 * @param cp The CircuitPanel to pass to the constructor
	 */
	public static void startLogic(CircuitPanel cp) {
		LogicWorker worker = new LogicWorker(cp);
		worker.execute();
	}
}