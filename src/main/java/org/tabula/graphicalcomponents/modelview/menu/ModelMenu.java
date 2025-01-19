package org.tabula.graphicalcomponents.modelview.menu;

import javax.swing.*;
import java.util.ResourceBundle;

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
        var names = ResourceBundle.getBundle("names");
        deleteItem = new JMenuItem(names.getString("DELETE"));
        createMappingItem = new JMenuItem(names.getString("SHOW_MAPPING"));
        closeModelItem = new JMenuItem(names.getString("CLOSE_MODEL"));
        forwardMappingItem = new JMenuItem(names.getString("FORWARD"));
        fitLayoutItem = new JMenuItem(names.getString("FIT_SIZE"));
        setColorItem = new JMenuItem(names.getString("SET_COLOR"));
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
