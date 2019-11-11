package com.logic.ui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.logic.main.FileManager;
import com.logic.main.LogicSimApp;

/**
 * A menu bar for the program
 * @author toddstennes
 *
 */
public class LMenuBar extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The radio buttons in the tools menu
	 */
	private JRadioButtonMenuItem insert, pan, select;
	
	/**
	 * The check box buttons in the view menu
	 */
	private JCheckBoxMenuItem snap, showGrid;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The LToolBar
	 */
	private LToolBar toolbar;
	
	/**
	 * The FileManager
	 */
	private FileManager fileManager;
	
	/**
	 * Constructs a new LMenuBar
	 */
	public LMenuBar(CircuitPanel cp, LToolBar toolbar, FileManager fileManager) {
		this.cp = cp;
		this.toolbar = toolbar;
		this.fileManager = fileManager;
		createMenu();
	}
	
	/**
	 * Creates the GUI for the menu bar
	 */
	private void createMenu() {
		JMenu menu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("New", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Creates a new document");
		addListener(menuItem, "New");
		menu.add(menuItem);
		menuItem = new JMenuItem("Open...", KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Opens a document from the filesystem");
		addListener(menuItem, "Open");
		menu.add(menuItem);
		menuItem = new JMenuItem("Save", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Saves this document");
		addListener(menuItem, "Save");
		menu.add(menuItem);
		menuItem = new JMenuItem("Save as...", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Saves this document to a specified location");
		addListener(menuItem, "Save as");
		menu.add(menuItem);
		add(menu);
 
		menu = new JMenu("Edit");
		menuItem = new JMenuItem("Undo", KeyEvent.VK_Z);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Reverts the previous action");
		addListener(menuItem, "Undo");
		menu.add(menuItem);
		menuItem = new JMenuItem("Redo", KeyEvent.VK_Y);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Undos an undo");
		addListener(menuItem, "Redo");
		menu.add(menuItem);
		menuItem = new JMenuItem("Cut", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Cuts the current selection");
		addListener(menuItem, "Cut");
		menu.add(menuItem);
		menuItem = new JMenuItem("Copy", KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Copys the current selection");
		addListener(menuItem, "Copy");
		menu.add(menuItem);
		menuItem = new JMenuItem("Paste", KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Pastes a copied selection");
		addListener(menuItem, "Paste");
		menu.add(menuItem);
		add(menu);
		
		menu = new JMenu("View");
		menuItem = new JMenuItem("Zoom in", KeyEvent.VK_PLUS);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Reverts the previous action");
		addListener(menuItem, "Zoom in");
		menu.add(menuItem);
		menuItem = new JMenuItem("Zoom out", KeyEvent.VK_MINUS);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Undos an undo");
		addListener(menuItem, "Zoom out");
		menu.add(menuItem);		
		menu.addSeparator();	 
		JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("Snap to grid");
		addListener(cbMenuItem, "Snap");
		snap = cbMenuItem;
		menu.add(cbMenuItem);
		cbMenuItem = new JCheckBoxMenuItem("Show grid");
		cbMenuItem.setSelected(true);
		addListener(cbMenuItem, "Show");
		showGrid = cbMenuItem;
		menu.add(cbMenuItem);
		add(menu);
		
		menu = new JMenu("Tools");
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Select");
		addListener(rbMenuItem, "Select");
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		select = rbMenuItem;
		rbMenuItem = new JRadioButtonMenuItem("Pan");
		addListener(rbMenuItem, "Pan");
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		pan = rbMenuItem;
		rbMenuItem = new JRadioButtonMenuItem("Insert");
		rbMenuItem.setSelected(true);
		addListener(rbMenuItem, "Insert");
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		insert = rbMenuItem;
		add(menu);
		
		menu = new JMenu("Component");
		menuItem = new JMenuItem("Delete");
		menuItem.getAccessibleContext().setAccessibleDescription("Deletes all selected components");
		addListener(menuItem, "Delete");
		menu.add(menuItem);
		menuItem = new JMenuItem("Rotate clockwise", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Rotates the selection clockwise");
		addListener(menuItem, "Clockwise");
		menu.add(menuItem);
		menuItem = new JMenuItem("Rotate counter-clockwise", KeyEvent.VK_K);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		menuItem.getAccessibleContext().setAccessibleDescription("Rotates the selection counter-clockwise");
		addListener(menuItem, "Counter");
		menu.add(menuItem);
		menuItem = new JMenuItem("Create custom component");
		menuItem.getAccessibleContext().setAccessibleDescription("Creates a custom component from the current selection");
		addListener(menuItem, "Custom");
		menu.add(menuItem);
		add(menu);
	}
	
	/**
	 * Adds the LMenuBar as an action listener for the given button and sets its action command to the specified string. 
	 * @param b The AbstractButton
	 * @param command The action command string
	 */
	private void addListener(AbstractButton b, String command) {
		b.setActionCommand(command);
		b.addActionListener(this);
	}

	/**
	 * Performs one of the actions necessary to respond to an item in this menu 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Select")) toolbar.changeToSelect(LToolBar.MENU);
		else if(command.equals("Pan")) toolbar.changeToPan(LToolBar.MENU);
		else if(command.equals("Insert")) toolbar.changeToInsert(LToolBar.MENU, true);
		else if(command.equals("Undo")) cp.getEditor().getRevision().undo();
		else if(command.equals("Redo")) cp.getEditor().getRevision().redo();
		else if(command.equals("Copy")) cp.getEditor().getSelection().copy();
		else if(command.equals("Cut")) cp.getEditor().getSelection().cut();
		else if(command.equals("Paste")) cp.getEditor().paste();
		else if(command.equals("Zoom in")) cp.getCamera().zoomIn();
		else if(command.equals("Zoom out")) cp.getCamera().zoomOut();
		else if(command.equals("Snap")) cp.getEditor().setSnap(snap.isSelected());
		else if(command.equals("Show")) cp.setShowGrid(showGrid.isSelected());
		else if(command.equals("New")) LogicSimApp.newWindow(null);
		else if(command.equals("Open")) fileManager.open();
		else if(command.equals("Save")) fileManager.save();
		else if(command.equals("Save as")) fileManager.saveAs();
		else if(command.equals("Delete")) cp.getEditor().deleteElements();
		else if(command.equals("Clockwise")) cp.getEditor().getSelection().rotate(CompRotator.CLOCKWISE);
		else if(command.equals("Counter")) cp.getEditor().getSelection().rotate(CompRotator.COUNTER_CLOCKWISE);
		else if(command.equals("Custom")) cp.getEditor().getCustomCreator().createCustom();
	}
	
	/**
	 * Sets the insert radio button in the menu to appear selected
	 */
	public void chooseInsert() {
		insert.setSelected(true);
	}
	
	/**
	 * Sets the pan radio button in the menu to appear selected
	 */
	public void choosePan() {
		pan.setSelected(true);
	}
	
	/**
	 * Sets the select radio button in the menu to appear selected
	 */
	public void chooseSelect() {
		select.setSelected(true);
	}
}
