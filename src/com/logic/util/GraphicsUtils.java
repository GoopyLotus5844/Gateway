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
	 * Adds a red border to the given JComponent for layout debugging purposes
	 * @param j The JComponent to debug
	 */
	public static void debug(JComponent j) {
		j.setBorder(BorderFactory.createLineBorder(Color.RED));
	}
	
}
