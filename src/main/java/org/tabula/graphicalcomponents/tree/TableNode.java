package org.tabula.graphicalcomponents.tree;

import org.tabula.objects.model.Table;

import javax.swing.tree.DefaultMutableTreeNode;

public class TableNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 1L;
    private Table table;

    public TableNode(Table table) {
        super(table.getName());
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
