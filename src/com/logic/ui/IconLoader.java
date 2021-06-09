package com.logic.ui;

import com.logic.components.*;
import com.logic.components.Button;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.*;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

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
	 * The array of logic SVGs
	 */
	public GraphicsNode[] logicSVGs;
	
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
		logicSVGs = new GraphicsNode[numLogicIcons];
		makeImageIcons();
	}
	
	/**
	 * Loads all the BufferedImages and SVG GraphicsNode objects that the program uses
	 */
	private void makeImageIcons() {
		logicImages[0] = new LogicImage(imageFromSVG("res/buffer.svg", 240, 240));
		logicImages[1] = new LogicImage(imageFromSVG("res/and.svg", 240, 240));
		logicImages[2] = new LogicImage(imageFromSVG("res/or.svg", 240, 240));
		logicImages[3] = new LogicImage(imageFromSVG("res/switch_off.svg", 180, 240));
		logicImages[4] = new LogicImage(imageFromSVG("res/switch_on.svg", 180, 240));
		logicImages[5] = new LogicImage(imageFromSVG("res/button_off.svg", 240, 240));
		logicImages[6] = new LogicImage(imageFromSVG("res/button_on.svg", 240, 240));
		logicImages[7] = new LogicImage(imageFromSVG("res/light_off.svg", 180, 240));
		logicImages[8] = new LogicImage(imageFromSVG("res/light_on.svg", 180, 240));
		logicImages[9] = new LogicImage(imageFromSVG("res/off_const.svg", 180, 240));
		logicImages[10] = new LogicImage(imageFromSVG("res/on_const.svg", 180, 240));
		logicImages[11] = new LogicImage(imageFromSVG("res/clock_off.svg", 240, 240));
		logicImages[12] = new LogicImage(imageFromSVG("res/clock_on.svg", 240, 240));
		logicImages[13] = new LogicImage(imageFromSVG("res/display.svg", 300, 300));

		logicSVGs[0] = loadSvg("res/buffer.svg");
		logicSVGs[1] = loadSvg("res/and.svg");
		logicSVGs[2] = loadSvg("res/or.svg");
		logicSVGs[3] = loadSvg("res/switch_off.svg");
		logicSVGs[4] = loadSvg("res/switch_on.svg");
		logicSVGs[5] = loadSvg("res/button_off.svg");
		logicSVGs[6] = loadSvg("res/button_on.svg");
		logicSVGs[7] = loadSvg("res/light_off.svg");
		logicSVGs[8] = loadSvg("res/light_on.svg");
		logicSVGs[9] = loadSvg("res/off_const.svg");
		logicSVGs[10] = loadSvg("res/on_const.svg");
		logicSVGs[11] = loadSvg("res/clock_off.svg");
		logicSVGs[12] = loadSvg("res/clock_on.svg");
		logicSVGs[13] = loadSvg("res/display.svg");

		BufferedImage[] toolBarImages = readSheetSection(iconSheet, new BufferedImage[numToolBarIcons], 0, 57, 2, 8, 13, 13, 15, 0);
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
		logicIcons[30] = new ImageIcon(renderLogicIcon(new Display(0, 0)));
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
	 * Renders the icons for each gate using CompDrawer drawing code set to SVG mode. Does not include connections.
	 * @param model The LComponent that will be rendered to the image (should be placed at 0, 0)
	 * @return The icon based on the given LComponent
	 */
	private BufferedImage renderLogicIcon(LComponent model){
		BufferedImage image = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		Rectangle bounds = model.getBounds();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		float scale = Math.min((InsertPanel.BUTTON_SIZE - 2 * InsertPanel.IMAGE_PADDING) / (float) bounds.width,
				(InsertPanel.BUTTON_SIZE - 2 * InsertPanel.IMAGE_PADDING) / (float) bounds.height);
		g2d.scale(scale, scale);
		g2d.translate((int) ((InsertPanel.BUTTON_SIZE - bounds.width * scale) / 2 / scale),
				(int) ((InsertPanel.BUTTON_SIZE - bounds.height * scale) / 2 / scale));
		model.getDrawer().setUseSVG(true);
		model.getDrawer().drawComponentBody(g2d);
		g2d.dispose();
		return image;
	}

	/**
	 * Loads the given SVG file as a GraphicsNode so that it can then be rendered at any resolution
	 * @param path The path to the SVG
	 * @return The GraphicsNode object
	 */
	private GraphicsNode loadSvg(String path){
		GraphicsNode svgIcon = null;
		try {
			String xmlParser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory df = new SAXSVGDocumentFactory(xmlParser);
			SVGDocument doc = df.createSVGDocument(path);
			UserAgent userAgent = new UserAgentAdapter();
			DocumentLoader loader = new DocumentLoader(userAgent);
			BridgeContext ctx = new org.apache.batik.bridge.BridgeContext(userAgent, loader);
			ctx.setDynamicState(org.apache.batik.bridge.BridgeContext.DYNAMIC);
			GVTBuilder builder = new org.apache.batik.bridge.GVTBuilder();
			svgIcon = builder.build(ctx, doc);
		} catch (Exception excp) {
			svgIcon = null;
			excp.printStackTrace();
		}
		return svgIcon;
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

	/**
	 * An easy way to get a BufferedImage directly from an SVG file using the twelvemonkeys ImageIO plugin
	 * @param file The SVG file to read from
	 * @param width The width of the new BufferedImage
	 * @param height The height of the new BufferedImage
	 * @return The image
	 */
	private BufferedImage imageFromSVG(String file, int width, int height){
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
