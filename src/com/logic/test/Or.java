package com.logic.test;

import java.awt.*;
import java.awt.geom.*;
import static java.awt.Color.*;
import static java.awt.MultipleGradientPaint.CycleMethod.*;
import static java.awt.MultipleGradientPaint.ColorSpaceType.*;

/**
 * This class has been automatically generated using
 * <a href="http://ebourg.github.io/flamingo-svg-transcoder/">Flamingo SVG transcoder</a>.
 */
public class Or implements org.pushingpixels.flamingo.api.common.icon.ResizableIcon {

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
        transformations.push(g.getTransform());
        g.transform(new AffineTransform(0.003903353f, 0, 0, 0.003903353f, 0.0068894154f, 0));

        // _0
        transformations.push(g.getTransform());
        g.transform(new AffineTransform(1, 0, 0, 1, -1.34f, 0.09f));

        // _0_0
        shape = new GeneralPath();

        g.setPaint(BLACK);
        g.fill(shape);

        g.setTransform(transformations.pop()); // _0_0
        transformations.push(g.getTransform());
        g.transform(new AffineTransform(1, 0, 0, 1, -1.34f, 0.09f));

        // _0_1
        shape = new GeneralPath();
        ((GeneralPath) shape).moveTo(246.5, 128.5);
        ((GeneralPath) shape).curveTo(223.0, 27.32, 139.39, 7.5, 101.5, 7.5);
        ((GeneralPath) shape).lineTo(11.37, 7.41);
        ((GeneralPath) shape).curveTo(10.322522, 7.405038, 9.381199, 8.048577, 9.005619, 9.02642);
        ((GeneralPath) shape).curveTo(8.630039, 10.004261, 8.898528, 11.112478, 9.68, 11.809999);
        ((GeneralPath) shape).curveTo(24.17, 24.77, 38.5, 67.59, 38.5, 128.5);

        g.fill(shape);

        g.setTransform(transformations.pop()); // _0_1
        transformations.push(g.getTransform());
        g.transform(new AffineTransform(1, 0, 0, 1, -1.34f, 0.09f));

        // _0_2
        shape = new GeneralPath();
        ((GeneralPath) shape).moveTo(246.5, 127.5);
        ((GeneralPath) shape).curveTo(223.0, 228.68, 139.39, 248.5, 101.5, 248.5);
        ((GeneralPath) shape).lineTo(11.370003, 248.59);
        ((GeneralPath) shape).curveTo(10.322527, 248.59496, 9.381207, 247.95142, 9.005628, 246.97357);
        ((GeneralPath) shape).curveTo(8.630048, 245.99574, 8.898535, 244.88753, 9.680002, 244.19);
        ((GeneralPath) shape).curveTo(24.170002, 231.19, 38.5, 188.41, 38.5, 127.5);

        g.fill(shape);

        g.setTransform(transformations.pop()); // _0_2
        transformations.push(g.getTransform());
        g.transform(new AffineTransform(1, 0, 0, 1, -1.34f, 0.09f));

        // _0_3
        shape = new GeneralPath();

        g.fill(shape);

        g.setTransform(transformations.pop()); // _0_3
        transformations.push(g.getTransform());
        g.transform(new AffineTransform(1, 0, 0, 1, -1.34f, 0.09f));

        // _0_4
        shape = new GeneralPath();

        g.fill(shape);

        g.setTransform(transformations.pop()); // _0_4

        g.setTransform(transformations.pop()); // _0

    }

    /**
     * Returns the X of the bounding box of the original SVG image.
     * 
     * @return The X of the bounding box of the original SVG image.
     */
    public static int getOrigX() {
        return 1;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * 
     * @return The Y of the bounding box of the original SVG image.
     */
    public static int getOrigY() {
        return 1;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * 
     * @return The width of the bounding box of the original SVG image.
     */
    public static int getOrigWidth() {
        return 1;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * 
     * @return The height of the bounding box of the original SVG image.
     */
    public static int getOrigHeight() {
        return 1;
    }

    /**
     * The current width of this resizable icon.
     */
    int width;

    /**
     * The current height of this resizable icon.
     */
    int height;

    /**
     * Creates a new transcoded SVG image.
     */
    public Or() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pushingpixels.flamingo.api.common.icon.ResizableIcon#setDimension(java.awt.Dimension)
     */
    @Override
    public void setDimension(Dimension dimension) {
        this.width = dimension.width;
        this.height = dimension.height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(x, y);

        double coef1 = (double) this.width / (double) getOrigWidth();
        double coef2 = (double) this.height / (double) getOrigHeight();
        double coef = Math.min(coef1, coef2);
        g2d.scale(coef, coef);
        paint(g2d);
        g2d.dispose();
    }
}

