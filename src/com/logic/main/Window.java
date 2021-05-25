package com.logic.main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.logic.ui.CircuitPanel;
import com.logic.ui.CompProperties;
import com.logic.ui.InsertPanel;
import com.logic.ui.LMenuBar;
import com.logic.ui.LToolBar;
import com.logic.ui.ZoomSlider;

/**
 * This class represents one window of the application. Each window holds completely separate data and is essentially its own program
 * @author toddstennes
 *
 */
public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new Window with a blank CircuitPanel
	 */
	public Window() {
		super("Gateway - Untitled Circuit");
		createAndShowGUI(null);
	}
	
	/**
	 * Creates a new Window and loads the given file into the CircuitPanel
	 * @param path
	 */
	public Window(String path) {
		super("Gateway - " + path);
		FileManager manager = createAndShowGUI(path);
		manager.openFile(path, false);
	}
	
	/**
	 * Instantiates all main classes that the program uses and displays the GUI
	 * @param The path of the file in the new window, which can be null if it is a new file
	 * @return A FileManager that reflects the given path or lack thereof
	 */
	private FileManager createAndShowGUI(String path) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(layout);
		
		LToolBar toolbar = new LToolBar();
		toolbar.setMaximumSize(new Dimension(getWidth(), 50));
		toolbar.setPreferredSize(new Dimension(getWidth(), 50));
		toolbar.setAlignmentX(Component.CENTER_ALIGNMENT);
		toolbar.setFloatable(false);
		mainPanel.add(toolbar);
		
		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
		centralPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		
		InsertPanel insertPanel = new InsertPanel(toolbar);
		JScrollPane insertScroll = new JScrollPane(insertPanel);
		insertScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		toolbar.setInsertPanel(insertPanel);
		centralPanel.add(insertScroll);
		
		CompProperties properties = new CompProperties();
		JScrollPane propertiesScroll = new JScrollPane(properties);
		
		CircuitPanel cp = new CircuitPanel(this, properties, toolbar, insertPanel);
		toolbar.setCircuitPanel(cp);
		insertPanel.setCircuitPanel(cp);
		properties.setCircuitPanel(cp);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, propertiesScroll, cp);
		splitPane.setDividerLocation(280);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		centralPanel.add(splitPane);
		mainPanel.add(centralPanel);
		
		ZoomSlider slider = new ZoomSlider(cp);
		slider.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(slider);
		cp.setZoomSlider(slider);
		slider.updatePosition();
		
		FileManager fileManager;
		if(path == null) fileManager = new FileManager(cp);
		else fileManager = new FileManager(cp, path);
		toolbar.setFileManager(fileManager);
		LMenuBar menuBar = new LMenuBar(cp, toolbar, fileManager);
		toolbar.setLMenuBar(menuBar);
		setJMenuBar(menuBar);
		
		add(mainPanel);
		setVisible(true);
		return fileManager;
	}
	
	/**
	 * Adds "Gateway - " to the given String and sets it as the title of the JFrame
	 */
	@Override
	public void setTitle(String s) {
		s = "Gateway - " + s;
		super.setTitle(s);
	}
	
}
