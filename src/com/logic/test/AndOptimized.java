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
public class AndOptimized implements org.pushingpixels.flamingo.api.common.icon.ResizableIcon {

    /**
     * Paints the transcoded SVG image on the specified graphics context. You
     * can install a custom transformation on the graphics context to scale the
     * image.
     *
     * @param g Graphics context.
     */
    public static void paint(Graphics2D g) {

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
        g.transform(new AffineTransform(0.013888889f, 0, 0, 0.013888889f, 0, 0));

        GeneralPath path = new GeneralPath();
        path.moveTo(58.17, 36.0);
        path.curveTo(58.17, 24.5, 48.5, 15.83, 36.0, 15.83);
        path.curveTo(23.5, 15.83, 18.0, 15.83, 18.0, 15.83);
        path.lineTo(18.0, 36.0);

        g.setPaint(WHITE);
        g.fill(path);
        g.setPaint(BLACK);
        g.setStroke(new BasicStroke(3, 0, 0, 10));
        g.draw(path);

        path.reset();
        path.moveTo(58.17, 35.83);
        path.curveTo(58.17, 47.33, 48.5, 56.0, 36.0, 56.0);
        path.curveTo(23.5, 56.0, 18.0, 56.0, 18.0, 56.0);
        path.lineTo(18.0, 35.83);

        g.setPaint(WHITE);
        g.fill(path);
        g.setPaint(BLACK);
        g.draw(path);

        Line2D line = new Line2D.Float(18.000000f, 23.719999f, 6.500000f, 23.719999f);
        g.draw(line);
        line.setLine(18.000000f, 48.720001f, 6.500000f, 48.720001f);
        g.draw(line);
        line.setLine(58.169998f, 36.000000f, 65.940002f, 36.000000f);
        g.draw(line);

        // _0_5
        Ellipse2D shape = new Ellipse2D.Double(2.5, 19.719999313354492, 8, 8);
        g.setPaint(new Color(0x5B6EE1));
        g.fill(shape);
        shape.setFrame(2.5, 44.720001220703125, 8, 8);
        g.fill(shape);
        shape.setFrame(63.5, 32, 8, 8);
        g.fill(shape);

        g.setTransform(transformations.pop());
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
    public AndOptimized() {
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
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(x, y);

        double coef1 = (double) this.width / (double) getOrigWidth();
        double coef2 = (double) this.height / (double) getOrigHeight();
        double coef = Math.min(coef1, coef2);
        g2d.scale(coef, coef);
        paint(g2d);
        g2d.dispose();
    }
}


