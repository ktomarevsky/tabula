package org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {

    private TopCenterPanel topCenterPanel;
    private JTextArea textArea;

    public CenterPanel() {

        setLayout(new BorderLayout());

        topCenterPanel = new TopCenterPanel();
        add(topCenterPanel, BorderLayout.PAGE_START);

        textArea = new JTextArea();
        textArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public TopCenterPanel getTopCenterPanel() {
        return topCenterPanel;
    }
}
