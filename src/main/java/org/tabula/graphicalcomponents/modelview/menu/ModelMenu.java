package org.tabula.graphicalcomponents.modelview.menu;

import org.tabula.utils.TextLoader;

import javax.swing.*;

public class ModelMenu extends JPopupMenu {

    private JMenuItem deleteItem;
    private JMenuItem createMappingItem;

    private JMenuItem closeModelItem;

    private JMenuItem forwardMappingItem;

    private JMenuItem fitLayoutItem;

    private JMenuItem setColorItem;

    public ModelMenu() {
        super();
        initComponents();
        adding();
    }

    public void initComponents() {
        deleteItem = new JMenuItem(TextLoader.DELETE);
        createMappingItem = new JMenuItem(TextLoader.SHOW_MAPPING);
        closeModelItem = new JMenuItem("Close model");
        forwardMappingItem = new JMenuItem("Forward");
        fitLayoutItem = new JMenuItem("Fit layout size");
        setColorItem = new JMenuItem("Set color");
    }

    public void adding() {
        add(deleteItem);
//        add(createMappingItem);
        add(closeModelItem);
        add(forwardMappingItem);
        add(fitLayoutItem);
        add(setColorItem);
    }

    public JMenuItem getDeleteItem() {
        return deleteItem;
    }

    public JMenuItem getCreateMappingItem() {
        return createMappingItem;
    }

    public JMenuItem getCloseModelItem() {
        return closeModelItem;
    }

    public JMenuItem getForwardMappingItem() {
        return forwardMappingItem;
    }

    public JMenuItem getFitLayoutItem() {
        return fitLayoutItem;
    }

    public JMenuItem getSetColorItem() {
        return setColorItem;
    }
}
