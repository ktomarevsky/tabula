package org.tabula.instances;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImageIcons {


    static public Image mainIcon;
    static public Icon tabIcon;
    static {
        try {
            mainIcon = ImageIO.read(ImageIcons.class.getResource("/icon.jpg"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static {
        try {
            tabIcon = new ImageIcon(ImageIO.read(ImageIcons.class.getResource("/TabIcon.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
