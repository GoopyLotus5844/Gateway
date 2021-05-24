

import java.awt.*;
import java.awt.geom.*;
import static java.awt.Color.*;
import static java.awt.MultipleGradientPaint.CycleMethod.*;
import static java.awt.MultipleGradientPaint.ColorSpaceType.*;

/**
 * This class has been automatically generated using
 * <a href="http://ebourg.github.io/flamingo-svg-transcoder/">Flamingo SVG transcoder</a>.
 */
public class Pizza {

    /**
     * Paints the transcoded SVG image on the specified graphics context. You
     * can install a custom transformation on the graphics context to scale the
     * image.
     * 
     * @param g Graphics context.
     */
    public static void paint(Graphics2D g) {
        Shape shape = null;
        
        float origAlpha = 1.0f;
        Composite origComposite = g.getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
        java.util.LinkedList<AffineTransform> transformations = new java.util.LinkedList<AffineTransform>();
        

        // 

        // _0

        // _0_0
        shape = new GeneralPath();
        ((GeneralPath) shape).moveTo(12.0, 15.0);
        ((GeneralPath) shape).curveTo(10.895431, 15.0, 10.0, 14.104569, 10.0, 13.0);
        ((GeneralPath) shape).curveTo(10.0, 11.89, 10.9, 11.0, 12.0, 11.0);
        ((GeneralPath) shape).curveTo(13.104569, 11.0, 14.0, 11.895431, 14.0, 13.0);
        ((GeneralPath) shape).curveTo(14.0, 14.104569, 13.104569, 15.0, 12.0, 15.0);
        ((GeneralPath) shape).moveTo(7.0, 7.0);
        ((GeneralPath) shape).curveTo(7.0, 5.89, 7.89, 5.0, 9.0, 5.0);
        ((GeneralPath) shape).curveTo(10.104569, 5.0, 11.0, 5.8954306, 11.0, 7.0);
        ((GeneralPath) shape).curveTo(11.0, 8.104569, 10.104569, 9.0, 9.0, 9.0);
        ((GeneralPath) shape).curveTo(7.89, 9.0, 7.0, 8.1, 7.0, 7.0);
        ((GeneralPath) shape).moveTo(12.0, 2.0);
        ((GeneralPath) shape).curveTo(8.43, 2.0, 5.23, 3.54, 3.0, 6.0);
        ((GeneralPath) shape).lineTo(12.0, 22.0);
        ((GeneralPath) shape).lineTo(21.0, 6.0);
        ((GeneralPath) shape).curveTo(18.78, 3.54, 15.57, 2.0, 12.0, 2.0);
        ((GeneralPath) shape).closePath();

        g.setPaint(BLACK);
        g.fill(shape);

    }

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public static int getOrigX() {
        return 3;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 2;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * 
     * @return The width of the bounding box of the original SVG image.
     */
    public static int getOrigWidth() {
        return 18;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public static int getOrigHeight() {
        return 20;
    }
}

