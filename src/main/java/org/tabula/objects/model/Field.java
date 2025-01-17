package org.tabula.objects.model;

import org.tabula.graphicalcomponents.modelview.primitives.Dimension;
import org.tabula.graphicalcomponents.modelview.primitives.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Field {

    private String name;
    private Table table;
    private String keyType;

    /** Graphics */
    private Position position;
    private Dimension dimension;

    private Color color;
    /** Graphics */

    private List<Dependence> dependencies;

    public Field() {
        dependencies = new ArrayList<>();

        /** Graphics */
        position = new Position();
        dimension = new Dimension();
        dimension.setHeight(FieldParameters.FIELDHEIGHT_MIN);
        color = FieldParameters.FRAME_COLOR;
        /** Graphics */
    }

    /** Graphics */
    public void show(Graphics2D g2D) {
        g2D.setColor(FieldParameters.FIELD_COLOR);
        g2D.setColor(FieldParameters.FRAME_COLOR);
        g2D.drawLine(position.getX(), position.getY(),position.getX() + dimension.getWidth(), position.getY());

        g2D.setColor(FieldParameters.FRAME_COLOR);
        g2D.drawLine(position.getX() + FieldParameters.KEYOFFSET, position.getY() + 1,
                position.getX() + FieldParameters.KEYOFFSET, position.getY() + dimension.getHeight() - 2);
        g2D.drawString(keyType, position.getX() + FieldParameters.TABLENAMEXOFFSET + 5, position.getY() + FieldParameters.FIELDNAMEYOFFSET);
        g2D.setColor(FieldParameters.FONT_COLOR);
        g2D.setFont(new Font(Font.MONOSPACED, Font.BOLD, FieldParameters.FONT_SIZE));
        g2D.drawString(name, position.getX() + FieldParameters.FIELDNAMEXOFFSET, position.getY() + FieldParameters.FIELDNAMEYOFFSET);
    }

    public void calculateDimension() {
        calculateWidth();
        calculateHight();
    }

    private void calculateWidth() {
        dimension.setWidth(FieldParameters.LETTERWIDTH * name.length() + FieldParameters.FIELDNAMEXOFFSET);
    }

    private void calculateHight() {
        dimension.setHeight(FieldParameters.FIELDHEIGHT_MIN + FieldParameters.DEPENDENCESTEP * dependencies.size());
    }

    public void calculateDependenciesPosition() {
        int count = 0;
        for (Dependence dependence : dependencies) {
            Field fieldFrom = dependence.getFieldFrom();
            Field fieldTo = dependence.getFieldTo();

            if(!dependence.getTableFrom().equals(dependence.getTableTo())) {

                int delta = fieldTo.position.getX() - fieldFrom.position.getX();

                boolean flag = false;
                if(delta > 0) {
                    if (Math.abs(delta) < fieldFrom.dimension.getWidth() + 30) {
                        flag = false;
                    }

                    if (Math.abs(delta) > fieldFrom.dimension.getWidth() + 30) {
                        flag = true;
                    }
                }

                if(delta < 0) {
                    if (Math.abs(delta) < fieldTo.dimension.getWidth() + 30) {
                        flag = false;
                    }

                    if (Math.abs(delta) > fieldTo.dimension.getWidth() + 30) {
                        flag = true;
                    }
                }

                dependence.setFlag(flag);

                if (fieldFrom == this) {

                    Position startDependencePosition = dependence.getStartPosition();
                    Position endDependencePosition = dependence.getEndPosition();
                    Position fieldFromPosition = fieldFrom.position;
                    Position fieldToPosition = fieldTo.position;


                    if (delta > 0 && flag == false) {
                        startDependencePosition.setX(fieldFromPosition.getX());
                    }
                    if (delta > 0 && flag == true) {
                        startDependencePosition.setX(fieldFromPosition.getX() + fieldFrom.dimension.getWidth());
                        endDependencePosition.setX(fieldToPosition.getX());
                    }

                    if (delta < 0 && flag == false) {
                        startDependencePosition.setX(fieldFromPosition.getX());
                        endDependencePosition.setX(fieldToPosition.getX());
                    }

                    if (delta < 0 && flag == true) {
                        startDependencePosition.setX(fieldFromPosition.getX());
                        endDependencePosition.setX(fieldToPosition.getX() + fieldTo.dimension.getWidth());
                    }


                    startDependencePosition.setY(fieldFromPosition.getY() + FieldParameters.DEPENDENCEYOFFSETUP + FieldParameters.DEPENDENCESTEP * count);
                    count++;
                }

                if (fieldTo == this) {
                    Position startDependencePosition = dependence.getStartPosition();
                    Position endDependencePosition = dependence.getEndPosition();
                    Position fieldFromPosition = fieldFrom.position;
                    Position fieldToPosition = fieldTo.position;


                    if (delta > 0 && flag == false) {
                        endDependencePosition.setX(fieldToPosition.getX());
                        startDependencePosition.setX(fieldFromPosition.getX());
                    }
                    if (delta > 0 && flag == true) {
                        endDependencePosition.setX(fieldToPosition.getX());
                        startDependencePosition.setX(fieldFromPosition.getX() + fieldFrom.dimension.getWidth());
                    }
                    if (delta < 0 && flag == false) {
                        endDependencePosition.setX(fieldToPosition.getX());
                    }
                    if (delta < 0 && flag == true) {
                        endDependencePosition.setX(fieldToPosition.getX() + fieldTo.dimension.getWidth());
                        startDependencePosition.setX(fieldFromPosition.getX());
                    }

                    endDependencePosition.setY(fieldToPosition.getY() + FieldParameters.DEPENDENCEYOFFSETUP + FieldParameters.DEPENDENCESTEP * count);
                    count++;
                }
            } else {
                Position startDependencePosition = dependence.getStartPosition();
                Position endDependencePosition = dependence.getEndPosition();
                Position fieldFromPosition = fieldFrom.position;
                Position fieldToPosition = fieldTo.position;

                startDependencePosition.setX(fieldFromPosition.getX());
                endDependencePosition.setX(fieldToPosition.getX());

                startDependencePosition.setY(fieldFromPosition.getY() + FieldParameters.DEPENDENCEYOFFSETUP + FieldParameters.DEPENDENCESTEP * count);
                count++;
                endDependencePosition.setY(fieldToPosition.getY() + FieldParameters.DEPENDENCEYOFFSETUP + FieldParameters.DEPENDENCESTEP * count);
                count++;
            }
        }
    }

    public Position getPosition() {
        return position;
    }

    public Dimension getDimension() {
        return dimension;
    }

    /** Graphics */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public List<Dependence> getDependencies() {
        return dependencies;
    }

    public void addDependence(Dependence dependence) {
        dependencies.add(dependence);
    }

    public void removeDependence(Dependence dependence) {
        dependencies.remove(dependence);
    }
}
