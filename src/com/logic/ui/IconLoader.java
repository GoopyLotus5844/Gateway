package com.logic.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 * Loads and holds all of the images used by the program
 * @author toddstennes
 *
 */
public class IconLoader {

	/**
	 * The number of logic icons
	 */
	private final int numLogicIcons = 47;
	
	/**
	 * The number of tool bar icons
	 */ 
	private final int numToolBarIcons = 15;
	
	/**
	 * The sprite sheet that contains all of the images that the program uses
	 */
	public BufferedImage iconSheet;
	
	/**
	 * The array of logic images
	 */
	public LogicImage[] logicImages;
	
	/**
	 * The array of logic icons
	 */
	public ImageIcon[] logicIcons;
	
	/**
	 * The array of tool bar icons
	 */
	public ImageIcon[] toolBarIcons;
	
	/**
	 * Constructs a new IconLoader and loads all of the images
	 */
	public IconLoader() {
		iconSheet = loadImage("/icons.png");
		logicImages = new LogicImage[numLogicIcons];
		logicIcons = new ImageIcon[numLogicIcons];
		toolBarIcons = new ImageIcon[numToolBarIcons];
		makeImageIcons();
	}
	
	/**
	 * Loads all of the images that the program uses
	 */
	private void makeImageIcons() {
		BufferedImage[] tempLogicImages = new BufferedImage[numLogicIcons];
		tempLogicImages = readSheetSection(iconSheet, tempLogicImages, 0, 0, 5, 2, 7, 11, 10, 0);
		tempLogicImages = readSheetSection(iconSheet, tempLogicImages, 22, 0, 3, 2, 7, 11, 6, 10);
		tempLogicImages = readSheetSection(iconSheet, tempLogicImages, 44, 0, 3, 2, 11, 11, 6, 16);
		tempLogicImages = readSheetSection(iconSheet, tempLogicImages, 66, 0, 1, 2, 11, 7, 2, 22);
		tempLogicImages = readSheetSection(iconSheet, tempLogicImages, 80, 0, 2, 2, 9, 9, 4, 24);
		tempLogicImages = readSheetSection(iconSheet, tempLogicImages, 98, 0, 1, 2, 11, 9, 2, 28);
		tempLogicImages = readSheetSection(iconSheet, tempLogicImages, 0, 35, 2, 8, 11, 17, 16, 30);
		tempLogicImages[46] = iconSheet.getSubimage(116, 0, 2, 1);
		
		for(int i = 0; i < tempLogicImages.length; i++) {
			logicIcons[i] = new ImageIcon(tempLogicImages[i]);
			logicImages[i] = new LogicImage(tempLogicImages[i]);
		}
		logicIcons[1] = new ImageIcon(advancedLoadImage("./res/pizza.svg"));
		logicImages[1] = new LogicImage(advancedLoadImage("./res/pizza.svg"));
		
		BufferedImage[] toolBarImages = new BufferedImage[numToolBarIcons];
		toolBarImages = readSheetSection(iconSheet, toolBarImages, 0, 57, 2, 8, 13, 13, 15, 0);
		for(int i = 0; i < toolBarImages.length; i++) {
			toolBarIcons[i] = new ImageIcon(toolBarImages[i]);
		}
		toolBarIcons[0] = new ImageIcon(loadImage("/new_file.png"));
		toolBarIcons[0] = new ImageIcon(loadImage("/pizza.svg"));
		toolBarIcons[1] = new ImageIcon(loadImage("/open_file.png"));
		toolBarIcons[2] = new ImageIcon(loadImage("/save_file.png"));
		toolBarIcons[3] = new ImageIcon(loadImage("/select.png"));
		toolBarIcons[4] = new ImageIcon(loadImage("/pan.png"));
		toolBarIcons[5] = new ImageIcon(loadImage("/insert.png"));
	}
	
	/**
	 * Reads images from a section of a sprite sheet (This section must have all images arranged in a grid with uniform row and column sizes
	 * @param sheet The sprite sheet to read from
	 * @param images The array to place images in
	 * @param x The horizontal start of the section to read
	 * @param y The vertical start of the section to read
	 * @param rows The number of rows in the section
	 * @param cols The number of columns in the section
	 * @param rowSize The number of pixels that make one row
	 * @param colSize The number of pixels that make one column
	 * @param startIndex The starting index to place images in the array
	 * @return The array containing all of the images in the specified section of the sprite sheet, starting at the specified index
	 */
	private BufferedImage[] readSheetSection(BufferedImage sheet, BufferedImage[] images, int x, int y, int rows, int cols, int rowSize, int colSize, int numImages, int startIndex) {
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				if(col + row * cols + 1 > numImages) return images; 
				images[startIndex + col + row * cols] = sheet.getSubimage(x + col * colSize, y + row * rowSize, colSize, rowSize);
			}
		}
		return images;
	}
	
	/**
	 * Returns a scaled version of the image with the specified width and height
	 * @param image The image to scale
	 * @param newWidth The new width
	 * @param newHeight The new height
	 * @return The scaled image
	 */
	public static ImageIcon getScaledImage(ImageIcon image, int newWidth, int newHeight) {
		return new ImageIcon(image.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
	}
	
	/**
	 * Loads an image from the specified file
	 * @param path The file path to load the image from
	 * @return The BufferedImage
	 */
	public BufferedImage loadImage(String path){
		try {
			return ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage advancedLoadImage(String file){
		BufferedImage image = null;
		try (ImageInputStream input = ImageIO.createImageInputStream(new File(file))) {
			// Get the reader
			Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

			if (!readers.hasNext()) {
				throw new IllegalArgumentException("No reader for: " + file);
			}

			ImageReader reader = readers.next();

			try {
				reader.setInput(input);

				ImageReadParam param = reader.getDefaultReadParam();

				// Optionally, control read settings like sub sampling, source region or destination etc.
				param.setSourceRenderSize(new Dimension(500, 500));
				// ...

				// Finally read the image, using settings from param
				image = reader.read(0, param);

				// Optionally, read thumbnails, meta data, etc...
				int numThumbs = reader.getNumThumbnails(0);
				// ...
			}
			finally {
				// Dispose reader in finally block to avoid memory leaks
				reader.dispose();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
