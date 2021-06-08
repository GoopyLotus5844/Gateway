package com.logic.util;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import com.logic.components.LComponent;
import com.logic.ui.CompDrawer;
import com.logic.ui.CompRotator;

/**
 * A class that provides helper methods for the setup and functioning of a Custom component
 * @author toddstennes
 *
 */
public class CustomHelper {
	
	/**
	 * The space between each connection
	 */
	private final int connectionSpacing = 50;
	
	/**
	 * The minimum size for the width or height of a component.
	 */
	private final int minSize = 7;
	
	/**
	 * The map of lights and switches from the CustomCreator
	 */
	private HashMap<Integer, LComponent[]> content;
	
	/**
	 * Constructs a new CustomHelper
	 * @param content The content map given to the constructor of the custom component
	 */
	public CustomHelper(HashMap<Integer, LComponent[]> content) {
		this.content = content;
	}
	
	/**
	 * Chooses an appropriate width for the custom component. This is done by taking the maximum between the width required
	 * in order to fit the label and the width required to fit the number of connections.
	 * @param label The label of the custom component
	 * @param labelFont The font to be used for the label
	 * @return The width of the Custom component
	 */
	public int chooseWidth(String label, Font labelFont) {
		int firstDimension = findDimension(content.get(CompRotator.UP), content.get(CompRotator.DOWN));
		FontRenderContext frc = new FontRenderContext(labelFont.getTransform(), true, true);
		Rectangle2D bounds = labelFont.getStringBounds(label, frc);
		int secondDimension = (int) (Math.floor(bounds.getWidth()) + 20);
		//TODO fix the above line to use appropriate padding
		return Math.max(firstDimension, secondDimension);
	}
	
	/**
	 * Chooses an appropriate height for the custom component in large pixels. This is done by choosing the smallest value that allows for 
	 * the connections to be evenly spaced and centered according to the connectionSpacing constant.
	 * @return
	 */
	public int chooseHeight() {
		return findDimension(content.get(CompRotator.LEFT), content.get(CompRotator.RIGHT));
	}
	
	/**
	 * Returns the list of points that each connection should be placed at for the given side. The points will be spaced apart by the 
	 * connectionSpacing constant and the group of points will be centered within their side of the component.
	 * @param sideNum The CompRotator constant that represents the side 
	 * @param width The width of the custom component, as returned by chooseWidth
	 * @param height The height of the custom component, as returned by chooseHeight
	 * @return A list of points to place the connections at
	 */
	public Point[] getConnectionPoints(int sideNum, int width, int height) {
		int num = countConnections(content.get(sideNum));
		Point[] points = new Point[num];
		int connectionSpace = num + (num - 1) * connectionSpacing;	
		int pos;
		if(sideNum == CompRotator.LEFT || sideNum == CompRotator.RIGHT) pos = ((height + 4) - connectionSpace) / 2;
		else pos = ((width + 4) - connectionSpace) / 2;
		
		for(int i = 0; i < points.length; i++) {
			if(sideNum == CompRotator.LEFT) points[i] = new Point(0, pos);
			else if(sideNum == CompRotator.UP) points[i] = new Point(pos, 0);
			else if(sideNum == CompRotator.DOWN) points[i] = new Point(pos, height + 3);
			else if(sideNum == CompRotator.RIGHT) points[i] = new Point(width + 3, pos);
			pos += connectionSpacing + 1;
		}
		return points;
	}
	
	/**
	 * Finds the large pixel dimension required in order to fit the given lists of components on the top/bottom or the left/right
	 * @param one The first list of lights and switches (left or top)
	 * @param two The second list of lights and switches (right or bottom)
	 * @return The minimum dimension that fits the given inputs and outputs
	 */
	private int findDimension(LComponent[] one, LComponent[] two) {
		int firstDimension = Math.max(minSize, connectionSpacing * countConnections(one) + 20);
		int secondDimension = Math.max(minSize, connectionSpacing * countConnections(two) + 20);
		return Math.max(firstDimension, secondDimension);
	}
	
	/**
	 * Counts the number of connections that are represented by the given array of Lights and Switches, without causing a null pointer if
	 * the array is null
	 * @param side The array
	 * @return The number of connections
	 */
	private int countConnections(LComponent[] side) {
		if(side != null) return side.length;
		return 0;
	}

}
