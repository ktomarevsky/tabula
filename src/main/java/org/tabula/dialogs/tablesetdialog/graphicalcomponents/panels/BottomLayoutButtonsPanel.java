package org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class BottomLayoutButtonsPanel extends JPanel {

    private JButton saveButton;
    private JButton removeButton;
    private JButton clearButton;
    private JButton closeButton;

    public BottomLayoutButtonsPanel() {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        layout.setAlignment(FlowLayout.RIGHT);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        saveButton = new JButton("Save");
        add(saveButton);

        removeButton = new JButton("Remove");
        add(removeButton);

        clearButton = new JButton("Clear");
        add(clearButton);

        closeButton = new JButton("Close");
        add(closeButton);
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}
