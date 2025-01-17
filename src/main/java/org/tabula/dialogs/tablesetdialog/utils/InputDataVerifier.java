package org.tabula.dialogs.tablesetdialog.utils;

import org.tabula.dialogs.tablesetdialog.graphicalcomponents.TableSetDialog;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class InputDataVerifier {

    static private final String ERROR_TABLESETNAME = "Characters [A-Za-z0-9_] are available. Maximal length = 30.";
    static private final String ERROR_TABLES = "Characters [A-Za-z0-9_ ] are available. Maximal table length = 30.";
    static private final String ERROR_INPUT = "Wrong input data.";

    static private final Pattern tablesetnamePattern = Pattern.compile("[A-Za-z0-9_]{1,30}");
    static private final Pattern tablesPattern = Pattern.compile("[A-Za-z0-9_ ]{1,}");
    static private final Pattern tablePattern = Pattern.compile("[A-Za-z0-9_]{1,30}");

    public InputDataVerifier(TableSetDialog dialog) {

        JTextField tablesetName = dialog.getTablesetName();
        JTextArea textArea = dialog.getTextArea();

        tablesetName.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = tablesetnamePattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_TABLESETNAME, tablesetName);
                } else {
                    removeErrorStatus(dialog, tablesetName);
                }
                return flag;
            }
        });


        textArea.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextArea field = (JTextArea) input;
                String text = field.getText().replace("\n", "");
                boolean flag1 = tablesPattern.matcher(text).matches();
                boolean flag2 = verifyTables(text);
                if(!flag1 || !flag2) {
                    setErrorStatus(dialog, ERROR_TABLES, textArea);
                } else {
                    removeErrorStatus(dialog, textArea);
                }
                return flag1 && flag2;
            }
        });
    }

    static public boolean verifyAllFields(TableSetDialog dialog) {
        boolean result = false;
        String text = dialog.getTextArea().getText().replace("\n", "");
        boolean flag1 = tablesetnamePattern.matcher(dialog.getTablesetName().getText()).matches();
        boolean flag2 = tablesPattern.matcher(text).matches();
        boolean flag3 = verifyTables(text);

        result = flag1 && flag2 && flag3;

        if(!result) {
            dialog.setMessageText(ERROR_INPUT);
            dialog.setMessageColor(Color.RED);
        } else {
            dialog.setMessageText("");
            dialog.setMessageColor(Color.BLACK);
        }
        return result;
    }

    private void setErrorStatus(TableSetDialog dialog, String messageText, JComponent component) {
        dialog.setMessageText(messageText);
        dialog.setMessageColor(Color.RED);
        component.setBackground(Color.RED);
    }

    private void removeErrorStatus(TableSetDialog dialog, JComponent component) {
        dialog.setMessageText("");
        dialog.setMessageColor(Color.BLACK);
        component.setBackground(Color.WHITE);
    }

    static private boolean verifyTables(String text) {
        boolean result = true;
        String tables = text;
        String[] tablesList = tables.split(" ");
        for(String table : tablesList) {
            if(!"".equals(table)) {
                result = tablePattern.matcher(table).matches();
                //System.out.println("Table: " + table);
                if (!result) {
                    break;
                }
            }
        }

        return result;
    }
}
