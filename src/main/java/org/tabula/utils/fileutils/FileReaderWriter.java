package org.tabula.utils.fileutils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

public class FileReaderWriter {

    static private Properties properties;

    static private final String PARAMETERS = "parameters.properties";
    static public final String PATH_TO_CREDENTIALS_LIST = "configuration/connections";
    static public final String PATH_TO_TABLESET_LIST = "configuration/tablesets";

    static public void writeXMLToFile(Document document, String fileName) {
        DOMSource source = new DOMSource(document);
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(fileName));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    static public String getParameter(String parameterName) {
        if(properties == null) {
            InputStream inputStream = FileReaderWriter.class.getClassLoader().getResourceAsStream(PARAMETERS);
            properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return (String)properties.get(parameterName);
    }

    static public Document readXMLFromFile(File file) {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            InputStream inputStream = new FileInputStream(file);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(inputStream);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        document.getDocumentElement().normalize();

        return document;
    }

    static public void writeImage(BufferedImage image, File outputFile) {
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
