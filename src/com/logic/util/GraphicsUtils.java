package com.logic.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.logic.components.Connection;
import com.logic.main.LogicSimApp;
import com.logic.ui.CompDrawer;
import com.logic.ui.CompRotator;
import com.logic.ui.LogicImage;

/**
 * A class that holds static helper methods for GUI
 * @author toddstennes
 *
 */
public class GraphicsUtils {

	/**
	 * Makes the given JLabel display a bold version of its original font
	 * @param label The JLabel to make bold
	 */
	public static void makeBold(JLabel label) {
		Font f = label.getFont();
		label.setFont(f.deriveFont(Font.BOLD));
	}
	
	/**
	 * Renders the given connection using iconLoader.logicImages[46];
	 * @param g The Graphics object to use
	 * @param c The connection to draw
	 * @param xOffset The x position of the LComponent calling this method
	 * @param yOffset The y position of the LComponent calling this method
	 * @param rotation The rotation of the LComponent calling this method
	 */
	public static void drawConnection(Graphics g, Connection c, int xOffset, int yOffset, int rotation) {
		LogicImage image = LogicSimApp.iconLoader.logicImages[46];
		int connectX = c.getRotatedX(rotation);
		int connectY = c.getRotatedY(rotation);
		int connectDirection = c.getAbsoluteDirection();
		BufferedImage rotateImage = image.getBufferedImage(c.getAbsoluteDirection());
		/*
		float scale = CompDrawer.IMAGE_SCALE;
		if(connectDirection == CompRotator.LEFT || connectDirection == CompRotator.UP) g.drawImage(rotateImage, 
				(int) (connectX * scale + xOffset),
				(int) (connectY * scale + yOffset), 
				(int) (rotateImage.getWidth() * scale), 
				(int) (rotateImage.getHeight() * scale), 
				null);
		else if(connectDirection == CompRotator.RIGHT) g.drawImage(rotateImage, 
				(int) (connectX * scale + xOffset - scale),
				(int) (connectY * scale + yOffset), 
				(int) (rotateImage.getWidth() * scale), 
				(int) (rotateImage.getHeight() * scale), 
				null);
		else g.drawImage(rotateImage, 
				(int) (connectX * scale + xOffset),
				(int) (connectY * scale + yOffset - scale), 
				(int) (rotateImage.getWidth() * scale), 
				(int) (rotateImage.getHeight() * scale), 
				null);
		 */
		//TODO implement or remove this method
	}
	
	/**
	 * Adds a red border to the given JComponent for layout debugging purposes
	 * @param j The JComponent to debug
	 */
	public static void debug(JComponent j) {
		j.setBorder(BorderFactory.createLineBorder(Color.RED));
	}
	
}
