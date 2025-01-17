package org.tabula.dialogs.connectiondialog.graphicalcomponents.panels;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private JLabel connectionNameLabel;
    private JTextField connectionName;
    private JLabel usernameLabel;
    private JTextField username;
    private JLabel passwordLabel;
    private JPasswordField password;

    public TopPanel() {
        setPreferredSize(new Dimension(100, 100));
        setLayout(new GridBagLayout());

        initComponents();
    }

    private void initComponents() {
        GridBagConstraints constr = new GridBagConstraints();
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.insets = new Insets(6, 6,6,6);

        connectionNameLabel = new JLabel("Connection name");
        constr.weightx = 0.1;
        constr.gridx = 0;
        constr.gridy = 0;
        add(connectionNameLabel, constr);

        connectionName = new JTextField();
        constr.weightx = 0.8;
        constr.gridx = 1;
        constr.gridy = 0;
        add(connectionName, constr);

        usernameLabel = new JLabel("Username");
        constr.weightx = 0.1;
        constr.gridx = 0;
        constr.gridy = 1;
        add(usernameLabel, constr);

        username = new JTextField();
        constr.weightx = 0.8;
        constr.gridx = 1;
        constr.gridy = 1;
        add(username, constr);


        passwordLabel = new JLabel("Password");
        constr.weightx = 0.1;
        constr.gridx = 0;
        constr.gridy = 2;
        add(passwordLabel, constr);

        password = new JPasswordField();
        constr.weightx = 0.8;
        constr.gridx = 1;
        constr.gridy = 2;
        add(password, constr);
    }

    public JTextField getConnectionName() {
        return connectionName;
    }

    public JTextField getUsername() {
        return username;
    }

    public JPasswordField getPassword() {
        return password;
    }
}
