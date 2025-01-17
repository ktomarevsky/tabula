package org.tabula.dialogs.modelcreationdialog.eventcontroller;

import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionsTableModel;
import org.tabula.dialogs.modelcreationdialog.graphicalcomponents.ModelCreationDialog;
import org.tabula.dialogs.modelcreationdialog.utils.InputDataVerifier;
import org.tabula.dialogs.tablesetdialog.graphicalcomponents.TablesetTableModel;
import org.tabula.graphicalcomponents.frame.AppFrame;
import org.tabula.graphicalcomponents.tree.ModelNode;
import org.tabula.graphicalcomponents.tree.ModelTree;
import org.tabula.instances.Models;
import org.tabula.objects.Credentials;
import org.tabula.objects.Tableset;
import org.tabula.objects.model.Model;
import org.tabula.utils.dbutils.DBLoader;
import org.tabula.utils.dbutils.DBType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private String buffer1;
    private String buffer2;

    public Controller(ModelCreationDialog dialog) {
        buffer1 = "";
        buffer2 = "";

        JTextField modelNameField = dialog.getModelNameField();
        JButton createModelButton = dialog.getCreateModelButton();
        JTable connectionsTable = dialog.getConnectionsTable();
        JTable tablesetsTable = dialog.getTablesetsTable();

        connectionsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                dialog.setMessageText("");
                dialog.setMessageColor(Color.BLACK);
                dialog.getModelNameField().setBackground(Color.WHITE);
                ConnectionsTableModel model = (ConnectionsTableModel) dialog.getConnectionsTable().getModel();
                int selectedRow = dialog.getConnectionsTable().getSelectedRow();
                if(selectedRow != -1) {
                    Credentials credentials = model.getSelectedConnection(selectedRow);
                    buffer1 = credentials.getConnectionName();
                    if(!buffer1.isEmpty() && !buffer2.isEmpty()) {
                        modelNameField.setText(buffer1 + "_" + buffer2);
                    }
                }


            }
        });

        tablesetsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                dialog.setMessageText("");
                dialog.setMessageColor(Color.BLACK);
                dialog.getModelNameField().setBackground(Color.WHITE);
                TablesetTableModel model = (TablesetTableModel) dialog.getTablesetsTable().getModel();
                int selectedRow = dialog.getTablesetsTable().getSelectedRow();
                if(selectedRow != -1) {
                    Tableset tableset = model.getSelectedTableset(selectedRow);
                    buffer2 = tableset.getName();
                    if(!buffer1.isEmpty() && !buffer2.isEmpty()) {
                        modelNameField.setText(buffer1 + "_" + buffer2);
                    }
                }
            }
        });

        createModelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields(dialog);
                ConnectionsTableModel tableModel = (ConnectionsTableModel) connectionsTable.getModel();
                TablesetTableModel tableSetModel = (TablesetTableModel) tablesetsTable.getModel();

                int selectedConnectionRow = connectionsTable.getSelectedRow();
                int selectedTablesetRow = tablesetsTable.getSelectedRow();

                if(selectedConnectionRow != -1 && selectedTablesetRow != -1) {
                    if(InputDataVerifier.verifyModelName(dialog)) {
                        Credentials credentials = tableModel.getSelectedConnection(selectedConnectionRow);
                        Tableset tableset = tableSetModel.getSelectedTableset(selectedTablesetRow);

                        CreateModelThread thread = new CreateModelThread(dialog, credentials, tableset);
                        thread.start();
                    }
                } else {
                    dialog.setMessageText("Connection or tableset are not selected!");
                    dialog.setMessageColor(Color.RED);
                }
            }
        });
    }

    private void clearFields(ModelCreationDialog dialog) {
        dialog.setMessageText("");
        dialog.setMessageColor(Color.BLACK);
    }

    private class CreateModelThread extends Thread {
        private ModelCreationDialog dialog;
        private Credentials credentials;
        private Tableset tableset;

        private String databaseType = "ORACLE";

        public CreateModelThread(ModelCreationDialog dialog, Credentials credentials, Tableset tableset) {
            this.dialog = dialog;
            this.credentials = credentials;
            this.tableset = tableset;
        }

        public void run() {
            Model model = new Model();
            model.setName(dialog.getModelNameField().getText());
            model.setCredentials(credentials);
            Models.getInstance().setCurrentModel(model);

            AppFrame frame = (AppFrame) dialog.getParent();

            ModelTree tree = frame.getTree();
            ModelNode modelNode = new ModelNode(model, tree);

            dialog.setEnabled(false);
            dialog.setMessageText("Loading...");
            try {
                DBType dbType = model.getCredentials().getDbType();
                DBLoader<Credentials, Tableset, Model> dbLoader = dbType.getDBLoader();
                dbLoader.setCredentials(credentials);
                dbLoader.executeRequest(model, tableset);

                model.calculate();
                Models.getInstance().addModel(model);
                frame.getTabbedPane().addTab(model.getModelScrollPane());
                frame.getTabbedPane().setSelectedComponent(model.getModelScrollPane());
                tree.getRoot().add(modelNode);
                modelNode.createModelTree();
                tree.updateUI();
                tree.expandRow(0);
                dialog.dispose();
                dialog.setEnabled(true);
            } catch (RuntimeException exception) {
                dialog.setMessageText(exception.getMessage());
                dialog.setMessageColor(Color.RED);
                dialog.setEnabled(true);
            }
        }
    }
}
