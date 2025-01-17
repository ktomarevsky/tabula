package org.tabula.Main;

import org.tabula.instances.ApplicationInstance;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                JFrame.setDefaultLookAndFeelDecorated(true);

                ApplicationInstance.getInstance();
            }
        });
    }
}
