package org.tabula.dialogs.tablesetdialog.eventcontroller;

import org.tabula.dialogs.tablesetdialog.graphicalcomponents.TableSetDialog;
import org.tabula.dialogs.tablesetdialog.graphicalcomponents.TablesetTableModel;
import org.tabula.dialogs.tablesetdialog.utils.InputDataVerifier;
import org.tabula.instances.Tablesets;
import org.tabula.objects.Tableset;
import org.tabula.utils.fileutils.FileReaderWriter;
import org.tabula.utils.xmlutils.XMLHandler;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller {

    public Controller(TableSetDialog dialog) {

        JTable table = dialog.getTable();
        JButton saveButton = dialog.getSaveButton();
        JButton removeButton = dialog.getRemoveButton();
        JButton clearButton = dialog.getClearButton();
        JButton closeButton = dialog.getCloseButton();

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clearFields(dialog);

                TablesetTableModel model = (TablesetTableModel) table.getModel();
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1) {
                    Tableset tableset = model.getSelectedTableset(selectedRow);
                    dialog.getTablesetName().setText(tableset.getName());
                    dialog.getTextArea().setText(tableset.getTables());
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = InputDataVerifier.verifyAllFields(dialog);

                if(flag) {
                    List<Tableset> tablesets = Tablesets.getInstance().getTablesets();
                    String name = dialog.getTablesetName().getText();

                    Tableset foundTableset = findSelectedTableset(tablesets, name);

                    String text = dialog.getTextArea().getText().replace("\n", "");

                    if (foundTableset != null) {
                        foundTableset.setTables(text);
                        dialog.setMessageText("Tableset " + foundTableset.getName() + " was updated successfully!");
                    } else {
                        Tableset tableset = new Tableset();
                        tableset.setName(dialog.getTablesetName().getText());
                        tableset.setTables(text);
                        tablesets.add(tableset);
                        dialog.setMessageText("Tableset " + tableset.getName() + " was saved successfully!");
                    }

                    dialog.getTable().updateUI();
                    Document document = XMLHandler.createXMLWithTablesets(tablesets);
                    FileReaderWriter.writeXMLToFile(document, FileReaderWriter.PATH_TO_TABLESET_LIST);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TablesetTableModel model = (TablesetTableModel) table.getModel();
                int selectedRow = table.getSelectedRow();

                if(selectedRow != -1) {
                    Tableset tableset = model.getSelectedTableset(selectedRow);
                    Tablesets.getInstance().getTablesets().remove(tableset);
                    clearFields(dialog);
                    table.updateUI();
                    Document document = XMLHandler.createXMLWithTablesets(Tablesets.getInstance().getTablesets());
                    FileReaderWriter.writeXMLToFile(document, FileReaderWriter.PATH_TO_TABLESET_LIST);
                    dialog.setMessageText("Tableset " + tableset.getName() + " was deleted successfully!");
                } else {
                    dialog.setMessageText("Tableset to delete is not selected!");
                    dialog.setMessageColor(Color.RED);
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields(dialog);
                table.clearSelection();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    private Tableset findSelectedTableset(List<Tableset> tablesets, String name) {
        Tableset foundTableset = null;
        for(Tableset tableset : tablesets) {
            if(name.equals(tableset.getName())) {
                foundTableset = tableset;
                break;
            }
        }

        return foundTableset;
    }

    private void clearFields(TableSetDialog dialog) {
        dialog.setMessageText("");
        dialog.setMessageColor(Color.BLACK);
        dialog.getTablesetName().setText("");
        dialog.getTextArea().setText("");
        dialog.getTablesetName().setBackground(Color.WHITE);
        dialog.getTextArea().setBackground(Color.WHITE);
    }
}
