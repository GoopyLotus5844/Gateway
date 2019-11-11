package com.logic.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.logic.components.Connection;
import com.logic.components.Custom;
import com.logic.components.IOManager;
import com.logic.components.LComponent;
import com.logic.components.Light;
import com.logic.components.Switch;
import com.logic.components.Wire;
import com.logic.ui.CompRotator;

/**
 * A class that holds static methods for performing various operations on lists of LComponents
 * @author toddstennes
 *
 */
public class CompUtils {

	/**
	 * Creates a deep clone of the given list of LComponents without adding any offset
	 * @param lcomps The LComponents to duplicate
	 * @param cp The CircuitPanel
	 * @return The list of duplicated components
	 */
	public static ArrayList<LComponent> duplicate(List<LComponent> lcomps){
		return duplicate(lcomps, new Point(0, 0));
	}
	
	/**
	 * Creates a deep clone of the given list of LComponents. This is a complicated process that was one of the most annoying parts of 
	 * creating this application.
	 * @param lcomps The LComponents to duplicate
	 * @param cp The CircuitPanel
	 * @param offset The amount by which to translate the duplicated components from their original positions
	 * @return The list of duplicated components
	 */
	public static ArrayList<LComponent> duplicate(List<LComponent> lcomps, Point offset) {
		HashMap<LComponent, LComponent> oldToNew = new HashMap<LComponent, LComponent>();
		ArrayList<LComponent> newComps = new ArrayList<LComponent>();
		ArrayList<Wire> oldWires = new ArrayList<Wire>();
		
		for(int l = 0; l < lcomps.size(); l++) { 
			LComponent oldComp = lcomps.get(l);
			IOManager oldIO = oldComp.getIO();
			LComponent newComp = oldComp.makeCopy();
			newComp.setX(oldComp.getX() + offset.x);
			newComp.setY(oldComp.getY() + offset.y);
			oldToNew.put(oldComp, newComp);
			newComps.add(newComp);
			for(int c = 0; c < oldIO.getNumInputs(); c++) {
				Connection connection = oldIO.connectionAt(c, Connection.INPUT);
				if(connection.numWires() == 1) {
					Wire oldWire = connection.getWire();
					if(lcomps.contains(oldWire.getSourceConnection().getLcomp())) oldWires.add(oldWire);
				}
			}
		}

		for(int w = 0; w < oldWires.size(); w++) {
			Wire oldWire = oldWires.get(w);
			Connection oldSourceConnection = oldWire.getSourceConnection();
			Connection oldDestConnection = oldWire.getDestConnection();
			LComponent newSourceComp = oldToNew.get(oldSourceConnection.getLcomp());
			LComponent newDestComp = oldToNew.get(oldDestConnection.getLcomp());
			Connection newSourceConnection = newSourceComp.getIO().connectionAt(oldSourceConnection.getIndex(), Connection.OUTPUT);
			Connection newDestConnection = newDestComp.getIO().connectionAt(oldDestConnection.getIndex(), Connection.INPUT);
			Wire newWire = new Wire();
			newWire.setSignal(oldWire.getSignal());
			newSourceConnection.addWire(newWire);
			newDestConnection.addWire(newWire);
		}
		
		return newComps;
	}
	
	/**
	 * Makes a copy of the given Custom component by duplicating its inner components
	 * @param custom The Custom component to copy
	 * @param cp The CircuitPanel
	 * @return A copy of the Custom component
	 */
	public static Custom duplicateCustom(Custom custom) {
		HashMap<Integer, LComponent[]> content = custom.getContent();
		ArrayList<LComponent> innerComps = custom.getInnerComps();
		ArrayList<LComponent> newInnerComps = CompUtils.duplicate(innerComps);
		ArrayList<LComponent> top = new ArrayList<LComponent>(), bottom = new ArrayList<LComponent>(), left = new ArrayList<LComponent>(), 
				right = new ArrayList<LComponent>();		
		for(int i = 0; i < newInnerComps.size(); i++) {
			LComponent oldComp = innerComps.get(i);
			LComponent newComp = newInnerComps.get(i);
			if(oldComp instanceof Light || oldComp instanceof Switch) {
				if(Arrays.asList(content.get(CompRotator.LEFT)).contains(oldComp)) CompUtils.addInPlace(newComp, left, false);
				else if(Arrays.asList(content.get(CompRotator.UP)).contains(oldComp)) CompUtils.addInPlace(newComp, top, true);
				else if(Arrays.asList(content.get(CompRotator.RIGHT)).contains(oldComp)) CompUtils.addInPlace(newComp, right, false);
				else if(Arrays.asList(content.get(CompRotator.DOWN)).contains(oldComp)) CompUtils.addInPlace(newComp, bottom, true);
			}
		}
		HashMap<Integer, LComponent[]> newContent = new HashMap<Integer, LComponent[]>();
		newContent.put(CompRotator.UP, top.toArray(new LComponent[0]));
		newContent.put(CompRotator.DOWN, bottom.toArray(new LComponent[0]));
		newContent.put(CompRotator.LEFT, left.toArray(new LComponent[0]));
		newContent.put(CompRotator.RIGHT, right.toArray(new LComponent[0]));
		Custom result = new Custom(custom.getX(), custom.getY(), custom.getLabel(), newContent, newInnerComps);
		result.setName(custom.getName());
		result.getRotator().setRotation(custom.getRotator().getRotation());
		return result;
	}
	
	/**
	 * Rotates the given components in the specified direction (CompRotator.CLOCKWISE or CompRotator.COUNTER_CLOCKWISE) so that all
	 * of the components are the same distance apart.  This method relies on the CompUtils.getBoundingRectangle(...) method along with
	 * the CompRotator.withRotation(...) method.
	 * @param lcomps The list of components to rotate
	 * @param direction The direction to rotate the components
	 */
	public static void rotateAll(ArrayList<LComponent> lcomps, boolean direction) {
		Rectangle bounds = getBoundingRectangle(lcomps);
		int x = bounds.x, y = bounds.y, width = bounds.width, height = bounds.height;
		int rotation;
		if(direction == CompRotator.CLOCKWISE) rotation = CompRotator.DOWN;
		else rotation = CompRotator.UP;
		for(int i = 0; i < lcomps.size(); i++) {
			LComponent lcomp = lcomps.get(i);
			Point rotPoint = CompRotator.withRotation(lcomp.getX() - x, lcomp.getY() - y, width, height, rotation);
			CompRotator rotator = lcomp.getRotator();
			if(direction == CompRotator.CLOCKWISE) {
				rotator.setRotation(rotator.getRotation() + 1);
				lcomp.setX(rotPoint.x - lcomp.getBounds().width + x + 1);
				lcomp.setY(rotPoint.y + y);
			}
			else {
				rotator.setRotation(rotator.getRotation() - 1);
				lcomp.setX(rotPoint.x + x);
				lcomp.setY(rotPoint.y - lcomp.getBounds().height + y + 1);
			}
		}
	}
	
	/**
	 * Returns the smallest possible Rectangle that completely encloses all of the components 
	 * @return The bounding Rectangle
	 */
	public static Rectangle getBoundingRectangle(ArrayList<LComponent> lcomps) {
		int minX = 0, maxX = 0, minY = 0, maxY = 0;
		for(int i = 0; i < lcomps.size(); i++) {
			LComponent lcomp = lcomps.get(i);
			int compMinX = lcomp.getX();
			int compMaxX = (int) (lcomp.getX() + lcomp.getBounds().getWidth());
			int compMinY = lcomp.getY();
			int compMaxY = (int) (lcomp.getY() + lcomp.getBounds().getHeight());
			
			if(i == 0) {
				minX = compMinX;
				maxX = compMaxX;
				minY = compMinY;
				maxY = compMaxY;
			}
			else {
				if(compMinX < minX) minX = compMinX;
				if(compMaxX > maxX) maxX = compMaxX;
				if(compMinY < minY) minY = compMinY;
				if(compMaxY > maxY) maxY = compMaxY;
			}
		}
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
	
	/**
	 * Adds the given LComponent to the list based on its x or y value. The component is placed so that the list has the components in order
	 * of ascending x or y value
	 * @param lcomp The LComponent to add
	 * @param lcomps The list of LComponents
	 * @param xy True if the components are being sorted by x value, false if the components are being sorted by y value
	 */
	public static void addInPlace(LComponent lcomp, ArrayList<LComponent> lcomps, boolean xy) {
		if(lcomps.size() == 0) lcomps.add(lcomp);
		else {
			int val;
			if(xy) val = lcomp.getX();
			else val = lcomp.getY();
			boolean compAdded = false;
			
			for(int i = 0; i < lcomps.size(); i++) {
				int above;
				if(i >= lcomps.size()) above = Integer.MAX_VALUE;
				else {
					if(xy) above = lcomps.get(i).getX();
					else above = lcomps.get(i).getY();
				}
				int below;
				if(i - 1 < 0) below = Integer.MIN_VALUE;
				else {
					if(xy) below = lcomps.get(i - 1).getX();
					else below = lcomps.get(i - 1).getY();
				}
				if(below < val && val < above) {
					lcomps.add(i, lcomp);
					compAdded = true;
				}
			}
			if(!compAdded) lcomps.add(lcomp);
		}
	}
	
}
