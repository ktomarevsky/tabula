package org.tabula.eventcontroller.treecontroller;

import org.tabula.model.ModelEngine;
import org.tabula.graphicalcomponents.frame.LayoutTabbedPane;
import org.tabula.graphicalcomponents.layout.ModelScrollPane;
import org.tabula.graphicalcomponents.tree.ModelNode;
import org.tabula.graphicalcomponents.tree.ModelTree;
import org.tabula.graphicalcomponents.tree.TableNode;
import org.tabula.graphicalcomponents.tree.TreeMenu;
import org.tabula.instances.ApplicationInstance;
import org.tabula.instances.Models;
import org.tabula.objects.model.Model;
import org.tabula.objects.model.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeController {
    private final TreeMenu menu;
    private Icon icon;

    public TreeController(ModelTree modelTree) {

        menu = modelTree.getMenu();
        JMenuItem deleteItem = menu.getDeleteItem();
        JMenuItem showOnLayoutItem = menu.getShowOnLayoutItem();

        Image image = null;
        //Icon icon = null;
        try {
            image = ImageIO.read(getClass().getResource("/icon.jpg"));
            icon = new ImageIcon(image);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        modelTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                TreePath[] paths = modelTree.getSelectionPaths();
                if(paths != null) {
                    List<TreePath> list = Arrays.asList(paths);
                    if (evt.getButton() == MouseEvent.BUTTON3 && list.size() == 1 && list.stream().noneMatch(path -> path.getPathCount() < 4)) {
                        menu.show(modelTree, evt.getX(), evt.getY());
                    }
                }
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] paths = modelTree.getSelectionPaths();
                if(paths != null) {
                    List<TreePath> list = Arrays.asList(paths);
                    if (list.stream().noneMatch(path -> path.getPathCount() < 4)) {
                        Model currentModel = Models.getInstance().getCurrentModel();
                        if (list.stream().allMatch(path -> path.getPathComponent(1).toString().equals(currentModel.getName()) && path.getPathComponent(2).toString().equals("Tables"))) {

                            ModelNode currentNode = modelTree.getCurrentModelNode(currentModel);

                            if(currentNode != null) {
                                int value = JOptionPane.showConfirmDialog(ApplicationInstance.getInstance().getAppFrame(), "Are you sure you want to drop these tables?", "Confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if(value == 0) {
                                    currentModel.resetSelection();
                                    list.forEach(path -> {
                                        Table table = currentModel.findTable(path.getLastPathComponent().toString());
                                        table.setCursor(true);
                                    });
                                    ModelEngine.deleteTablesWithDependencies(currentModel, currentNode);
                                    currentModel.updateUI();
                                    modelTree.updateUI();
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(ApplicationInstance.getInstance().getAppFrame(), "Only tables of the current model can be deleted!", "Error!",  JOptionPane.ERROR_MESSAGE, icon);
                        }
                    } else {
                        JOptionPane.showMessageDialog(ApplicationInstance.getInstance().getAppFrame(), "Only tables of the current model can be deleted!", "Error!",  JOptionPane.ERROR_MESSAGE, icon);
                    }
                }
            }
        });

        showOnLayoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] paths = modelTree.getSelectionPaths();
                if(paths != null) {
                    List<TreePath> list = Arrays.asList(paths);
                    if (list.size() == 1 && list.stream().noneMatch(path -> path.getPathCount() < 4)) {
                        if("Tables".equals(list.get(0).getPathComponent(2).toString())) {
                            String modelName = list.get(0).getPathComponent(1).toString();
                            Model model = Models.getInstance().getModels().stream().filter(value -> value.getName().equals(modelName)).findFirst().get();
                            LayoutTabbedPane tabbedPane = ApplicationInstance.getInstance().getAppFrame().getTabbedPane();
                            ModelScrollPane modelScrollPane = tabbedPane.getTabByModel(model);
                            TableNode node = (TableNode) modelTree.getLastSelectedPathComponent();
                            Table table = node.getTable();
                            modelScrollPane.getModel().getTables().forEach(value -> value.setCursor(false));
                            table.setCursor(true);
                            Collections.swap(modelScrollPane.getModel().getTables(), modelScrollPane.getModel().getTables().indexOf(table), modelScrollPane.getModel().getTables().size() - 1);
                            tabbedPane.setSelectedComponent(modelScrollPane);
                            modelScrollPane.getHorizontalScrollBar().setValue(table.getPosition().getX());
                            modelScrollPane.getVerticalScrollBar().setValue(table.getPosition().getY());
                            modelScrollPane.repaint();
                        } else {
                            JOptionPane.showMessageDialog(ApplicationInstance.getInstance().getAppFrame(), "Only tables can be highlited on layout!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
    }
}
