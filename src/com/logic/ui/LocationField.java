package com.logic.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.logic.components.LComponent;
import com.logic.input.Selection;
import com.logic.util.GraphicsUtils;

/**
 * A JPanel that holds JLabels and JTextField for describing an (x, y) location and has functionality for moving JComponents accordingly
 * @author toddstennes
 *
 */
public class LocationField extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The width of each field
	 */
	private final int fieldWidth = 50;
	
	/**
	 * The height of each field
	 */
	private final int fieldHeight = 20;
	
	/**
	 * The name of this location field
	 */
	private JLabel label;
	
	/**
	 * The text fields
	 */
	private JTextField xField, yField;
	
	/**
	 * The Selection
	 */
	private Selection selection;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * Constructs a new LocationField
	 */
	public LocationField() {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(0);
		setLayout(layout);
		label = new JLabel("Location: ");
		GraphicsUtils.makeBold(label);
		add(label);
		add(new JLabel(" (x ="));
		xField = new JTextField();
		xField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
		add(xField);
		add(new JLabel(", y ="));
		yField = new JTextField();
		yField.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
		add(yField);
		add(new JLabel(")"));
		xField.getDocument().addDocumentListener(makeDocumentListener());
		yField.getDocument().addDocumentListener(makeDocumentListener());
	}
	
	/**
	 * Creates a document listener for the text field that changes the location of the selected component
	 * @return A new DocumentListener 
	 */
	public DocumentListener makeDocumentListener() {
		return new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				moveComponent();
				if(cp != null) cp.repaint();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				moveComponent();
				if(cp != null) cp.repaint();
			}

			@Override
			public void changedUpdate(DocumentEvent e) { }
		};
	}
	
	/**
	 * Moves the currently selected component to the location described by the location fields
	 */
	private void moveComponent() {
		if(selection != null && selection.size() == 1) {
			LComponent lcomp = selection.get(0);
			try {
				lcomp.setX(Integer.parseInt(xField.getText()));
				lcomp.setY(Integer.parseInt(yField.getText()));
			} catch(NumberFormatException e) { }
		}
	}
	
	/**
	 * Returns the Point that this location field describes
	 * @return The Point of this LocationField
	 */
	public Point getPoint() {
		return new Point(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
	}
	
	/**
	 * Changes the text to match the given point
	 * @param p The point to change the text to
	 */
	public void setPoint(Point p) {
		xField.setText(Integer.toString(p.x));
		yField.setText(Integer.toString(p.y));
	}
	
	/**
	 * Sets the CircuitPanel
	 * @param cp The CircuitPanel
	 */
	public void setCircuitPanel(CircuitPanel cp) {
		this.cp = cp;
		selection = cp.getEditor().getSelection();
	}
	
}
