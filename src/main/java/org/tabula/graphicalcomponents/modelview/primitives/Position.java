package org.tabula.graphicalcomponents.modelview.primitives;

import java.io.Serializable;

public class Position implements Cloneable, Serializable {

    private int x;
    private int y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }
    
    public Position clone() throws CloneNotSupportedException {
        Position dot = (Position)super.clone();
        return dot;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
