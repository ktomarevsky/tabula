package org.tabula.utils.imageutils;

import org.tabula.graphicalcomponents.frame.AppFrame;
import org.tabula.instances.ApplicationInstance;
import org.tabula.objects.model.Model;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class ImageHandler {

    static public BufferedImage createPNGImage(Model model) {
        BufferedImage image = new BufferedImage(model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2D = image.createGraphics();
        model.paint(g2D);
        g2D.dispose();

        return image;
    }

    static public void createVectorImage(Model model, File outputFile) {
        AppFrame frame = ApplicationInstance.getInstance().getAppFrame();
        // Get a DOMImplementation.
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(ctx, false);
        model.paint(svgGenerator);

        try {
            // Finally, stream out SVG to the standard output using
            // UTF-8 encoding.
            boolean useCSS = false; // we want to use CSS style attributes
            Writer out1 = new FileWriter(outputFile);
            svgGenerator.stream(out1, useCSS);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(frame, "Unknown error!" + exception.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
