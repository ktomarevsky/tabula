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
import java.util.ResourceBundle;

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
                                var names = ResourceBundle.getBundle("names");
                                int value = JOptionPane.showConfirmDialog(ApplicationInstance.getInstance().getAppFrame(), names.getString("DELETE_TABLE_QUESTION"), names.getString("DIALOG_TITLE_QUESTION"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
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
                            var names = ResourceBundle.getBundle("names");
                            JOptionPane.showMessageDialog(ApplicationInstance.getInstance().getAppFrame(), names.getString("DELETE_TABLE_NOT_ALLOWED"), names.getString("DIALOG_TITLE_ERROR"),  JOptionPane.ERROR_MESSAGE, icon);
                        }
                    } else {
                        var names = ResourceBundle.getBundle("name");
                        JOptionPane.showMessageDialog(ApplicationInstance.getInstance().getAppFrame(), names.getString("DELETE_TABLE_NOT_ALLOWED_ANOTHER_MODEL"), names.getString("DIALOG_TITLE_ERROR"),  JOptionPane.ERROR_MESSAGE, icon);
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
