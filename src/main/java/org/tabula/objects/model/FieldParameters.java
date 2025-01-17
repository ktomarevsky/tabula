package org.tabula.objects.model;

import java.awt.*;

public interface FieldParameters {

    public static final String PRIMARY_KEY = "PK";
    public static final String FOREIGN_KEY = "FK";

    public static final int FIELDHEIGHT_MIN = 30;
    public static final int KEYOFFSET = 30;
    public static final int FIELDNAMEXOFFSET = 10 + KEYOFFSET;
    public static final int FIELDNAMEYOFFSET = 20;
    public static final int TABLENAMEXOFFSET = 2;
    public static final int TABLENAMEYOFFSET = 12;
    public static final int LEFTDEPENDENCEXOFFSET = 0;
    public static final int DEPENDENCEYOFFSETUP = 5;
    public static final int DEPENDENCEYOFFSETDOWN = 5;
    public static final int DEPENDENCESTEP = 6;

    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color FIELD_COLOR = new Color(232, 230, 230);
    public static final Color FONT_COLOR = Color.BLACK;

    public static final int FONT_SIZE = 16;
    public static final int LETTERWIDTH = 12;
}
