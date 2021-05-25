package com.logic.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

import com.logic.engine.LogicFunctions;
import com.logic.ui.IconLoader;

/**
 * A digital logic simulator meant as an alternative to applications like Logicly and Boolr.
 * @author GoopyLotus5844
 */
public class LogicSimApp {

	/**
	 * OS type, used to determine if the look and feel should be switched from default java to windows
	 */
	public static String OS = System.getProperty("os.name").toLowerCase();

	/**
	 * The IconLoader for the program, which is responsible for loading and holding all of the images that the program uses
	 */
	public static IconLoader iconLoader;
	
	/**
	 * The list of all currently open windows, used for closing the application when there are none left
	 */
	public static ArrayList<Window> windows;
	
	/**
	 * Starts the program 
	 */
	public LogicSimApp() {
		iconLoader = new IconLoader();
		windows = new ArrayList<Window>();
		LogicFunctions.setFunctions();
		newWindow(null);
	}
	
	/**
	 * Opens a new window that contains the given file. If path is equal to null, then a blank window is opened
	 * @param path The file path, null if the window is to be a new file
	 */
	public static void newWindow(String path) {
		Window window;
		if(path != null) window = new Window(path);
		else window = new Window();
		
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				windows.remove(window);
				if(windows.size() == 0) System.exit(0);
			}
		});
		windows.add(window);
	}
	
	/**
	 * The main method of the program, which creates a new LogicSimApp on the event dispatch thread through the use of SwingUtilities.
	 * invokeLater(...)
	 * @param args The arguments to the main method, which have no effect whatsoever on anything
	 */
	public static void main(String[] args) {
		if(OS.contains("win")) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		}
		SwingUtilities.invokeLater(LogicSimApp::new);
		/**
		 * To do list:
		 * Make circuit editor add to selection instead of clearing selection if shift key is down *
		 * Add component properties ui *
		 * Make wires selectable *
		 * Add framework for increasing inputs (implemented through CompProperties) *
		 * Add framework for 90 degree component rotation (implemented through CompProperties) *
		 * Improve performance at this point because its probably gotten really slow and buggy *
		 * Add undo and redo functions *
		 * Add ability to save and load files *
		 * Add ability to create ICs *
		 * Add built in latches, flip flops, and random generator
		 * Make all menu items functional *
		 * Text labels (implemented through CompProperties)
		 * Keyboard shortcuts (such as press A for and, O for or)
		 * Add circuit timing diagrams for ICs
		 * Add ability to print circuits
		 * Add ability to move components with arrow keys
		 * Add ability to reshape wires
		 */
		
		/**
		 * Issues
		 * Trying to drag to create wires is janky *
		 * Somehow it saved custom_components.gtw.gtw instead of saving over the first *
		 * Dragging when zoomed in is janky *
		 * Magic unicorn properties *
		 * The CircuitEditor and supporting input classes are disorganized *
		 * The location field in the properties can malfunction
		 * Opening a file while the app is maximized results in a window with a strange layout at the top
		 * The insert and select options are redundant; there is no reason to ever use them
		 * Make scroll to zoom less jank
		 */
	}
}
