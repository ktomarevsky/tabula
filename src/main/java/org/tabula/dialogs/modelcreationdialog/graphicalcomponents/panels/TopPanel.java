package org.tabula.dialogs.modelcreationdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private JLabel fieldName;
    private JTextField modelNameField;
    private JLabel text;

    public TopPanel() {
        setPreferredSize(new Dimension(300, 50));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
        initComponents();
    }

    private void initComponents() {
        fieldName = new JLabel("Model name:");
        add(fieldName, BorderLayout.LINE_START);

        modelNameField = new JTextField();
        modelNameField.setPreferredSize(new Dimension(400, 20));
        add(modelNameField, BorderLayout.LINE_END);

        text = new JLabel("Choose configured database connection and table set in the tables below:");
        add(text,BorderLayout.PAGE_END);
    }

    public JTextField getModelNameField() {
        return modelNameField;
    }
}
