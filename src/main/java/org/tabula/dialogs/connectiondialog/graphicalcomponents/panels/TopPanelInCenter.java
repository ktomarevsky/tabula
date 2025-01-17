package org.tabula.dialogs.connectiondialog.graphicalcomponents.panels;

import org.tabula.utils.dbutils.DBType;

import javax.swing.*;
import java.awt.*;

public class TopPanelInCenter extends JPanel {

    private JLabel hostnameLabel;
    private JTextField hostname;
    private JLabel portLabel;
    private JTextField port;
    private JLabel SIDLabel;
    private JTextField SID;
    private JComboBox<DBType> dbType;
    private JLabel dbTypeLabel;

    public TopPanelInCenter() {
        setPreferredSize(new Dimension(100, 130));
        setLayout(new GridBagLayout());

        initComponents();
    }

    private void initComponents() {
        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.insets = new Insets(6, 6,6,6);

        hostnameLabel = new JLabel("Hostname");
        constr.weightx = 0.1;
        constr.gridx = 0;
        constr.gridy = 0;
        add(hostnameLabel, constr);

        hostname = new JTextField();
        //hostname.setInputVerifier();
        constr.weightx = 0.8;
        constr.gridx = 1;
        constr.gridy = 0;
        add(hostname, constr);

        portLabel = new JLabel("Port");
        constr.weightx = 0.1;
        constr.gridx = 0;
        constr.gridy = 1;
        add(portLabel, constr);

        port = new JTextField();
        constr.weightx = 0.8;
        constr.gridx = 1;
        constr.gridy = 1;
        add(port, constr);


        SIDLabel = new JLabel("SID");
        constr.weightx = 0.1;
        constr.gridx = 0;
        constr.gridy = 2;
        add(SIDLabel, constr);

        SID = new JTextField();
        constr.weightx = 0.8;
        constr.gridx = 1;
        constr.gridy = 2;
        add(SID, constr);

        dbTypeLabel = new JLabel("DB type");
        constr.weightx = 0.1;
        constr.gridx = 0;
        constr.gridy = 3;
        add(dbTypeLabel, constr);

        dbType = new JComboBox<>(DBType.values());
        dbType.setSelectedItem(null);
//        dbType.setSelectedIndex(0);
        constr.weightx = 0.8;
        constr.gridx = 1;
        constr.gridy = 3;
        add(dbType, constr);
    }

    public JTextField getHostname() {
        return hostname;
    }

    public JTextField getPort() {
        return port;
    }

    public JTextField getSID() {
        return SID;
    }

    public JComboBox<DBType> getDbType() {
        return dbType;
    }
}
