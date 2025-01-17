package org.tabula.dialogs.connectiondialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {

    private BottomLayoutMessagePanel messagePanel;

    private BottomLayoutButtonsPanel buttonsPanel;
    public BottomPanel() {
        setPreferredSize(new Dimension(300, 60));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        messagePanel = new BottomLayoutMessagePanel();
        add(messagePanel);

        buttonsPanel = new BottomLayoutButtonsPanel();
        add(buttonsPanel);
    }

    public BottomLayoutMessagePanel getMessagePanel() {
        return messagePanel;
    }

    public BottomLayoutButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }
}
