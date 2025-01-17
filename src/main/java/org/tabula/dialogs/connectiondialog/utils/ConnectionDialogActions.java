package org.tabula.dialogs.connectiondialog.utils;

import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionDialog;
import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionsTableModel;
import org.tabula.objects.Credentials;
import org.tabula.utils.dbutils.DBType;

import java.awt.*;
import java.util.List;

public class ConnectionDialogActions {

    static public void saveConnection(ConnectionDialog dialog) {
        List<Credentials> credentialsList = dialog.getConnections();
        String name = dialog.getConnectionName().getText();

        Credentials foundCredentials = findConnection(credentialsList, name);

        if (foundCredentials != null) {
            foundCredentials.setLogin(dialog.getUsername().getText());
            foundCredentials.setPassword(dialog.getPassword().getText());
            foundCredentials.setHost(dialog.getHost().getText());
            foundCredentials.setPort(dialog.getPort().getText());
            foundCredentials.setSid(dialog.getSID().getText());
            foundCredentials.setDbType(DBType.valueOf(dialog.getDbType().getSelectedItem().toString()));
            dialog.setMessageText("Connection " + foundCredentials.getConnectionName() + " was updated successfully!");
        } else {

            Credentials credentials = new Credentials();

            credentials.setConnectionName(dialog.getConnectionName().getText());
            credentials.setLogin(dialog.getUsername().getText());
            credentials.setPassword(dialog.getPassword().getText());
            credentials.setHost(dialog.getHost().getText());
            credentials.setPort(dialog.getPort().getText());
            credentials.setSid(dialog.getSID().getText());
            credentials.setDbType(DBType.valueOf(dialog.getDbType().getSelectedItem().toString()));

            credentialsList.add(credentials);
            dialog.setMessageText("Connection " + credentials.getConnectionName() + " was saved successfully!");
        }
    }

    static public void fillFields(ConnectionDialog dialog) {
        ConnectionsTableModel model = (ConnectionsTableModel) dialog.getTable().getModel();
        int selectedRow = dialog.getTable().getSelectedRow();
        if(selectedRow != -1) {
            Credentials credentials = model.getSelectedConnection(selectedRow);
            dialog.getConnectionName().setText(credentials.getConnectionName());
            dialog.getUsername().setText(credentials.getLogin());
            dialog.getPassword().setText(credentials.getPassword());
            dialog.getHost().setText(credentials.getHost());
            dialog.getPort().setText(credentials.getPort());
            dialog.getSID().setText(credentials.getSid());
            dialog.getDbType().setSelectedItem(credentials.getDbType());
        }
    }

    static public void clearFields(ConnectionDialog dialog) {
        dialog.getConnectionName().setText("");
        dialog.getUsername().setText("");
        dialog.getPassword().setText("");
        dialog.getHost().setText("");
        dialog.getPort().setText("");
        dialog.getSID().setText("");
        dialog.getDbType().setSelectedItem(null);

        dialog.getConnectionName().setBackground(Color.WHITE);
        dialog.getUsername().setBackground(Color.WHITE);
        dialog.getPassword().setBackground(Color.WHITE);
        dialog.getHost().setBackground(Color.WHITE);
        dialog.getPort().setBackground(Color.WHITE);
        dialog.getSID().setBackground(Color.WHITE);
        dialog.getDbType().setBackground(Color.WHITE);
        dialog.setMessageText("");
        dialog.setMessageColor(Color.BLACK);
    }

    static public Credentials findConnection(List<Credentials> credentialsList, String name) {
        Credentials result = null;

        for(Credentials credentials : credentialsList){
            if(credentials.getConnectionName().equals(name)) {
                result = credentials;
                break;
            }
        }
        return result;
    }
}
