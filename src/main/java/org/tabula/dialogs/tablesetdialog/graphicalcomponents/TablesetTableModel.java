package org.tabula.dialogs.tablesetdialog.graphicalcomponents;

import org.tabula.objects.Tableset;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TablesetTableModel implements TableModel {

    private Set<TableModelListener> listeners;
    private List<Tableset> tablesets;

    public TablesetTableModel(List<Tableset> tablesets) {
        listeners = new HashSet<TableModelListener>();
        this.tablesets = tablesets;
    }

    @Override
    public int getRowCount() {
        return tablesets.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0 : return "Tableset name";
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
        Tableset tableset = tablesets.get(rowIndex);
        switch (columnIndex) {
            case 0 : return tableset.getName();
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

    public Tableset getSelectedTableset(int selectedRow) {
        return tablesets.get(selectedRow);
    }
}
