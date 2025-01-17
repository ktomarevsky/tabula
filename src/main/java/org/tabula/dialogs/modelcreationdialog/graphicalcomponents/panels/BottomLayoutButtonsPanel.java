package org.tabula.dialogs.modelcreationdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class BottomLayoutButtonsPanel extends JPanel {

    private JButton createModelButton;

    public BottomLayoutButtonsPanel() {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        layout.setAlignment(FlowLayout.RIGHT);
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        createModelButton = new JButton("Load data and create model");
        add(createModelButton);
    }

    public JButton getCreateModelButton() {
        return createModelButton;
    }
}
