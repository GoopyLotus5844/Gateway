package com.logic.ui;

import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * A group of abstract buttons that provides functionality for managing them, including a ButtonGroup
 * @author toddstennes
 *
 */
public class AbstractBCollection {

	/**
	 * The ButtonGroup that the buttons will be added to
	 */
	private ButtonGroup group;
	
	/**
	 * A list of all of the buttons that have been added to this AbstractBCollection
	 */
	private ArrayList<AbstractButton> buttons;
	
	/**
	 * The names of each button, in the same order as the button list
	 */
	private ArrayList<String> names;
	
	/**
	 * The button that was selected at the last time that the selection was cleared
	 */
	private AbstractButton selectedBeforeClear;
	
	/**
	 * True is the collection has no selected buttons, false otherwise
	 */
	private boolean cleared = false;
	
	/**
	 * Constructs a new AbstractBCollection
	 */
	public AbstractBCollection() {
		group = new ButtonGroup();
		buttons = new ArrayList<AbstractButton>();
		names = new ArrayList<String>();
	}
	
	/**
	 * Adds an AbstractButton to the list of buttons and gives it a name
	 * @param b The button to add
	 * @param name The name of the button
	 */
	public void add(AbstractButton b, String name) {
		group.add(b);
		buttons.add(b);
		names.add(name);
	}

	/**
	 * Selects the button with the given name
	 * @param name The name of the button to select
	 */
	public void select(String name) {
		cleared = false;
		buttons.get(indexOf(name)).setSelected(true);
	}
	
	/**
	 * Deselects all of the buttons and saves the button that was selected before the clear
	 */
	public void clearSelection() {
		selectedBeforeClear = getSelectedButton();
		group.clearSelection();
		cleared = true;
	}
	
	/**
	 * Re-selects the button that was last deselected as a result of the selection being cleared
	 */
	public void restoreSelection() {
		if(cleared && selectedBeforeClear != null) selectedBeforeClear.setSelected(true);
		cleared = false;
	}
	
	/**
	 * Returns the button that is currently selected
	 * @return The button that is currently selected
	 */
	public AbstractButton getSelectedButton() {
		for(int i = 0; i < buttons.size(); i++) {
			if(buttons.get(i).isSelected()) return buttons.get(i);
		}
		return null;
	}
	
	/**
	 * Returns the name of the button that is currently selected
	 * @return
	 */
	public String getSelectedButtonName() {
		for(int i = 0; i < buttons.size(); i++) {
			if(buttons.get(i).isSelected()) return names.get(i);
		}
		return null;
	}
	
	/**
	 * Returns the index of the button that has the given name
	 * @param name The name to search for
	 * @return The index of the button that has the given name
	 */
	public int indexOf(String name) {
		return names.indexOf(name);
	}
}
