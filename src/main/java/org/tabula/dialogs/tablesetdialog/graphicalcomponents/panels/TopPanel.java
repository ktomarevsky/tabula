package org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    public TopPanel() {
        setPreferredSize(new Dimension(300, 60));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
