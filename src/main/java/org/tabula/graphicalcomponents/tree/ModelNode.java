package org.tabula.graphicalcomponents.tree;

import org.tabula.objects.model.Dependence;
import org.tabula.objects.model.Model;
import org.tabula.objects.model.Table;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class ModelNode extends DefaultMutableTreeNode {

    private ModelTree tree;
    private Model model;
    private DefaultMutableTreeNode tableNodes;
    private DefaultMutableTreeNode dependenceNodes;

    public ModelNode(Model model, ModelTree tree) {
        super(model.getName());
        this.tree = tree;
        this.model = model;
        var names = ResourceBundle.getBundle("names");
        tableNodes = new DefaultMutableTreeNode(names.getString("TREE_NODES_TABLES"));
        dependenceNodes = new DefaultMutableTreeNode(names.getString("TREE_NODES_DEPENDENCIES"));
        add(tableNodes);
        add(dependenceNodes);
    }

    public void createModelTree() {
        List<Table> tables = model.getTables();
        tables.forEach(table -> {
            TableNode tableNode = new TableNode(table);
            tableNodes.add(tableNode);
        });

        List<Dependence> dependencies = model.getDependencies();
        dependencies.forEach(dependence -> {
            DependenceNode dependenceNode = new DependenceNode(dependence);
            dependenceNodes.add(dependenceNode);
        });

        tree.expandRow(0);
    }

    public void clearModel() {
        tableNodes.removeAllChildren();
        dependenceNodes.removeAllChildren();
    }


    public void deleteTableNodes(List<Table> tables)
    {
        List<TableNode> selectedTableNodes = new ArrayList<TableNode>();
        Enumeration<TableNode> nodes = Enumeration.class.cast(tableNodes.children());

        while(nodes.hasMoreElements()) {
            TableNode node = nodes.nextElement();
            for(Object object : tables) {
                Table table = (Table) object;
                if(node.getTable().equals(table)) {
                    selectedTableNodes.add(node);
                }
            }
        }

        for(TableNode node : selectedTableNodes) {
            tableNodes.remove(node);
        }
    }

    public void deleteDependenceNodes(List<Dependence> dependencies) {
        List<DependenceNode> selectedDependenceNodes = new ArrayList<DependenceNode>();
        Enumeration<DependenceNode> nodes = Enumeration.class.cast(dependenceNodes.children());

        while(nodes.hasMoreElements()) {
            DependenceNode node = nodes.nextElement();
            for(Object object : dependencies) {
                Dependence dependence = (Dependence) object;
                if(node.getDependence().equals(dependence)) {
                    selectedDependenceNodes.add(node);
                }
            }
        }

        for(DependenceNode dependenceNode : selectedDependenceNodes) {
            dependenceNodes.remove(dependenceNode);
        }
    }

    public DefaultMutableTreeNode getTableNodes() {
        return tableNodes;
    }

    public DefaultMutableTreeNode getDependenceNodes() {
        return dependenceNodes;
    }

    public Model getModel() {
        return model;
    }
}
