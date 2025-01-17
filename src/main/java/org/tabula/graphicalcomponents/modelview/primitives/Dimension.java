package org.tabula.graphicalcomponents.modelview.primitives;

import java.io.Serializable;

public class Dimension implements Cloneable, Serializable {

    private int width;
    private int height;

    public Dimension() {
        this.width = 0;
        this.height = 0;
    }
    
    public Dimension clone() throws CloneNotSupportedException {
        Dimension dimension = (Dimension)super.clone();
        return dimension;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
