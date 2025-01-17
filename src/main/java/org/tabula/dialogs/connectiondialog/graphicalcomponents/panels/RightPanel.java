package org.tabula.dialogs.connectiondialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    private TopPanel topPanel;
    private CenterPanel centerPanel;

    public RightPanel() {
        setLayout(new BorderLayout());

        initComponents();
        addComponents();
    }

    private void initComponents() {
        topPanel = new TopPanel();
        centerPanel = new CenterPanel();
    }

    private void addComponents() {
        add(topPanel, BorderLayout.PAGE_START);
        add(centerPanel, BorderLayout.CENTER);

    }

    public TopPanel getTopPanel() {
        return topPanel;
    }

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }
}
