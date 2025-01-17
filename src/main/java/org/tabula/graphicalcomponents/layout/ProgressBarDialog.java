package org.tabula.graphicalcomponents.layout;

import javax.swing.*;
import java.awt.*;

public class ProgressBarDialog extends JDialog {

    private JProgressBar progressBar;

    public ProgressBarDialog(JFrame frame) {
        super(frame, "Progress...", ModalityType.MODELESS);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(300, 50);
        setLocationRelativeTo(frame);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(50);
        progressBar.setIndeterminate(true);

        add(BorderLayout.CENTER, progressBar);

        setUndecorated(true);
        setVisible(true);

    }
}
