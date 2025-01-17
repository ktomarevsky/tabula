package org.tabula.dialogs.connectiondialog.graphicalcomponents;

import org.tabula.objects.Credentials;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConnectionsTableModel implements TableModel {

    private Set<TableModelListener> listeners;
    private List<Credentials> credentials;

    public ConnectionsTableModel(List<Credentials> credentials) {
        listeners = new HashSet<TableModelListener>();
        this.credentials = credentials;
    }

    @Override
    public int getRowCount() {
        return credentials.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0 : return "Connection name";
            case 1 : return "Host";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Credentials credentials = this.credentials.get(rowIndex);
        switch (columnIndex) {
            case 0 : return credentials.getConnectionName();
            case 1 : return credentials.getHost();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public Credentials getSelectedConnection(int selectedRow) {
        return credentials.get(selectedRow);
    }
}
