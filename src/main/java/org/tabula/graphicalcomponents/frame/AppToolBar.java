package org.tabula.graphicalcomponents.frame;

import org.tabula.instances.ImageIcons;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class AppToolBar extends JPanel {

    private ToolBarButton connectionDialogCallButton;
    private ToolBarButton tablesetDialogButton;
    private ToolBarButton loadDataAndCreateModelDialogButton;
    private ToolBarButton saveModelButton;
    private ToolBarButton openModelButton;
    private ToolBarButton saveImageButton;

    public AppToolBar() {
        init();
        initComponents();
        adding();
    }

    private void init() {
        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        layout.setAlignment(FlowLayout.LEFT);
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        setPreferredSize(new Dimension(getWidth(), 100));
    }

    private void initComponents() {
        var names = ResourceBundle.getBundle("names");
        connectionDialogCallButton = new ToolBarButton();
        connectionDialogCallButton.setToolTipText(names.getString("BUTTON_TOOLTIP_CONNECTIONS"));

        tablesetDialogButton = new ToolBarButton();
        tablesetDialogButton.setToolTipText(names.getString("BUTTON_TOOLTIP_TABLESETS"));

        loadDataAndCreateModelDialogButton = new ToolBarButton();
        loadDataAndCreateModelDialogButton.setToolTipText(names.getString("BUTTON_TOOLTIP_MODEL_CREATION"));

        saveModelButton = new ToolBarButton();
        saveModelButton.setToolTipText(names.getString("BUTTON_TOOLTIP_SAVE_MODEL"));

        openModelButton = new ToolBarButton();
        openModelButton.setToolTipText(names.getString("BUTTON_TOOLTIP_OPEN_MODEL"));

        saveImageButton = new ToolBarButton();
        saveImageButton.setToolTipText(names.getString("BUTTON_TOOLTIP_SAVE_AS_IMAGE"));

        connectionDialogCallButton.setText(names.getString("BUTTON_CONNECTIONS"));
        tablesetDialogButton.setText(names.getString("BUTTON_TABLESETS"));
        loadDataAndCreateModelDialogButton.setText(names.getString("BUTTON_MODEL_CREATION"));
        saveModelButton.setText(names.getString("BUTTON_SAVE_MODEL"));
        openModelButton.setText(names.getString("BUTTON_OPEN_MODEL"));
        saveImageButton.setText(names.getString("BUTTON_SAVE_AS_IMAGE"));
    }

    private void adding() {
        add(connectionDialogCallButton);
        add(tablesetDialogButton);
        add(loadDataAndCreateModelDialogButton);
        add(saveModelButton);
        add(openModelButton);
        add(saveImageButton);
    }

    public ToolBarButton getConnectionDialogCallButton() {
        return connectionDialogCallButton;
    }

    public ToolBarButton getTablesetDialogButton() {
        return tablesetDialogButton;
    }

    public ToolBarButton getLoadDataAndCreateModelDialogButton() {
        return loadDataAndCreateModelDialogButton;
    }

    public ToolBarButton getSaveModelButton() {
        return saveModelButton;
    }

    public ToolBarButton getOpenModelButton() {
        return openModelButton;
    }

    public ToolBarButton getSaveImageButton() {
        return saveImageButton;
    }
}
