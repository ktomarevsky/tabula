package org.tabula.objects.model;

import org.tabula.graphicalcomponents.modelview.primitives.Dimension;
import org.tabula.graphicalcomponents.modelview.primitives.Position;
import org.tabula.instances.Models;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;

    private List<Field> fields;

    /** Graphics */
    private Position position;
    private Dimension dimension;
    private Color color;
    private boolean cursor;

    private float space;
    /** Graphics */

    public Table() {
        fields = new ArrayList<>();
        position = new Position();
        dimension = new Dimension();

        color = TableParameters.TABLE_COLOR;
    }

    /** Graphics */
    public void show(Graphics2D g2D) {
        g2D.setColor(color);
        g2D.fillRoundRect(position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight(), TableParameters.ARC_WIDTH_HEIGHT, TableParameters.ARC_WIDTH_HEIGHT);

        g2D.setColor(TableParameters.FRAME_COLOR);
        g2D.drawRoundRect(position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight(), TableParameters.ARC_WIDTH_HEIGHT - 2, TableParameters.ARC_WIDTH_HEIGHT - 2);
        g2D.drawRoundRect(position.getX() + 1, position.getY() + 1, dimension.getWidth() - 2, dimension.getHeight() - 2, TableParameters.ARC_WIDTH_HEIGHT - 3, TableParameters.ARC_WIDTH_HEIGHT - 3);

        g2D.setFont(new Font(Font.MONOSPACED, Font.BOLD, TableParameters.FONT_SIZE));
        g2D.drawString(name, position.getX() + TableParameters.TABLENAMEXOFFSET, position.getY() + TableParameters.TABLENAMEYOFFSET);

        for (Field field : fields) {
            field.show(g2D);
        }

        if (cursor) {
            paintCursor(g2D);
        }
    }

    private void paintCursor(Graphics2D g2D) {
        g2D.setColor(Color.RED);
        g2D.drawRoundRect(position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight(), TableParameters.ARC_WIDTH_HEIGHT, TableParameters.ARC_WIDTH_HEIGHT);
        g2D.drawRoundRect(position.getX() + 1, position.getY() + 1, dimension.getWidth() - 2, dimension.getHeight() - 2, TableParameters.ARC_WIDTH_HEIGHT, TableParameters.ARC_WIDTH_HEIGHT);
        g2D.drawRoundRect(position.getX() + 2, position.getY() + 2, dimension.getWidth() - 4, dimension.getHeight() - 4, TableParameters.ARC_WIDTH_HEIGHT, TableParameters.ARC_WIDTH_HEIGHT);
    }

    public boolean contains(int x, int y) {
        return x > position.getX() && x < (position.getX() + dimension.getWidth()) && y > position.getY() && y < (position.getY() + dimension.getHeight());
    }

    public void move(int newX, int newY) {
        position.setX(newX);
        position.setY(newY);
        calculatePosition();
        calculateDependenciesPosition();
        calculateDependenciesDimension();
        Models.getInstance().getCurrentModel().setSaved(false);
    }

    public void calculateDimension() {
        calculateWidth();
        calculateHight();

        fields.forEach(field -> field.getDimension().setWidth(dimension.getWidth()));

        calculateSpace();
    }

    public void calculatePosition() {
        int shift = position.getY() + FieldParameters.FIELDHEIGHT_MIN;

        for (Field field : fields) {
            field.getPosition().setX(position.getX());
            field.getPosition().setY(shift);
            shift = shift + field.getDimension().getHeight();
        }
    }

    private void calculateWidth() {

        for (Field field : fields) {
            field.calculateDimension();
        }

        calculateTableWidth();

        int maxWidth;
        maxWidth = dimension.getWidth();

        for (Field field : fields) {
            if (field.getDimension().getWidth() > maxWidth) {
                maxWidth = field.getDimension().getWidth();
            }
        }

        dimension.setWidth(maxWidth);
    }

    private void calculateHight() {
        int height = FieldParameters.FIELDHEIGHT_MIN;
        for (Field fieldView : fields) {
            height = height + fieldView.getDimension().getHeight();
        }
        dimension.setHeight(height);
    }

    private void calculateTableWidth() {
        dimension.setWidth(FieldParameters.LETTERWIDTH * name.length());
    }

    public void calculateDependenciesPosition() {
        for (Field field : fields) {
            field.calculateDependenciesPosition();
        }
    }

    public void calculateDependenciesDimension() {
        for(Field field : fields) {
            for(Dependence dependence : field.getDependencies()) {
                dependence.calculateDimension();
            }
        }
    }

    private void calculateSpace() {
        space = dimension.getWidth() * dimension.getHeight();
    }

    public float getSpace() {
        return space;
    }

    public Position getPosition() {
        return position;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public boolean isCursor() {
        return cursor;
    }

    public void setCursor(boolean cursor) {
        this.cursor = cursor;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /** Graphics */

    public void addField(Field field) {
        fields.add(field);
    }

    public void removeField(Field field) {
        fields.remove(field);
    }

    public Field findField(String fieldName) {
        Field foundField = null;
        for(Field field : fields) {
            if(fieldName.equals(field.getName())) {
                foundField = field;
                break;
            }
        }
        return foundField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void createXML(Document document, Element element) {
        Element tableElement = document.createElement("table");
        tableElement.setAttribute("id", name);
        tableElement.setAttribute("name", name);
        tableElement.setAttribute("x", String.valueOf(getPosition().getX()));
        tableElement.setAttribute("y", String.valueOf(getPosition().getY()));
        tableElement.setAttribute("r", String.valueOf(color.getRed()));
        tableElement.setAttribute("g", String.valueOf(color.getGreen()));
        tableElement.setAttribute("b", String.valueOf(color.getBlue()));
        tableElement.setAttribute("alpha", String.valueOf(color.getAlpha()));
        element.appendChild(tableElement);
    }
}
