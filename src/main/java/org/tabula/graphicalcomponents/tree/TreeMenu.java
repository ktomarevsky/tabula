package org.tabula.graphicalcomponents.tree;

import javax.swing.*;

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
        deleteItem = new JMenuItem("Delete");
        showOnLayoutItem = new JMenuItem("Show on layout");
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
