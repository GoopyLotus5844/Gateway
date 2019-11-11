package com.logic.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.logic.components.Clock;
import com.logic.components.Connection;
import com.logic.components.Custom;
import com.logic.components.IOManager;
import com.logic.components.LComponent;
import com.logic.components.Wire;
import com.logic.input.Selection;
import com.logic.util.GraphicsUtils;
import com.logic.util.NameConverter;

/**
 * A panel containing GUI for editing the properties of LComponents
 * @author toddstennes
 *
 */
public class CompProperties extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The title of the properties panel
	 */
	private JLabel titleLabel;
	
	/**
	 * A label that appears when no components are selected
	 */
	private JLabel emptyLabel;
	
	/**
	 * The component name field
	 */
	private JTextField nameField;
	
	/**
	 * The component location field
	 */
	private LocationField location;
	
	/**
	 * The input spinner
	 */
	private InputSpinner inputSpinner;
	
	/**
	 * The rotation spinner
	 */
	private RotationSpinner rotationSpinner;
	
	/**
	 * The label that shows a component's connections
	 */
	private JLabel connectionLabel;
	
	/**
	 * The comment area
	 */
	private JTextArea commentArea;
	
	/**
	 * The spinner to edit the delay for clocks
	 */
	private DelaySpinner delay;
	
	/**
	 * The list of components when multiple components are selected
	 */
	private JLabel multiList;
	
	/**
	 * The panel that is displayed when one component is selected
	 */
	private JPanel singlePanel;
	
	/**
	 * The panel that is displayed when multiple components are selected
	 */
	private JPanel multiPanel;
	
	/**
	 * The Selection instance that the program is using, which is set using the setCircuitPanel() method
	 */
	private Selection selection;
	
	/**
	 * Creates a new CompProperties and displays the GUI
	 */
	public CompProperties() {
		createAndShowGUI();
	}
	
	/**
	 * Displays the GUI
	 */
	private void createAndShowGUI() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		singlePanel = new JPanel();
		singlePanel.setLayout(new BoxLayout(singlePanel, BoxLayout.Y_AXIS));
		float alignmentX = Component.LEFT_ALIGNMENT;
		
		titleLabel = new JLabel("Title");
		titleLabel.setAlignmentX(alignmentX);
		GraphicsUtils.makeBold(titleLabel);
		singlePanel.add(titleLabel);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setAlignmentX(alignmentX);
		GraphicsUtils.makeBold(nameLabel);
		singlePanel.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setMaximumSize(new Dimension(250, 30));
		nameField.setAlignmentX(alignmentX);
		addNameListener();
		singlePanel.add(nameField);
		
		location = new LocationField();
		location.setAlignmentX(alignmentX);
		location.setMaximumSize(new Dimension(250, 20));
		singlePanel.add(location);
		
		inputSpinner = new InputSpinner();
		rotationSpinner = new RotationSpinner();
		
		JPanel spinnerPanel = new JPanel();
		FlowLayout spinnerPanelLayout = new FlowLayout(FlowLayout.LEFT);
		spinnerPanelLayout.setHgap(0);
		spinnerPanel.setLayout(spinnerPanelLayout);
		spinnerPanel.setPreferredSize(new Dimension(250, 38));
		spinnerPanel.setMaximumSize(new Dimension(250, 38));
		spinnerPanel.setAlignmentX(alignmentX);
		spinnerPanel.add(inputSpinner);
		JPanel spacerPanel = new JPanel();
		spacerPanel.setPreferredSize(new Dimension(20, 20));
		spinnerPanel.add(spacerPanel);
		spinnerPanel.add(rotationSpinner);
		singlePanel.add(spinnerPanel);
		
		JLabel connectionTitle = new JLabel("Connections");
		GraphicsUtils.makeBold(connectionTitle);
		connectionTitle.setAlignmentX(alignmentX);
		singlePanel.add(connectionTitle);
		
		connectionLabel = new JLabel();
		connectionLabel.setAlignmentX(alignmentX);
		singlePanel.add(connectionLabel);
		
		JLabel commentLabel = new JLabel("Comments");
		commentLabel.setAlignmentX(alignmentX);
		commentLabel.setPreferredSize(new Dimension(200, 30));
		GraphicsUtils.makeBold(commentLabel);
		singlePanel.add(commentLabel);
		
		commentArea = new JTextArea();
		commentArea.setAlignmentX(alignmentX);
		commentArea.setLineWrap(true);
		addCommentListener();
		singlePanel.add(commentArea);
		
		delay = new DelaySpinner();
		delay.setAlignmentX(alignmentX);
		singlePanel.add(delay);
		delay.setVisible(false);
		
		singlePanel.setVisible(false);
		add(singlePanel);
		
		multiPanel = new JPanel();
		multiPanel.setLayout(new BoxLayout(multiPanel, BoxLayout.Y_AXIS));
		
		JLabel multiListTitle = new JLabel("Selected Components");
		GraphicsUtils.makeBold(multiListTitle);
		multiListTitle.setAlignmentX(alignmentX);
		multiPanel.add(multiListTitle);
		
		multiList = new JLabel();
		multiList.setAlignmentX(alignmentX);
		multiPanel.add(multiList);
		
		multiPanel.setVisible(false);
		add(multiPanel);
		
		emptyLabel = new JLabel("No components selected");
		emptyLabel.setAlignmentX(alignmentX);
		add(emptyLabel);
	}
	
	/**
	 * Refreshes the GUI to reflect the current selection
	 */
	public void refresh() {
		//Called by CircuitEditor.mouseReleased(...), Selection.select(...), Selection.clear(), Selection.rotate(...), 
		//CustomViewer.view(...), CustomViewer.exit()
		if(selection.size() == 0) {
			singlePanel.setVisible(false);
			multiPanel.setVisible(false);
			emptyLabel.setVisible(true);
		}
		if(selection.size() == 1) {
			LComponent lcomp = selection.get(0);
			multiPanel.setVisible(false);
			singlePanel.setVisible(true);
			emptyLabel.setVisible(false);
			if(lcomp instanceof Custom) titleLabel.setText("\"" + ((Custom) lcomp).getLabel() + "\" properties");
			else titleLabel.setText(NameConverter.nameFromType(lcomp.getType()) + " properties");
			if(lcomp instanceof Clock) {
				delay.setVisible(true); 
				delay.setDelayText(((Clock) lcomp).getDelay());
			}
			else delay.setVisible(false);
			nameField.setText(lcomp.getName());
			location.setPoint(new Point(lcomp.getX(), lcomp.getY()));
			inputSpinner.getSpinner().setValue(lcomp.getIO().getNumInputs());
			inputSpinner.getSpinner().setEnabled(lcomp.getIO().isInputFlexible());
			rotationSpinner.getSpinner().setValue(NameConverter.rotationFromValue(lcomp.getRotator().getRotation()));
			connectionLabel.setText(makeConnectionList(lcomp));
			commentArea.setText(lcomp.getComments());
		}
		else if(selection.size() > 1) {
			singlePanel.setVisible(false);
			multiPanel.setVisible(true);
			emptyLabel.setVisible(false);
			multiList.setText(makeMultiList());
		}
	}
	
	/**
	 * Adds a document listener to the name field to keep the saved name consistent with the name that is displayed
	 */
	private void addNameListener() {
		nameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				saveName();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				saveName();
			}
			@Override
			public void changedUpdate(DocumentEvent e) { }
		});
	}
	
	/**
	 * Adds a document listener to the comment field to keep the saved comments consistent with the comments that are displayed
	 */
	private void addCommentListener() {
		commentArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				saveComments();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				saveComments();
			}
			@Override
			public void changedUpdate(DocumentEvent e) { }
		});
	}
	
	/**
	 * Saves the name in the nameField to the currently selected LComponent
	 */
	private void saveName() {
		if(selection != null && selection.size() == 1) {
			selection.get(0).setName(nameField.getText());
		}
	}
	
	/**
	 * Saves the comments in the commentField to the currently selected LComponent
	 */
	private void saveComments() {
		if(selection != null && selection.size() == 1) {
			selection.get(0).setComments(commentArea.getText());
		}
	}
	
	/**
	 * Uses HTML to create a list of all connections that the selected component contains
	 * @param lcomp The LComponent to make a connection list for
	 * @return The String connection list, which is expressed as HTML code
	 */
	private String makeConnectionList(LComponent lcomp) {
		IOManager io = lcomp.getIO();
		String s = "<html>";
		for(int i = 0; i < io.getNumInputs(); i++) {
			Connection c = io.connectionAt(i, Connection.INPUT);
			s = s + "Input " + c.getIndex() + ":";
			if(c.numWires() == 0 || c.getWire(0).getSourceConnection() == null) s = s + " Empty<br>";
			else {
				Wire wire = c.getWire(0);
				Connection connect = wire.getSourceConnection();
				s = s + " " + connect.getLcomp().toString() + ", " + NameConverter.nameFromSignal(wire.getSignal()) + "<br>";
			}
		}
		for(int i = 0; i < io.getNumOutputs(); i++) {
			Connection c = io.connectionAt(i, Connection.OUTPUT);
			s = s + "Output " + c.getIndex() + ":";
			if(c.numWires() == 0 || c.getWire(0).getDestConnection() == null) s = s + " Empty<br>";
			else if(c.numWires() == 1) {
				Wire wire = c.getWire(0);
				Connection connect = wire.getDestConnection();
				s = s + " " + connect.getLcomp().toString() + ", " + NameConverter.nameFromSignal(wire.getSignal()) + "<br>";
			}
			else {
				s = s + "<ul>";
				for(int w = 0; w < c.numWires(); w++) {
					Wire wire = c.getWire(w);
					Connection connect = wire.getDestConnection();
					if(connect != null) {
						s = s + "<li>" + connect.getLcomp().toString() + ", " + 
								NameConverter.nameFromSignal(wire.getSignal()) + "</li>";
					}
				}
				s = s + "</ul>";
			}
		}
		s = s + "<html>";
		return s;
	}
	
	/**
	 * Makes a list of all of the selected components
	 * @return The list of components, expressed as HTML code
	 */
	private String makeMultiList() {
		String s = "<html><ul>";
		for(int i = 0; i < selection.size(); i++) {
			s = s + "<li>" + selection.get(i).toString() + "</li>";
		}
		s = s + "</ul></html>";
		return s;
	}
	
	/**
	 * Sets the CircuitPanel being used by this CompProperties and updates its reference to the Selection
	 * @param cp The CircuitPanel
	 */
	public void setCircuitPanel(CircuitPanel cp) {
		location.setCircuitPanel(cp);
		inputSpinner.setCircuitPanel(cp);
		rotationSpinner.setCircuitPanel(cp);
		delay.setCircuitPanel(cp);
		selection = cp.getEditor().getSelection();
	}

}
