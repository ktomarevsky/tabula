package org.tabula.dialogs.connectiondialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {

    private TopPanelInCenter topPanelInCenterPanel;

    public CenterPanel() {
        setPreferredSize(new Dimension(100, 100));
        setLayout(new BorderLayout());

        topPanelInCenterPanel = new TopPanelInCenter();
        add(topPanelInCenterPanel, BorderLayout.PAGE_START);
    }

    public TopPanelInCenter getTopPanelInCenterPanel() {
        return topPanelInCenterPanel;
    }
}
