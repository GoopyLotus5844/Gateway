package com.logic.test;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.*;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import java.awt.*;

public class SvgRenderer {

    /**
     * Method to fetch the SVG icon from a url
     *
     * @param url the url from which to fetch the SVG icon
     *
     * @return a graphics node object that can be used for painting
     */
    public static org.apache.batik.gvt.GraphicsNode getSvgIcon(String path) {
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
     * Method to paint the icon using Graphics2D. Note that the scaling factors have nothing to do with the zoom
     * operation, the scaling factors set the size your icon relative to the other objects on your canvas.
     *
     * @param g the graphics context used for drawing
     *
     * @param svgIcon the graphics node object that contains the SVG icon information
     *
     * @param x the X coordinate of the top left corner of the icon
     *
     * @param y the Y coordinate of the top left corner of the icon
     *
     * @param scaleX the X scaling to be applied to the icon before drawing
     *
     * @param scaleY the Y scaling to be applied to the icon before drawing
     */
    public static void paintSvgIcon(java.awt.Graphics2D g, org.apache.batik.gvt.GraphicsNode svgIcon, int x, int y, double scaleX, double scaleY) {
        java.awt.geom.AffineTransform transform = new java.awt.geom.AffineTransform(scaleX, 0.0, 0.0, scaleY, x, y);
        svgIcon.setTransform(transform);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        svgIcon.paint(g);
    }
}
