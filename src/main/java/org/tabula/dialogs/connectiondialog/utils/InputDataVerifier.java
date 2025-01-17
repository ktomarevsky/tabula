package org.tabula.dialogs.connectiondialog.utils;

import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionDialog;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class InputDataVerifier {

    static private final String ERROR_CONNECTIONNAME = "Characters [A-Za-z0-9_] are available. Maximal length = 30.";
    static private final String ERROR_LOGIN = "Maximal length = 30";
    static private final String ERROR_PASSWORD = "Maximal length = 30";
    static private final String ERROR_HOST = "Characters [A-Za-z0-9.-] are available.";
    static private final String ERROR_PORT = "Only digits available. Maximal length = 5.";
    static private final String ERROR_SID = "Characters [A-Za-z0-9] are available. Maximal length = 8.";
    static private final String ERROR_INPUT = "Wrong input data.";

    private static Pattern connectionNamePattern;
    private static Pattern loginPattern;
    private static Pattern passwordPattern;
    private static Pattern hostPattern;
    private static Pattern portPattern;
    private static Pattern sidPattern;

    public InputDataVerifier(ConnectionDialog dialog) {
        JTextField connectionName = dialog.getConnectionName();
        JTextField username = dialog.getUsername();
        JPasswordField password = dialog.getPassword();
        JTextField host = dialog.getHost();
        JTextField port = dialog.getPort();
        JTextField SID = dialog.getSID();

        connectionNamePattern = Pattern.compile("[A-Za-z0-9_]{1,30}");
        loginPattern = Pattern.compile(".{1,30}");
        passwordPattern = Pattern.compile(".{0,30}");
        hostPattern = Pattern.compile("[A-Za-z0-9.-]{1,253}");
        portPattern = Pattern.compile("[0-9]{1,5}");
        sidPattern = Pattern.compile("[A-Za-z0-9]{1,8}");

        connectionName.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = connectionNamePattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_CONNECTIONNAME, connectionName);
                } else {
                    removeErrorStatus(dialog, connectionName);
                }
                return flag;
            }
        });

        username.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = loginPattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_LOGIN, username);
                } else {
                    removeErrorStatus(dialog, username);
                }
                return flag;
            }
        });

        password.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = passwordPattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_PASSWORD, password);
                } else {
                    removeErrorStatus(dialog, password);
                }
                return flag;
            }
        });

        host.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = hostPattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_HOST, host);
                } else {
                    removeErrorStatus(dialog, host);
                }
                return flag;
            }
        });

        port.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = portPattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_PORT, port);
                } else {
                    removeErrorStatus(dialog, port);
                }
                return flag;
            }
        });

        SID.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = sidPattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_SID, SID);
                } else {
                    removeErrorStatus(dialog, SID);
                }
                return flag;
            }
        });
    }

    static public boolean verifyAllFields(ConnectionDialog dialog) {
        boolean result = false;
        boolean flag1 = connectionNamePattern.matcher(dialog.getConnectionName().getText()).matches();
        boolean flag2 = loginPattern.matcher(dialog.getUsername().getText()).matches();
        boolean flag3 = passwordPattern.matcher(dialog.getPassword().getText()).matches();
        boolean flag4 = hostPattern.matcher(dialog.getHost().getText()).matches();
        boolean flag5 = portPattern.matcher(dialog.getPort().getText()).matches();
        boolean flag6 = sidPattern.matcher(dialog.getSID().getText()).matches();
        boolean flag7 = dialog.getDbType().getSelectedItem() != null;

        result = flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7;

        if(!result) {
            dialog.setMessageText(ERROR_INPUT);
            dialog.setMessageColor(Color.RED);
        } else {
            dialog.setMessageText("");
            dialog.setMessageColor(Color.BLACK);
        }
        return result;
    }

    private void setErrorStatus(ConnectionDialog dialog, String messageText, JComponent component) {
        dialog.setMessageText(messageText);
        dialog.setMessageColor(Color.RED);
        component.setBackground(Color.RED);
    }

    private void removeErrorStatus(ConnectionDialog dialog, JComponent component) {
        dialog.setMessageText("");
        dialog.setMessageColor(Color.BLACK);
        component.setBackground(Color.WHITE);
    }
}
