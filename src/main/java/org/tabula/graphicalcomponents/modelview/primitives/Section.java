package org.tabula.graphicalcomponents.modelview.primitives;

import java.awt.*;

public class Section {

    private Position startPosition;
    private Position endPosition;
    private Color color;

    public Section() {
        startPosition = new Position();
        endPosition = new Position();
    }

    public void show(Graphics2D g2D) {
        g2D.setColor(color);
        g2D.drawLine(startPosition.getX(), startPosition.getY(), endPosition.getX(), endPosition.getY());
    }

    public Section clone() throws CloneNotSupportedException {
        Section section = (Section) super.clone();
        section.startPosition = startPosition.clone();
        section.endPosition = endPosition.clone();
        return section;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
