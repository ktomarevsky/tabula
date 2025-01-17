package org.tabula.dialogs.modelcreationdialog.utils;

import org.tabula.dialogs.modelcreationdialog.graphicalcomponents.ModelCreationDialog;
import org.tabula.instances.Models;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class InputDataVerifier {

    static private final String ERROR_MODELNAME = "Characters [A-Za-z0-9_] are available. Maximal length = 60.";
    static private final String ERROR_MODELNAME_EXISTS = "Model with this name already exists!";

    static private Pattern modelNamePattern = Pattern.compile("[A-Za-z0-9_]{1,60}");;

    public InputDataVerifier(ModelCreationDialog dialog) {
        JTextField modelNameField = dialog.getModelNameField();

        //modelNamePattern = Pattern.compile("[A-Za-z0-9_]{1,60}");

        /*modelNameField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                boolean flag = modelNamePattern.matcher(field.getText()).matches();
                if(!flag) {
                    setErrorStatus(dialog, ERROR_MODELNAME, modelNameField);
                } else {
                    removeErrorStatus(dialog, modelNameField);
                }
                return flag;
            }
        });*/
    }

    static public boolean verifyModelName(ModelCreationDialog dialog) {
        boolean result = false;
        String modelName = dialog.getModelNameField().getText();
        boolean flag1 = modelNamePattern.matcher(modelName).matches();
        if(!flag1) {
            dialog.setMessageText(ERROR_MODELNAME);
            dialog.setMessageColor(Color.RED);
            dialog.getModelNameField().setBackground(Color.RED);
            result = false;
        } else if(Models.getInstance().modelExists(modelName)) {
            dialog.setMessageText(ERROR_MODELNAME_EXISTS);
            dialog.setMessageColor(Color.RED);
            dialog.getModelNameField().setBackground(Color.RED);
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    private void setErrorStatus(ModelCreationDialog dialog, String messageText, JComponent component) {
        dialog.setMessageText(messageText);
        dialog.setMessageColor(Color.RED);
        component.setBackground(Color.RED);
    }

    private void removeErrorStatus(ModelCreationDialog dialog, JComponent component) {
        dialog.setMessageText("");
        dialog.setMessageColor(Color.BLACK);
        component.setBackground(Color.WHITE);
    }
}
