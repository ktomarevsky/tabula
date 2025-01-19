package org.tabula.graphicalcomponents.tree;

import javax.swing.*;
import java.util.ResourceBundle;

public class TreeMenu extends JPopupMenu {

    private static final long serialVersionUID = 1L;
    private JMenuItem deleteItem;
    private JMenuItem showOnLayoutItem;

    public TreeMenu() {
        super();
        initComponents();
        adding();
    }

    private void initComponents() {
        var names = ResourceBundle.getBundle("names");
        deleteItem = new JMenuItem(names.getString("DELETE"));
        showOnLayoutItem = new JMenuItem(names.getString("SHOW_ON_LAYOUT"));
    }

    private void adding() {
        add(deleteItem);
        add(showOnLayoutItem);
    }

    public JMenuItem getDeleteItem() {
        return deleteItem;
    }

    public JMenuItem getShowOnLayoutItem() {
        return showOnLayoutItem;
    }
}
