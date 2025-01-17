package org.tabula.objects.model;

import org.tabula.eventcontroller.layout.DragController;
import org.tabula.graphicalcomponents.layout.ModelScrollPane;
import org.tabula.graphicalcomponents.modelview.menu.ModelMenu;
import org.tabula.objects.Credentials;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Model extends JPanel {

    private String name;
    private List<Table> tables;
    private List<Dependence> dependencies;

    private Credentials credentials;
    private boolean saved;

    /** Graphics */
    private ModelMenu menu;
    private transient ModelScrollPane modelScrollPane;
    private transient DragController dragController;

    private float commonSpaceOfTables;

    static private final int DEFAULT_WIDTH = 1920;
    static private final int DEFAULT_HEIGHT = 1280;
    /** Graphics */

    public Model() {
        tables = new ArrayList<>();
        dependencies = new ArrayList<>();
        saved = false;

        /** Graphics */
        setLayoutSettings();
        menu = new ModelMenu();

        setDragController();

        modelScrollPane = new ModelScrollPane(this);
        setLayout(modelScrollPane);
        /** Graphics */
    }

    /** Graphics */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        paintModel(g2D);
    }

    private void paintModel(Graphics2D g2D) {

        for (Dependence dependence : dependencies) {
            dependence.show(g2D);
        }

        for (Table table : tables) {
            table.show(g2D);
        }
    }

    public void calculate() {
        calculateTablesDimension();
        calculateDefaultPositionsForTables();
        calculateDependenciesPosition();
        calculateDependenciesDimension();
    }

    public void calculateWithoutDefaultPositions() {
        calculateTablesDimension();
        calculatePositionsForTables();
        calculateDependenciesPosition();
        calculateDependenciesDimension();
    }

    private void calculateTablesDimension() {
        for(Table table : tables) {
            table.calculateDimension();
            commonSpaceOfTables += table.getSpace();
        }
        setDimensions();
    }

    private void setDimensions() {
        float space = commonSpaceOfTables * 6;
        int defaultSpace = getWidth() * getHeight();
        if(defaultSpace < space) {
            int h = (int)Math.sqrt(2*space/3);
            int w = (int)space/h;
            setPreferredSize(new Dimension(w, h));
        }
    }

    public void fitLayoutSize() {
        Table tableWithMaxX = tables.stream().max(Comparator.comparingInt(table -> table.getPosition().getX())).get();
        Table tableWithMaxY = tables.stream().max(Comparator.comparingInt(table -> table.getPosition().getY())).get();

        setPreferredSize(new Dimension(tableWithMaxX.getPosition().getX() + tableWithMaxX.getDimension().getWidth() + 60,
                tableWithMaxY.getPosition().getY() + tableWithMaxY.getDimension().getHeight() + 60));
    }

    private void calculateDefaultPositionsForTables() {
        Random rand = new Random();
        for (Table table : tables) {
            table.getPosition().setX(rand.nextInt(getPreferredSize().width - 50));
            table.getPosition().setY(rand.nextInt(getPreferredSize().height - 50));
            table.calculatePosition();
        }
    }

    private void calculatePositionsForTables() {
        tables.forEach(Table::calculatePosition);
    }

    private void calculateDependenciesPosition() {
        tables.forEach(Table::calculateDependenciesPosition);
    }

    private void calculateDependenciesDimension() {
        dependencies.forEach(Dependence::calculateDimension);
    }

    public void setLayout(ModelScrollPane modelScrollPane) {
        this.modelScrollPane = modelScrollPane;
    }

    private void setLayoutSettings() {
        setBackground(Color.WHITE);
    }

    public void setDragController() {
        dragController = new DragController(this);
    }

    public ModelMenu getMenu() {
        return menu;
    }

    public ModelScrollPane getModelScrollPane() {
        return modelScrollPane;
    }

    public List<Table> getSelectedTables() {
        return tables.stream().filter(Table::isCursor).collect(Collectors.toList());
    }

    public void resetSelection() {
        tables.forEach(table -> table.setCursor(false));
    }

    /** Graphics */

    public void createXML(Document document) {
        Element rootElement = document.createElement("model");

        Element checkSum = document.createElement("checksum");
        checkSum.setTextContent("");
        rootElement.appendChild(checkSum);

        Element modelName = document.createElement("modelName");
        modelName.setTextContent(name);
        rootElement.appendChild(modelName);

        Element connectionName = document.createElement("connectionName");
        connectionName.setTextContent(credentials.getConnectionName());
        rootElement.appendChild(connectionName);

        Element tablesElement = document.createElement("tables");
        rootElement.appendChild(tablesElement);
        tables.forEach(tableView -> tableView.createXML(document, tablesElement));

        Element dependenciesElement = document.createElement("dependencies");
        rootElement.appendChild(dependenciesElement);
        dependencies.forEach(dependenceView -> dependenceView.createXML(document, dependenciesElement));

        document.appendChild(rootElement);
    }

    public void restorePositions(Document document) {
        tables.forEach(table -> {
            NodeList list = document.getElementsByTagName("table");
            for(int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element tableElement = (Element) node;
                    if(table.getName().equals(tableElement.getAttribute("id"))) {
                        table.getPosition().setX(Integer.parseInt(tableElement.getAttribute("x")));
                        table.getPosition().setY(Integer.parseInt(tableElement.getAttribute("y")));
                        table.setColor(new Color(Integer.parseInt(tableElement.getAttribute("r")),
                                Integer.parseInt(tableElement.getAttribute("g")),
                                        Integer.parseInt(tableElement.getAttribute("b")),
                                Integer.parseInt(tableElement.getAttribute("alpha"))));
                    }
                }
            }

        });

        dependencies.forEach(dependence -> {
            NodeList list = document.getElementsByTagName("dependence");
            for(int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element dependenceElement = (Element) node;
                    String tableFromXML = dependenceElement.getElementsByTagName("tableFrom").item(0).getTextContent();
                    String fieldFromXML = dependenceElement.getElementsByTagName("fieldFrom").item(0).getTextContent();
                    String tableToXML = dependenceElement.getElementsByTagName("tableTo").item(0).getTextContent();
                    String fieldToXML = dependenceElement.getElementsByTagName("fieldTo").item(0).getTextContent();

                    String tableFrom = dependence.getTableFrom().getName();
                    String fieldFrom = dependence.getFieldFrom().getName();
                    String tableTo = dependence.getTableTo().getName();
                    String fieldTo = dependence.getFieldTo().getName();

                    if(tableFrom.equals(tableFromXML) && fieldFrom.equals(fieldFromXML) && tableTo.equals(tableToXML) && fieldTo.equals(fieldToXML)) {
                        dependence.getNode1().setX(Integer.parseInt(dependenceElement.getAttribute("x1")));
                        dependence.getNode1().setY(Integer.parseInt(dependenceElement.getAttribute("y1")));
                        dependence.getNode2().setX(Integer.parseInt(dependenceElement.getAttribute("x2")));
                        dependence.getNode2().setY(Integer.parseInt(dependenceElement.getAttribute("y2")));
                    }
                }
            }
        });

        calculateWithoutDefaultPositions();
    }


    /* **************End block**************** */

    public ArrayList<Dependence> findTableDependencies(String tableName) {
        ArrayList<Dependence> foundDependencies = new ArrayList<>();
        ArrayList<Dependence> foundStartDependencies = findStartDependencies(tableName);
        ArrayList<Dependence> foundEndDependencies = findEndDependencies(tableName);
        foundDependencies.addAll(foundStartDependencies);
        foundDependencies.addAll(foundEndDependencies);
        return foundDependencies;
    }

    private ArrayList<Dependence> findStartDependencies(String tableName) {
        ArrayList<Dependence> foundStartDependencies = new ArrayList<>();
        for (Dependence dependence : dependencies) {

            if (tableName.equals(dependence.getTableFrom().getName())) {
                foundStartDependencies.add(dependence);
            }
        }

        return foundStartDependencies;
    }

    private ArrayList<Dependence> findEndDependencies(String tableId) {
        ArrayList<Dependence> foundEndDependencies = new ArrayList<>();
        for (Dependence dependence : dependencies) {

            if (tableId.equals(dependence.getTableTo().getName())) {
                foundEndDependencies.add(dependence);
            }
        }
        return foundEndDependencies;
    }

    public Table findTable(String tableName) {
        Table foundTable = null;
        for(Table table : tables) {
            if(tableName.equals(table.getName())) {
                foundTable = table;
                break;
            }
        }
        return foundTable;
    }

    public Dependence findDependence(String tableFrom, String fieldFrom, String tableTo, String fieldTo) {
        Dependence foundDependence = null;

        for(Dependence dependence : dependencies) {
            if(tableFrom.equals(dependence.getTableFrom().getName())
                    && fieldFrom.equals(dependence.getFieldFrom().getName())
                    && tableTo.equals(dependence.getTableTo().getName())
                    && fieldTo.equals(dependence.getFieldTo().getName())) {

                foundDependence = dependence;
                break;
            }
        }

        return foundDependence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public List<Dependence> getDependencies() {
        return dependencies;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
