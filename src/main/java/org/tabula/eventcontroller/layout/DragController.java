package org.tabula.eventcontroller.layout;

import org.tabula.model.ModelEngine;
import org.tabula.graphicalcomponents.frame.LayoutTabbedPane;
import org.tabula.graphicalcomponents.layout.ModelScrollPane;
import org.tabula.graphicalcomponents.layout.ProgressBarDialog;
import org.tabula.graphicalcomponents.modelview.menu.ModelMenu;
import org.tabula.graphicalcomponents.tree.ModelNode;
import org.tabula.graphicalcomponents.tree.ModelTree;
import org.tabula.instances.ApplicationInstance;
import org.tabula.instances.Models;
import org.tabula.objects.Credentials;
import org.tabula.objects.Tableset;
import org.tabula.objects.model.Dependence;
import org.tabula.objects.model.Model;
import org.tabula.objects.model.Table;
import org.tabula.objects.model.TableParameters;
import org.tabula.utils.dbutils.DBLoader;
import org.tabula.utils.dbutils.DBType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DragController implements Serializable {
    private Integer deltaX;
    private Integer deltaY;
    private Table selectedTable;
    private Dependence selectedDependence;
    private final ModelMenu menu;
    MouseListener mouseListener;
    MouseMotionListener mouseMotionListener;

    private Point origin;

    public DragController(final Model model) {
        menu = model.getMenu();
        JMenuItem deleteItem = menu.getDeleteItem();
        JMenuItem closeModelItem = menu.getCloseModelItem();
        JMenuItem forwardItem = menu.getForwardMappingItem();
        JMenuItem fitLayoutItem = menu.getFitLayoutItem();
        JMenuItem setColorItem = menu.getSetColorItem();

        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if(evt.getButton() == MouseEvent.BUTTON1) {
                    ModelEngine.tableSelection(model, selectedTable, evt);
                    model.repaint();
                }
                if(evt.getButton() == MouseEvent.BUTTON3) {
                    List<Table> tables = model.getTables();
                    List<Table> selectedTables = tables.stream()
                            .filter(table -> table.contains(evt.getX(), evt.getY()))
                            .collect(Collectors.toList());

                    if(selectedTables.size() > 0) {
                        selectedTable = selectedTables.get(selectedTables.size() - 1);
//                        ModelEngine.tableSelection(model, selectedTable, evt);
                        model.repaint();
                        menu.getDeleteItem().setVisible(true);
                        menu.getCreateMappingItem().setVisible(true);
                        menu.getCloseModelItem().setVisible(false);
                        menu.getForwardMappingItem().setVisible(true);
                        menu.getFitLayoutItem().setVisible(false);
                        menu.getSetColorItem().setVisible(true);
                        menu.show(model, evt.getX(), evt.getY());
                    }

                    if(selectedTables.size() == 0) {
                        menu.getDeleteItem().setVisible(false);
                        menu.getCreateMappingItem().setVisible(false);
                        menu.getCloseModelItem().setVisible(true);
                        menu.getForwardMappingItem().setVisible(false);
                        menu.getFitLayoutItem().setVisible(true);
                        menu.getSetColorItem().setVisible(false);
                        menu.show(model, evt.getX(), evt.getY());
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                origin = new Point(evt.getPoint());

                selectedTable = null;
                selectedDependence = null;

                for (Table table : model.getTables()) {
                    if (table.contains(evt.getX(), evt.getY())) {
                        selectedTable = table;
                    }
                }
                if (selectedTable != null) {
                    deltaX = evt.getX() - selectedTable.getPosition().getX();
                    deltaY = evt.getY() - selectedTable.getPosition().getY();
                    model.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
                
                for (Dependence dependence : model.getDependencies()) {
                    if (dependence.contains(evt.getX(), evt.getY())) {
                        selectedDependence = dependence;
                    }
                }
            }

            public void mouseReleased(MouseEvent evt) {
                model.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        mouseMotionListener = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent evt) {
                ModelEngine.showCursorOnDependence(model, evt);
            }

            @Override
            public void mouseDragged(MouseEvent evt) {
                if(!evt.isControlDown()) {
                    if (selectedTable != null || selectedDependence != null) {
                        ModelEngine.dragObject(model, selectedTable, selectedDependence, deltaX, deltaY, evt);
                        ModelScrollPane scrollPane = model.getModelScrollPane();
                        int horizontalValue = scrollPane.getHorizontalScrollBar().getValue();
                        int verticalValue = scrollPane.getVerticalScrollBar().getValue();
                        if(evt.getX() > scrollPane.getViewport().getWidth()) {
                            scrollPane.getHorizontalScrollBar().setValue(horizontalValue + 10);
//                            if(scrollPane.getHorizontalScrollBar().isMaximumSizeSet()) {
//                                int width = model.getWidth();
//                                model.setPreferredSize(new Dimension(width + 10, model.getHeight()));
//                            }
                        }

                        if(evt.getY() > scrollPane.getViewport().getHeight()) {
                            scrollPane.getVerticalScrollBar().setValue(verticalValue + 10);
                        }
                    }
                } else {
                    model.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    ModelScrollPane modelScrollPane = model.getModelScrollPane();
                    int dx = origin.x - evt.getX();
                    int dy = origin.y - evt.getY();
                    int startHorizontalValue = modelScrollPane.getHorizontalScrollBar().getValue();
                    int startVerticalValue = modelScrollPane.getVerticalScrollBar().getValue();
                    modelScrollPane.getHorizontalScrollBar().setValue(startHorizontalValue + dx);
                    modelScrollPane.getVerticalScrollBar().setValue(startVerticalValue + dy);
                }
            }
        };



        deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                List<Table> selectedTables = model.getSelectedTables();
                if(selectedTables.size() > 0) {
                    var names = ResourceBundle.getBundle("names");
                    int value = JOptionPane.showConfirmDialog(ApplicationInstance.getInstance().getAppFrame(), names.getString("DELETE_TABLE_QUESTION"), names.getString("DIALOG_TITLE_QUESTION"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (value == 0) {
                        ModelTree tree = ApplicationInstance.getInstance().getAppFrame().getTree();
                        ModelNode modelNode = tree.getCurrentModelNode(model);
                        ModelEngine.deleteTablesWithDependencies(model, modelNode);
                        model.updateUI();
                        tree.updateUI();
                    }
                } else {
                    var names = ResourceBundle.getBundle("names");
                    JOptionPane.showMessageDialog(ApplicationInstance.getInstance().getAppFrame(), names.getString("NO_TABLE_SELECTED"), names.getString("DIALOG_TITLE_INFO"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        forwardItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Task task = new Task();
                task.setFrame(ApplicationInstance.getInstance().getAppFrame());
                task.setModel(model);
                task.execute();
            }
        });

        fitLayoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.fitLayoutSize();
                model.updateUI();
            }
        });

        setColorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Table> tables = model.getTables().stream().filter(Table::isCursor).collect(Collectors.toList());

                if(tables.size() > 0) {
                    var names = ResourceBundle.getBundle("names");
                    Color color = JColorChooser.showDialog(ApplicationInstance.getInstance().getAppFrame(), names.getString("DIALOG_TITLE_SELECT_COLOR"), TableParameters.TABLE_COLOR);
                    if(color != null) {
                        tables.forEach(table -> table.setColor(color));
                        model.setSaved(false);
                        model.updateUI();
                    }
                } else {
                    var names = ResourceBundle.getBundle("names");
                    JOptionPane.showMessageDialog(ApplicationInstance.getInstance().getAppFrame(), names.getString("NO_TABLE_SELECTED"), names.getString("DIALOG_TITLE_INFO"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        model.addMouseListener(mouseListener);
        model.addMouseMotionListener(mouseMotionListener);

        closeModelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int result = 1;
                if(!model.isSaved()) {
                    var names = ResourceBundle.getBundle("names");
                    var message = String.format(names.getString("MODEL_NOT_SAVED_CLOSE_WITHOUT_SAVING"), model.getName());
                    result = JOptionPane.showConfirmDialog(ApplicationInstance.getInstance().getAppFrame(), message, names.getString("DIALOG_TITLE_QUESTION"),
                            JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION) {

                        LayoutTabbedPane tabbedPane = ApplicationInstance.getInstance().getAppFrame().getTabbedPane();
                        ModelScrollPane modelScrollPane = tabbedPane.getTabByModel(model);
                        ApplicationInstance.getInstance().getAppFrame().getTabbedPane().removeTab(modelScrollPane);

                        ModelTree modelTree = ApplicationInstance.getInstance().getAppFrame().getTree();
                        modelTree.removeModelNode(model);

                        tabbedPane.updateUI();
                        modelTree.updateUI();

                        Models.getInstance().removeModel(model);
                    }
                } else {
                    LayoutTabbedPane tabbedPane = ApplicationInstance.getInstance().getAppFrame().getTabbedPane();
                    ModelScrollPane modelScrollPane = tabbedPane.getTabByModel(model);
                    ApplicationInstance.getInstance().getAppFrame().getTabbedPane().removeTab(modelScrollPane);

                    ModelTree modelTree = ApplicationInstance.getInstance().getAppFrame().getTree();
                    modelTree.removeModelNode(model);

                    tabbedPane.updateUI();
                    modelTree.updateUI();

                    Models.getInstance().removeModel(model);
                }

            }
        });
    }
}

class Task extends SwingWorker<String, Object> {

    private JFrame frame;
    private Model model;
    private ProgressBarDialog dialog;

    @Override
    protected String doInBackground() throws Exception {
        Table selectedTable1 = null;

        List<Table> selectedTables = model.getSelectedTables();

        if(selectedTables.size() == 1) {
            try {
                frame.setEnabled(false);
                dialog = new ProgressBarDialog(frame);
                selectedTable1 = selectedTables.get(0);
                Tableset tableset = new Tableset();
                tableset.setTables(selectedTable1.getName());
                DBType dbType = model.getCredentials().getDbType();
                if(dbType == null) {
                    var names = ResourceBundle.getBundle("names");
                    var message = String.format(names.getString("DB_CONNECTION_NOT_EXISTS"), model.getCredentials().getConnectionName());
                    throw new RuntimeException(message);
                }
                DBLoader<Credentials, Tableset, Model> dbLoader = dbType.getDBLoader();
                dbLoader.setCredentials(model.getCredentials());
                dbLoader.executeRequest(model, tableset);
                model.calculateWithoutDefaultPositions();

                ModelTree modelTree = ApplicationInstance.getInstance().getAppFrame().getTree();
                ModelNode modelNode = modelTree.getCurrentModelNode(model);
                modelNode.clearModel();
                modelNode.createModelTree();

                model.updateUI();
                modelTree.updateUI();
                frame.setEnabled(true);
                dialog.dispose();

            } catch (RuntimeException runtimeException) {
                frame.setEnabled(true);
                dialog.dispose();
                var names = ResourceBundle.getBundle("names");
                var message = String.format(names.getString("DB_CONN_ERROR"), runtimeException);
                JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
            }


        } else if (selectedTables.size() == 0) {
            var names = ResourceBundle.getBundle("names");
            JOptionPane.showMessageDialog(frame, names.getString("NO_TABLE_SELECTED"), names.getString("DIALOG_TITLE_INFO"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            var names = ResourceBundle.getBundle("names");
            JOptionPane.showMessageDialog(frame, names.getString("MULTIPLE_TABLE_SELECTION_NOT_ALLOWED"), names.getString("DIALOG_TITLE_INFO"), JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
