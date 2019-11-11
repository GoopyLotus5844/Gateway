package com.logic.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.logic.main.LogicSimApp;

/**
 * The panel of buttons to insert LComponents
 * @author toddstennes
 *
 */
public class InsertPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The names of the components in the InsertPanel, which are used for showing tool tips and by the Inserter
	 */
	private String[] logicNames = new String[] {"Buffer", "Not", "And", "Nand", "Or", "Nor", "Xor", "Xnor", "Clock", "Light", "Switch", 
			"Zero", "One", "Button", "Display"};
	/**
	 * The values by which to scale each logic image, in the same order as the array of logic names
	 */
	private float[] scales = new float[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3.5f};
	
	/**
	 * The indexes in the logic image array to skip (because they are different versions of the same component)
	 */
	private int[] skipImageIndexes = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 25, 29};
	
	/**
	 * The collection of buttons
	 */
	private AbstractBCollection buttonCollection;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * The tool bar
	 */
	private LToolBar toolbar;

	/**
	 * Constructs a new InsertPanel
	 * @param toolbar The tool bar
	 */
	public InsertPanel(LToolBar toolbar) {
		this.toolbar = toolbar;
		buttonCollection = new AbstractBCollection();
		setLayout(new FlowLayout(FlowLayout.LEFT));
		createButtons();
	}

	/**
	 * Creates the buttons
	 */
	private void createButtons() {
		int indexOffset = 0;
		for(int i = 0; i < logicNames.length; i++) {
			while(contains(skipImageIndexes, i + indexOffset)) indexOffset++;
			JToggleButton button = new JToggleButton(getLogicIcon(i + indexOffset, i));
			button.setToolTipText(logicNames[i]);
			button.setActionCommand(logicNames[i]);
			if(i == 0) button.setSelected(true);
			button.addActionListener(this);
			button.setPreferredSize(new Dimension(70, 70));
			buttonCollection.add(button, logicNames[i]);
			add(button);
		}
	}
	
	/**
	 * Returns the scaled version of the specified logic image
	 * @param imageIndex The index in the logicImages array in IconLoader
	 * @param arrIndex The index in the logicNames array
	 * @return
	 */
	private ImageIcon getLogicIcon(int imageIndex, int arrIndex) {
		ImageIcon original = LogicSimApp.iconLoader.logicIcons[imageIndex];
		return IconLoader.getScaledImage(original, (int) (original.getIconWidth() * scales[arrIndex]), 
				(int) (original.getIconHeight() * scales[arrIndex]));
	}
	
	/**
	 * Tells whether the given array contains the given value
	 * @param arr The array to consider
	 * @param x The value to search for
	 * @return A boolean telling whether the given array contains the value
	 */
	private boolean contains(int[] arr, int x) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == x) return true;
		}
		return false;
	}
	
	/**
	 * Prepares for insertion; 
	 * Sets the tool bar mode to insert, clears the selection, sets the cursor to the default cursor, and repaints the CircuitPanel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		toolbar.changeToInsert(LToolBar.INTERNAL, false);
		cp.repaint();
	}
	
	/**
	 * Returns the name of the selected component button
	 * @return The selected component name
	 */
	public String getSelectedComponent() {
		return buttonCollection.getSelectedButtonName();
	}
	
	/**
	 * Restores the last recorded selected button from before this InsertPanel was cleared using the AbstractBCollection.restoreSelection()
	 * method
	 */
	public void restore() {
		buttonCollection.restoreSelection();
	}
	
	/**
	 * De-selects all buttons by calling AbstractBCollection.clearSelection()
	 */
	public void clear() {
		buttonCollection.clearSelection();
	}
	
	/**
	 * Sets the CircuitPanel
	 * @param cp The CircuitPanel
	 */
	public void setCircuitPanel(CircuitPanel cp) {
		this.cp = cp;
	}
}
