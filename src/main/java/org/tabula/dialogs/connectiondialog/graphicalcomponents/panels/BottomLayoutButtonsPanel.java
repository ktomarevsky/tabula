package org.tabula.dialogs.connectiondialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class BottomLayoutButtonsPanel extends JPanel {

    private JButton saveButton;
    private JButton clearButton;
    private JButton testButton;
    private JButton cancelButton;
    private JButton deleteButton;

    public BottomLayoutButtonsPanel() {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        layout.setAlignment(FlowLayout.RIGHT);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        saveButton = new JButton("Save");
        add(saveButton);

        deleteButton = new JButton("Delete");
        add(deleteButton);

        clearButton = new JButton("Clear");
        add(clearButton);

        testButton = new JButton("Test");
        add(testButton);

        cancelButton = new JButton("Cancel");
        add(cancelButton);
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getTestButton() {
        return testButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
