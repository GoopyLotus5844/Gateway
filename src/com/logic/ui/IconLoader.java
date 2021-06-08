package com.logic.ui;

import com.logic.components.*;
import com.logic.components.Button;

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
		}

		logicImages[0] = new LogicImage(advancedLoadImage("res/buffer.svg", 240, 240));
		logicImages[1] = new LogicImage(advancedLoadImage("res/and.svg", 240, 240));
		logicImages[2] = new LogicImage(advancedLoadImage("res/or.svg", 240, 240));

		logicImages[3] = new LogicImage(advancedLoadImage("res/switch_off.svg", 180, 240));
		logicImages[4] = new LogicImage(advancedLoadImage("res/switch_on.svg", 180, 240));

		logicImages[5] = new LogicImage(advancedLoadImage("res/button_off.svg", 240, 240));
		logicImages[6] = new LogicImage(advancedLoadImage("res/button_on.svg", 240, 240));

		logicImages[7] = new LogicImage(advancedLoadImage("res/light_off.svg", 180, 240));
		logicImages[8] = new LogicImage(advancedLoadImage("res/light_on.svg", 180, 240));

		logicImages[9] = new LogicImage(advancedLoadImage("res/off_const.svg", 180, 240));
		logicImages[10] = new LogicImage(advancedLoadImage("res/on_const.svg", 180, 240));

		logicImages[11] = new LogicImage(advancedLoadImage("res/clock_off.svg", 240, 240));
		logicImages[12] = new LogicImage(advancedLoadImage("res/clock_on.svg", 240, 240));

		//logicIcons[0] = new ImageIcon(advancedLoadImage("res/buffer.svg", 55, 55));

		BufferedImage[] toolBarImages = new BufferedImage[numToolBarIcons];
		toolBarImages = readSheetSection(iconSheet, toolBarImages, 0, 57, 2, 8, 13, 13, 15, 0);
		for(int i = 0; i < toolBarImages.length; i++) {
			toolBarIcons[i] = new ImageIcon(toolBarImages[i]);
		}
		toolBarIcons[0] = new ImageIcon(loadImage("/new_file.png"));
		toolBarIcons[1] = new ImageIcon(loadImage("/open_file.png"));
		toolBarIcons[2] = new ImageIcon(loadImage("/save_file.png"));
		toolBarIcons[3] = new ImageIcon(loadImage("/select.png"));
		toolBarIcons[4] = new ImageIcon(loadImage("/pan.png"));
		toolBarIcons[5] = new ImageIcon(loadImage("/insert.png"));
		//TODO remove unnecessary parts of this method
	}

	public void generateToolbarIcons(){
		logicIcons[0] = new ImageIcon(renderLogicIcon(new SingleInputGate(0, 0, CompType.BUFFER)));
		logicIcons[1] = new ImageIcon(renderLogicIcon(new SingleInputGate(0, 0, CompType.NOT)));
		logicIcons[2] = new ImageIcon(renderLogicIcon(new BasicGate(0, 0, CompType.AND)));
		logicIcons[3] = new ImageIcon(renderLogicIcon(new BasicGate(0, 0, CompType.NAND)));
		logicIcons[4] = new ImageIcon(renderLogicIcon(new BasicGate(0, 0, CompType.OR)));
		logicIcons[5] = new ImageIcon(renderLogicIcon(new BasicGate(0, 0, CompType.NOR)));
		logicIcons[6] = new ImageIcon(renderLogicIcon(new BasicGate(0, 0, CompType.XOR)));
		logicIcons[7] = new ImageIcon(renderLogicIcon(new BasicGate(0, 0, CompType.XNOR)));
		logicIcons[8] = new ImageIcon(renderLogicIcon(new Clock(0, 0)));
		logicIcons[22] = new ImageIcon(renderLogicIcon(new Light(0, 0)));
		logicIcons[24] = new ImageIcon(renderLogicIcon(new Switch(0, 0)));
		logicIcons[26] = new ImageIcon(renderLogicIcon(new Constant(0, 0, CompType.ZERO)));
		logicIcons[27] = new ImageIcon(renderLogicIcon(new Constant(0, 0, CompType.ONE)));
		logicIcons[28] = new ImageIcon(renderLogicIcon(new Button(0, 0)));
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

	private BufferedImage renderLogicIcon(LComponent model){
		BufferedImage image = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		Rectangle bounds = model.getBounds();
		float scale = Math.min((InsertPanel.BUTTON_SIZE - 2 * InsertPanel.IMAGE_PADDING) / (float) bounds.width,
				(InsertPanel.BUTTON_SIZE - 2 * InsertPanel.IMAGE_PADDING) / (float) bounds.height);
		((Graphics2D) g).scale(scale, scale);
		g.translate((int) ((InsertPanel.BUTTON_SIZE - bounds.width * scale) / 2 / scale),
				(int) ((InsertPanel.BUTTON_SIZE - bounds.height * scale) / 2 / scale));
		model.getDrawer().drawComponentBody(g);
		g.dispose();
		return image;
		//TODO make the icons a little less rough around the edges?
	}

	public BufferedImage advancedLoadImage(String file, int width, int height){
		BufferedImage image = null;
		try (ImageInputStream input = ImageIO.createImageInputStream(new File(file))) {
			Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
			if (!readers.hasNext()) {
				throw new IllegalArgumentException("No reader for: " + file);
			}
			ImageReader reader = readers.next();

			try {
				reader.setInput(input);
				ImageReadParam param = reader.getDefaultReadParam();
				param.setSourceRenderSize(new Dimension(width, height));
				image = reader.read(0, param);
			}
			finally {
				reader.dispose();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
