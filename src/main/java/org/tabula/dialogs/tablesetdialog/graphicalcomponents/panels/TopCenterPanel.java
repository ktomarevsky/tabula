package org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class TopCenterPanel extends JPanel {

    private JTextField tablesetName;
    private JLabel label;

    public TopCenterPanel() {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        layout.setAlignment(FlowLayout.RIGHT);
        setPreferredSize(new Dimension(300, 50));
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        tablesetName = new JTextField();
        tablesetName.setPreferredSize(new Dimension(160, 30));
        add(tablesetName);

        label = new JLabel("Tableset name");
        add(label);
    }

    public JTextField getTablesetName() {
        return tablesetName;
    }
}
