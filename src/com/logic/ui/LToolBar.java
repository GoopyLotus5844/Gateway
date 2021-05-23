package com.logic.ui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.logic.main.FileManager;
import com.logic.main.LogicSimApp;

/**
 * A tool bar for the program
 * @author toddstennes
 *
 */
public class LToolBar extends JToolBar implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The number of items before each tool bar divider (buttons are organized by function
	 */
	private final int dividerIndex1 = 3, dividerIndex2 = 6, dividerIndex3 = 11;
	
	/**
	 * The names of each button
	 */
	private String[] tooltips = new String[] {"New", "Open", "Save", "Select", "Pan", "Insert", "Undo", "Redo", "Cut", "Copy", "Paste", 
			"Delete", "Rotate Counter-Clockwise", "Rotate Clockwise", "Create Custom Component"};
	
	/**
	 * The button collection, used for the select, pan, and insert toggle buttons
	 */
	private AbstractBCollection buttonCollection;
	
	/**
	 * A constant that specifies that the state of a toggle button was changed by the user
	 */
	public static final int DIRECT = 0;
	
	/**
	 * A constant that specifies that the state of a toggle button was changed internally by the program
	 */
	public static final int INTERNAL = 1;
	
	/**
	 * A constant that specifies that the state of a toggle button was changed using the tools menu in the menu bar
	 */
	public static final int MENU = 2;
	
	/**
	 * The InsertPanel
	 */
	private InsertPanel insertPanel;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The LMenuBar
	 */
	private LMenuBar menuBar;
	
	/**
	 * The file manager
	 */
	private FileManager fileManager;
		
	/**
	 * Constructs a new LToolBar
	 */
	public LToolBar() { 
		buttonCollection = new AbstractBCollection();
		createButtons();
	}
	
	/**
	 * Creates all of the buttons used by the tool bar according to the instance variables
	 */
	private void createButtons() {
		for(int i = 0; i < dividerIndex1; i++) {
			JButton button = new JButton(getToolbarIcon(i));
			button.setToolTipText(tooltips[i]);
			button.setActionCommand(tooltips[i]);
			button.addActionListener(this);
			add(button);
		}
		
		addSeparator();
		
		for(int i = dividerIndex1; i < dividerIndex2; i++) {
			JToggleButton button = new JToggleButton(getToolbarIcon(i));
			button.setToolTipText(tooltips[i]);
			button.setActionCommand(tooltips[i]);
			if(i == dividerIndex2 - 1) button.setSelected(true);
			button.addActionListener(this);
			buttonCollection.add(button, tooltips[i]);
			add(button);
		}
		
		addSeparator();
		
		for(int i = dividerIndex2; i < dividerIndex3; i++) {
			JButton button = new JButton(getToolbarIcon(i));
			button.setToolTipText(tooltips[i]);
			button.setActionCommand(tooltips[i]);
			button.addActionListener(this);
			add(button);
		}
		
		addSeparator();
		
		for(int i = dividerIndex3; i < tooltips.length; i++) {
			JButton button = new JButton(getToolbarIcon(i));
			button.setToolTipText(tooltips[i]);
			button.setActionCommand(tooltips[i]);
			button.addActionListener(this);
			add(button);
		}
	
	}
	
	/**
	 * Returns a correctly scaled version of the tool bar icon at the specified index in the IconLoader
	 * @param index The index of the icon in the IconLoader.toolbarIcons array
	 * @return The icon
	 */
	private ImageIcon getToolbarIcon(int index) {
		if(index <= 5) return LogicSimApp.iconLoader.toolBarIcons[index];
		ImageIcon original = LogicSimApp.iconLoader.toolBarIcons[index];
		return IconLoader.getScaledImage(original, original.getIconWidth() * 2, original.getIconHeight() * 2);
	}
	
	/**
	 * Performs the necessary action to respond to a button press (not complete)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Pan")) changeToPan(DIRECT);
		else if(command.equals("Select")) changeToSelect(DIRECT);
		else if(command.equals("Insert")) changeToInsert(DIRECT, true);
		else if(command.equals("Delete")) cp.getEditor().deleteElements();
		else if(command.equals("Cut")) cp.getEditor().getSelection().cut();
		else if(command.equals("Copy")) cp.getEditor().getSelection().copy();
		else if(command.equals("Paste")) cp.getEditor().paste();
		else if(command.equals("Rotate Counter-Clockwise")) cp.getEditor().getSelection().rotate(CompRotator.COUNTER_CLOCKWISE);
		else if(command.equals("Rotate Clockwise")) cp.getEditor().getSelection().rotate(CompRotator.CLOCKWISE);
		else if(command.equals("Undo")) cp.getEditor().getRevision().undo();
		else if(command.equals("Redo")) cp.getEditor().getRevision().redo();
		else if(command.equals("New")) LogicSimApp.newWindow(null);
		else if(command.equals("Open")) fileManager.open();
		else if(command.equals("Save")) fileManager.save();
		else if(command.equals("Create Custom Component")) cp.getEditor().getCustomCreator().createCustom();
	}
	
	/**
	 * Changes to pan mode
	 * @param type The type of change (INTERNAL, DIRECT, or MENU)
	 */
	public void changeToPan(int type) {
		if(type == INTERNAL || type == MENU) buttonCollection.select("Pan");
		if(type != MENU) menuBar.choosePan();
		cp.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Changes to select mode 
	 * @param type The type of change (INTERNAL, DIRECT, or MENU)
	 */
	public void changeToSelect(int type) {
		if(type == INTERNAL || type == MENU) buttonCollection.select("Select");
		if(type != MENU) menuBar.chooseSelect();
		cp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		insertPanel.clear();
	}
	
	/**
	 * Changes to insert mode. If restoreSelection is true, then restoreSelection will be called on the InsertPanel's button collection. This
	 * value should only be set to false if this method is being called by InsertPanel
	 * @param type The type of change (INTERNAL, DIRECT, or MENU)
	 * @param 
	 */
	public void changeToInsert(int type, boolean restoreSelection) {
		if(type == INTERNAL || type == MENU) buttonCollection.select("Insert");
		if(type != MENU) menuBar.chooseInsert();
		cp.getEditor().getSelection().clear();
		cp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		if(restoreSelection) insertPanel.restore();
	}
	
	/**
	 * Returns the name of the tool button that is currently selected ("Insert", "Pan", or "Select")
	 * @return The selected button name
	 */
	public String getToolMode() {
		return buttonCollection.getSelectedButtonName();
	}
	
	/**
	 * Sets the InsertPanel
	 * @param insertPanel The InsertPanel
	 */
	public void setInsertPanel(InsertPanel insertPanel) {
		this.insertPanel = insertPanel;
	}
	
	/**
	 * Sets the CircuitPanel
	 * @param cp The CircuitPanel
	 */
	public void setCircuitPanel(CircuitPanel cp) {
		this.cp = cp;
	}
	
	/**
	 * Sets the LMenuBar
	 * @param menuBar The LMenuBar
	 */
	public void setLMenuBar(LMenuBar menuBar) {
		this.menuBar = menuBar;
	}
	
	/**
	 * Sets the FileManager 
	 * @param fileManager The FileManager
	 */
	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
}
