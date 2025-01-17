package org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {

    private BottomLayoutMessagePanel messagePanel;
    private BottomLayoutButtonsPanel buttonPanel;

    public BottomPanel() {
        setPreferredSize(new Dimension(300, 60));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        messagePanel = new BottomLayoutMessagePanel();
        add(messagePanel);

        buttonPanel = new BottomLayoutButtonsPanel();
        add(buttonPanel);
    }

    public BottomLayoutMessagePanel getMessagePanel() {
        return messagePanel;
    }

    public BottomLayoutButtonsPanel getButtonPanel() {
        return buttonPanel;
    }
}
