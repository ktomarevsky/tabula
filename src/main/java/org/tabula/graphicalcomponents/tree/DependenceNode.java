package org.tabula.graphicalcomponents.tree;

import org.tabula.objects.model.Dependence;

import javax.swing.tree.DefaultMutableTreeNode;

public class DependenceNode extends DefaultMutableTreeNode {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Dependence dependence;

    public DependenceNode(Dependence dependence) {
        super(dependence.getText());
        this.dependence = dependence;
    }

    public Dependence getDependence() {
        return dependence;
    }
}
