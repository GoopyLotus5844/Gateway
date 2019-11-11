package com.logic.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * A message that can be displayed in the CircuitPanel
 * @author toddstennes
 *
 */
public class UserMessage implements ActionListener {
	
	/**
	 * The font of the message
	 */
	private final Font labelFont = new Font("Arial", Font.PLAIN, 15);
	
	/**
	 * The background color of the message
	 */
	private final Color labelColor = new Color(255, 244, 179);
	
	/**
	 * The margin inside the label on each side
	 */
	private final int xMargin = 10;
	
	/**
	 * The margin inside the label on the top and bottom
	 */
	private final int yMargin = 3;
	
	/**
	 * The offset of the label from the top of the CircuitPanel
	 */
	private final int offset = 20;

	/**
	 * The text to display
	 */
	private String text;
	
	/**
	 * The timer that is used to clear the message, if this message is timed
	 */
	private Timer timer;
	
	/**
	 * The CircuitPanel
	 */
	private CircuitPanel cp;
	
	/**
	 * Constructs a new UserMessage that stays on the screen until CircuitPanel.clearMessage() is called by another class
	 * @param cp The CircuitPanel
	 * @param text The text
	 */
	public UserMessage(CircuitPanel cp, String text) {
		this.cp = cp;
		this.text = text;
	}
	
	/**
	 * Constructs a new UserMessage that clears itself from the screen after the given time has passed
	 * @param cp The CircuitPanel
	 * @param text The text
	 * @param duration The duration of the message in milliseconds
	 */
	public UserMessage(CircuitPanel cp, String text, int duration) {
		this.cp = cp;
		this.text = text;
		timer = new Timer(duration, this);
	}
	
	/**
	 * Starts the timer if this is a timed message
	 */
	public void start() {
		if(timer != null) timer.start();
	}
	
	/**
	 * Renders the message at the top center of the CircuitPanel
	 * @param g The Graphics object to use
	 */
	public void render(Graphics g) {
		g.setFont(labelFont);
		FontMetrics metrics = g.getFontMetrics(labelFont);
		int width = metrics.stringWidth(text);
		int height = metrics.getAscent();
		
		int x = (cp.getWidth() - width) / 2;
		int y = offset + yMargin;
		
		g.setColor(labelColor);
		g.fillRect(x - xMargin, y - yMargin - 12, width + 2 * xMargin, height + 2 * yMargin);
		g.setColor(Color.BLACK);
		g.drawRect(x - xMargin, y - yMargin - 12, width + 2 * xMargin, height + 2 * yMargin);
		g.drawString(text, x, y);
	}

	/**
	 * Called when the timer finishes to clear this message from the CircuitPanel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		cp.clearMessage();
		timer.stop();
	}
	
}
