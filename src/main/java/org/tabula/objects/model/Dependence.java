package org.tabula.objects.model;

import org.tabula.graphicalcomponents.modelview.primitives.Position;
import org.tabula.graphicalcomponents.modelview.primitives.Section;
import org.tabula.instances.Models;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;

public class Dependence {

    private Table tableFrom;
    private Field fieldFrom;

    private Table tableTo;
    private Field fieldTo;

    /** Graphics */
    private Section[] line;
    private Position startPosition;
    private Position endPosition;

    private Position node1;
    private Position node2;
    private int position;
    private Color color;

    private boolean flag;
    /** Graphics */

    public String getText() {
        StringBuilder text = new StringBuilder();
        text.append(tableFrom.getName());
        text.append(" ");
        text.append(fieldFrom.getName());
        text.append(" -> ");
        text.append(tableTo.getName());
        text.append(" ");
        text.append(fieldTo.getName());
        return text.toString();
    }

    public Dependence() {
        /** Graphics */
        line = new Section[3];
        line[0] = new Section();
        line[1] = new Section();
        line[2] = new Section();

        flag = false;

        startPosition = line[0].getStartPosition();
        endPosition = line[2].getEndPosition();

        position = DependenceParameters.UNKNOWN;

        color = DependenceParameters.DEPENDENCE_COLOR;

        line[0].setColor(color);
        line[1].setColor(color);
        line[2].setColor(color);

        node1 = new Position();
        node2 = new Position();
        /** Graphics */
    }

    /** Graphics */
    public void show(Graphics2D g2D) {
        for (Section s : line) {
            s.show(g2D);
        }

        paintDot(g2D);
    }

    private void paintDot(Graphics2D g2D) {
        g2D.setColor(Color.RED);
        g2D.fillOval(endPosition.getX() - 3, endPosition.getY() - 3, 6, 6);
    }

    public boolean contains(int x, int y) {

        if (line[1].getStartPosition().getY() < line[1].getEndPosition().getY()) {
            if (((line[1].getStartPosition().getX() - 1) <= x) && (x <= (line[1].getStartPosition().getX() + 1)) && y > line[1].getStartPosition().getY() && y < line[1].getEndPosition().getY()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (((line[1].getStartPosition().getX() - 1) <= x) && (x <= (line[1].getStartPosition().getX() + 1)) && y > line[1].getEndPosition().getY() && y < line[1].getStartPosition().getY()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void calculateDimension() {
        if(!tableFrom.equals(tableTo)) {
            if (flag) {
                centerInit();
            } else {
                leftInit();
            }
        } else {
            leftInitForItselfDependence();
        }

        line[0].setEndPosition(node1);
        line[1].setStartPosition(node1);

        line[1].setEndPosition(node2);
        line[2].setStartPosition(node2);
    }

    private void centerInit() {

        position = DependenceParameters.CENTER;

        node1.setX(startPosition.getX() + ((endPosition.getX() - startPosition.getX()) / 2));
        node1.setY(startPosition.getY());
        node2.setX(startPosition.getX() + ((endPosition.getX() - startPosition.getX()) / 2));
        node2.setY(endPosition.getY());
    }

    private void leftInit() {

        position = DependenceParameters.LEFT;

        node1.setY(startPosition.getY());
        node2.setY(endPosition.getY());

        if(startPosition.getX() < endPosition.getX()) {
            node1.setX(startPosition.getX() - 20);
            node2.setX(startPosition.getX() - 20);
        }

        if(startPosition.getX() > endPosition.getX()) {
            node1.setX(endPosition.getX() - 20);
            node2.setX(endPosition.getX() - 20);
        }
    }

    private void leftInitForItselfDependence() {
        node1.setY(startPosition.getY());
        node2.setY(endPosition.getY());

        node1.setX(startPosition.getX() - 30);
        node2.setX(endPosition.getX() - 30);
    }

    public void move(int newX) {
        if(position == DependenceParameters.CENTER && (line[0].getStartPosition().getX() + 10 < newX && line[2].getEndPosition().getX() - 10 > newX
                || line[2].getEndPosition().getX() + 10 < newX && line[0].getStartPosition().getX() - 10 > newX)) {
            line[1].getStartPosition().setX(newX);
            line[1].getEndPosition().setX(newX);

            line[0].getEndPosition().setX(newX);
            line[2].getStartPosition().setX(newX);
        }

        if(position == DependenceParameters.LEFT && (newX < line[0].getStartPosition().getX() - 10 && line[0].getStartPosition().getX() < line[2].getEndPosition().getX()
                || newX < line[2].getEndPosition().getX() - 10 && line[2].getEndPosition().getX() < line[0].getStartPosition().getX())) {
            line[1].getStartPosition().setX(newX);
            line[1].getEndPosition().setX(newX);

            line[0].getEndPosition().setX(newX);
            line[2].getStartPosition().setX(newX);
        }

        Models.getInstance().getCurrentModel().setSaved(false);
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public Position getNode1() {
        return node1;
    }

    public Position getNode2() {
        return node2;
    }

    /** Graphics */



    public Table getTableFrom() {
        return tableFrom;
    }

    public void setTableFrom(Table tableFrom) {
        this.tableFrom = tableFrom;
    }

    public Field getFieldFrom() {
        return fieldFrom;
    }

    public void setFieldFrom(Field fieldFrom) {
        this.fieldFrom = fieldFrom;
    }

    public Table getTableTo() {
        return tableTo;
    }

    public void setTableTo(Table tableTo) {
        this.tableTo = tableTo;
    }

    public Field getFieldTo() {
        return fieldTo;
    }

    public void setFieldTo(Field fieldTo) {
        this.fieldTo = fieldTo;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void createXML(Document document, Element element) {
        Element dependence1 = document.createElement("dependence");
        dependence1.setAttribute("x1", String.valueOf(node1.getX()));
        dependence1.setAttribute("y1", String.valueOf(node1.getY()));
        dependence1.setAttribute("x2", String.valueOf(node2.getX()));
        dependence1.setAttribute("y2", String.valueOf(node2.getY()));
        element.appendChild(dependence1);

        Element tableFrom = document.createElement("tableFrom");
        tableFrom.setTextContent(this.tableFrom.getName());
        dependence1.appendChild(tableFrom);

        Element fieldFrom = document.createElement("fieldFrom");
        fieldFrom.setTextContent(this.fieldFrom.getName());
        dependence1.appendChild(fieldFrom);

        Element tableTo = document.createElement("tableTo");
        tableTo.setTextContent(this.tableTo.getName());
        dependence1.appendChild(tableTo);

        Element fieldTo = document.createElement("fieldTo");
        fieldTo.setTextContent(this.fieldTo.getName());
        dependence1.appendChild(fieldTo);
    }
}
