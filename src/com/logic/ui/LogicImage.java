package com.logic.ui;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * A collection of BufferedImages to represent an LComponent
 * @author toddstennes
 *
 */
public class LogicImage {

	/**
	 * The right (default) facing version of the image
	 */
	private BufferedImage right;
	
	/**
	 * The down facing version of the image
	 */
	private BufferedImage down;
	
	/**
	 * The left facing version of the image
	 */
	private BufferedImage left;
	
	/**
	 * The up facing version of the image
	 */
	private BufferedImage up;
	
	
	/**
	 * Constructs a new LogicImage
	 * @param image The image (interpreted as facing right) to use
	 */
	public LogicImage(BufferedImage image) {
		right = image;
		down = getRotatedImage(image, CompRotator.DOWN);
		left = getRotatedImage(image, CompRotator.LEFT);
		up = getRotatedImage(image, CompRotator.UP);
	}
	
	/**
	 * Returns a rotated version of the image
	 * @param image The right facing image to rotate
	 * @param rotation The rotation to apply to the image
	 * @return The rotated image
	 */
	public BufferedImage getRotatedImage(BufferedImage image, int rotation) {
		int width  = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage newImage;
		if(rotation == CompRotator.DOWN || rotation == CompRotator.UP) newImage = new BufferedImage(height, width, image.getType());
		else newImage = new BufferedImage(width, height, image.getType());
		for( int i = 0; i < width; i++) {
			for( int j = 0; j < height; j++) {
				Point p = CompRotator.withRotation(i, j, width, height, rotation);
				newImage.setRGB(p.x, p.y, image.getRGB(i, j));
			}
		}	
		return newImage;
	}
	
	/**
	 * Returns the version of the image that corresponds to the given rotation
	 * @param rotation The rotation (a CompRotator constant)
	 * @return The rotated version of the image
	 */
	public BufferedImage getBufferedImage(int rotation) {
		if(rotation == CompRotator.DOWN) return down;
		else if(rotation == CompRotator.LEFT) return left;
		else if(rotation == CompRotator.UP) return up;
		return right;
	}
	
}
