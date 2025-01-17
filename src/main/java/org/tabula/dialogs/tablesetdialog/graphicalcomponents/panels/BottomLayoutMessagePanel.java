package org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class BottomLayoutMessagePanel extends JPanel {

    private JLabel message;

    static private final String START_TEXT = "Status: ";

    public BottomLayoutMessagePanel() {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        layout.setAlignment(FlowLayout.LEFT);

        message = new JLabel(START_TEXT);
        add(message);
    }

    public void setMessageText(String text) {
        message.setText(START_TEXT + text);
    }

    public JLabel getMessage() {
        return message;
    }

    public void setTextColor(Color color) {
        message.setForeground(color);
    }
}
