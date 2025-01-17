package org.tabula.dialogs.connectiondialog.eventcontroller;

import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionDialog;
import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionsTableModel;
import org.tabula.dialogs.connectiondialog.utils.ConnectionDialogActions;
import org.tabula.dialogs.connectiondialog.utils.InputDataVerifier;
import org.tabula.instances.CredentialsInstance;
import org.tabula.objects.Credentials;
import org.tabula.utils.fileutils.FileReaderWriter;
import org.tabula.utils.xmlutils.XMLHandler;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

public class Controller {

    public Controller(ConnectionDialog dialog) {
        JTable table = dialog.getTable();

        JButton saveButton = dialog.getSaveButton();
        JButton deleteButton = dialog.getDeleteButton();
        JButton clearButton = dialog.getClearButton();
        JButton testButton = dialog.getTestButton();
        JButton cancelButton = dialog.getCancelButton();

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ConnectionDialogActions.clearFields(dialog);
                ConnectionDialogActions.fillFields(dialog);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = InputDataVerifier.verifyAllFields(dialog);

                if(flag) {
                    ConnectionDialogActions.saveConnection(dialog);
                    table.updateUI();
                    Document document = XMLHandler.createXMLWithCredentials(CredentialsInstance.getInstance().getCredentialsList());
                    FileReaderWriter.writeXMLToFile(document, FileReaderWriter.PATH_TO_CREDENTIALS_LIST);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectionsTableModel model = (ConnectionsTableModel) dialog.getTable().getModel();
                int selectedRow = dialog.getTable().getSelectedRow();
                if(selectedRow != -1) {
                    Credentials credentials = model.getSelectedConnection(selectedRow);
                    CredentialsInstance.getInstance().getCredentialsList().remove(credentials);
                    ConnectionDialogActions.clearFields(dialog);
                    table.updateUI();
                    Document document = XMLHandler.createXMLWithCredentials(CredentialsInstance.getInstance().getCredentialsList());
                    FileReaderWriter.writeXMLToFile(document, FileReaderWriter.PATH_TO_CREDENTIALS_LIST);
                    dialog.setMessageText("Connection " + credentials.getConnectionName() + " was deleted successfully!");
                }
                else {
                    dialog.setMessageText("Connection to delete is not selected!");
                    dialog.setMessageColor(Color.RED);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectionDialogActions.clearFields(dialog);
                dialog.getTable().clearSelection();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean flag = InputDataVerifier.verifyAllFields(dialog);
                if(flag) {
                    TestConnectionThread thread = new TestConnectionThread(dialog);
                    thread.start();
                }
            }
        });
    }

    private class TestConnectionThread extends Thread {

        private ConnectionDialog dialog;

        public TestConnectionThread(ConnectionDialog dialog) {
            this.dialog = dialog;
        }

        public void run() {
            String login = dialog.getUsername().getText();
            String password = dialog.getPassword().getText();

            String host = dialog.getHost().getText();
            String port = dialog.getPort().getText();
            String sid = dialog.getSID().getText();
            String result = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
            dialog.setEnabled(false);
            Connection connection = null;
            try {
                Class.forName(FileReaderWriter.getParameter("oracle_driver"));
                connection = DriverManager.getConnection(result, login, password);
                if(connection != null) {
                    dialog.setMessageText("Connection OK.");
                    dialog.setMessageColor(Color.BLACK);
                    connection.close();
                }
            } catch (Exception exception) {
                dialog.setMessageText(exception.getMessage());
                dialog.setMessageColor(Color.RED);
            }
            dialog.setEnabled(true);
        }
    }
}
