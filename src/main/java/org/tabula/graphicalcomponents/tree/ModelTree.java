package org.tabula.graphicalcomponents.tree;

import org.tabula.eventcontroller.treecontroller.TreeController;
import org.tabula.objects.model.Model;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Enumeration;

public class ModelTree extends JTree {

    private DefaultMutableTreeNode root;
    private TreeMenu menu;

    private TreeController treeController;

    public ModelTree() {
        super(new DefaultTreeModel(new DefaultMutableTreeNode("Models")));
        this.root = (DefaultMutableTreeNode) treeModel.getRoot();
        menu = new TreeMenu();

        treeController = new TreeController(this);
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }


    public TreeMenu getMenu() {
        return menu;
    }

    public void removeModelNode(Model model) {
        ModelNode modelNode = null;
        Enumeration<ModelNode> modelNodes = Enumeration.class.cast(root.children());
        while(modelNodes.hasMoreElements()) {
            modelNode = modelNodes.nextElement();
            if(model.equals(modelNode.getModel())) {
                break;
            }
        }
        root.remove(modelNode);
    }

    public ModelNode getCurrentModelNode(Model currentModel) {
        Enumeration<ModelNode> nodes = Enumeration.class.cast(getRoot().children());
        ModelNode currentNode = null;
        while(nodes.hasMoreElements()) {
            ModelNode node = nodes.nextElement();
            if(node.getModel().getName().equals(currentModel.getName())) {
                currentNode = node;
                break;
            }
        }

        return currentNode;
    }
}
