package org.tabula.utils.xmlutils;

import org.tabula.instances.CredentialsInstance;
import org.tabula.objects.Credentials;
import org.tabula.objects.Tableset;
import org.tabula.objects.model.Model;
import org.tabula.utils.checksumutils.ChecksumHandler;
import org.tabula.utils.datautils.ModelLinker;
import org.tabula.utils.dbutils.DBType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLHandler {

    static public List<Tableset> getTablesetsFromXML(Document document) {
        ChecksumHandler.verifyChecksum(document);

        List<Tableset> tablesetList = new ArrayList<Tableset>();

        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        NodeList tablesets = document.getElementsByTagName("tableset");

        for(int i = 0; i < tablesets.getLength(); i++) {
            Node node = tablesets.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Tableset tableset = new Tableset();
                tableset.setName(element.getElementsByTagName("name").item(0).getTextContent());
                tableset.setTables(element.getElementsByTagName("tables").item(0).getTextContent());

                tablesetList.add(tableset);
            }
        }

        return tablesetList;
    }

    static public Document createXMLWithTablesets(List<Tableset> tablesetList) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("XML cannot be parsed!", e);
        }
        Document document = builder.newDocument();

        Element rootElement1 = document.createElement("doc");

        Element checkSum = document.createElement("checksum");
        checkSum.setTextContent("");
        rootElement1.appendChild(checkSum);

        for(Tableset tableset : tablesetList) {
            Element temp;

            Element rootElement = document.createElement("tableset");

            temp = document.createElement("name");
            temp.setTextContent(tableset.getName());
            rootElement.appendChild(temp);

            temp = document.createElement("tables");
            temp.setTextContent(tableset.getTables());
            rootElement.appendChild(temp);

            rootElement1.appendChild(rootElement);
        }

        document.appendChild(rootElement1);

        String xmlAsText = XMLHandler.getXMLAsString(document);
        long checksum = ChecksumHandler.getCRC32Checksum(xmlAsText);

        checkSum.setTextContent(String.valueOf(checksum));

        return document;
    }

    static public Document createXMLWithCredentials(List<Credentials> connectionsList) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document = builder.newDocument();

        Element rootElement1 = document.createElement("doc");

        Element checkSum = document.createElement("checksum");
        checkSum.setTextContent("");
        rootElement1.appendChild(checkSum);

        for(Credentials credentials : connectionsList) {
            Element temp;

            Element rootElement = document.createElement("connection");

            temp = document.createElement("connectionName");
            temp.setTextContent(credentials.getConnectionName());
            rootElement.appendChild(temp);

            temp = document.createElement("login");
            temp.setTextContent(credentials.getLogin());
            rootElement.appendChild(temp);

            temp = document.createElement("password");
            temp.setTextContent(credentials.getPassword());
            rootElement.appendChild(temp);

            temp = document.createElement("host");
            temp.setTextContent(credentials.getHost());
            rootElement.appendChild(temp);

            temp = document.createElement("port");
            temp.setTextContent(credentials.getPort());
            rootElement.appendChild(temp);

            temp = document.createElement("sid");
            temp.setTextContent(credentials.getSid());
            rootElement.appendChild(temp);

            temp = document.createElement("dbtype");
            temp.setTextContent(credentials.getDbType().name());
            rootElement.appendChild(temp);

            rootElement1.appendChild(rootElement);
        }

        document.appendChild(rootElement1);

        String xmlAsText = XMLHandler.getXMLAsString(document);
        long checksum = ChecksumHandler.getCRC32Checksum(xmlAsText);

        checkSum.setTextContent(String.valueOf(checksum));

        return document;
    }

    static public List<Credentials> getConnectionsFromXML(Document document) {
        ChecksumHandler.verifyChecksum(document);

        List<Credentials> connectionsList = new ArrayList<Credentials>();

        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        NodeList connections = document.getElementsByTagName("connection");

        for(int i = 0; i < connections.getLength(); i++) {
            Node node = connections.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Credentials credentials = new Credentials();
                credentials.setConnectionName(element.getElementsByTagName("connectionName").item(0).getTextContent());
                credentials.setLogin(element.getElementsByTagName("login").item(0).getTextContent());
                credentials.setPassword(element.getElementsByTagName("password").item(0).getTextContent());
                credentials.setHost(element.getElementsByTagName("host").item(0).getTextContent());
                credentials.setPort(element.getElementsByTagName("port").item(0).getTextContent());
                credentials.setSid(element.getElementsByTagName("sid").item(0).getTextContent());
                credentials.setDbType(DBType.valueOf(element.getElementsByTagName("dbtype").item(0).getTextContent()));

                connectionsList.add(credentials);
            }
        }

        return connectionsList;
    }

    static public Document createXMLFromModel(Model model) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new RuntimeException(parserConfigurationException);
        }
        Document document = documentBuilder.newDocument();
        model.createXML(document);

        String xmlAsText = XMLHandler.getXMLAsString(document);
        long checksum = ChecksumHandler.getCRC32Checksum(xmlAsText);

        Node checkSum = document.getElementsByTagName("checksum").item(0);
        checkSum.setTextContent(String.valueOf(checksum));

        return document;
    }

    static public void loadDataFromXML(Document document, Model model) {

        ChecksumHandler.verifyChecksum(document);

        Node connectionNameNode = document.getElementsByTagName("connectionName").item(0);
        model.setCredentials(CredentialsInstance.getInstance().findCredentials(connectionNameNode.getTextContent()));

        NodeList dependencies = document.getElementsByTagName("dependence");
        for(int i = 0; i < dependencies.getLength(); i++) {
            Node dependence = dependencies.item(i);
            if(dependence.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) dependence;

                String tableFromName = element.getElementsByTagName("tableFrom").item(0).getTextContent();
                String fieldFromName = element.getElementsByTagName("fieldFrom").item(0).getTextContent();
                String tableToName = element.getElementsByTagName("tableTo").item(0).getTextContent();
                String fieldToName = element.getElementsByTagName("fieldTo").item(0).getTextContent();

                ModelLinker.compose(model, tableFromName, fieldFromName, tableToName, fieldToName);
            }
        }
    }

    static public String getXMLAsString(Document document) {
        StringWriter writer = null;
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
//            tf.setAttribute("indent-number", 4);
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        return output;
    }
}
