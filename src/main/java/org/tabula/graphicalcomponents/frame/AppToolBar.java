package org.tabula.graphicalcomponents.frame;

import org.tabula.instances.ImageIcons;

import javax.swing.*;
import java.awt.*;

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
        connectionDialogCallButton = new ToolBarButton();
        connectionDialogCallButton.setToolTipText("Manage connections");

        tablesetDialogButton = new ToolBarButton();
        tablesetDialogButton.setToolTipText("Manage tablesets");

        loadDataAndCreateModelDialogButton = new ToolBarButton();
        loadDataAndCreateModelDialogButton.setToolTipText("Load data and create model");

        saveModelButton = new ToolBarButton();
        saveModelButton.setToolTipText("Save model");

        openModelButton = new ToolBarButton();
        openModelButton.setToolTipText("Open model");

        saveImageButton = new ToolBarButton();
        saveImageButton.setToolTipText("Save model as image");

//        connectionDialogCallButton.setText("Н");
        connectionDialogCallButton.setIcon(ImageIcons.databases);
        tablesetDialogButton.setText("M");
        loadDataAndCreateModelDialogButton.setText("P");
        saveModelButton.setText("S");
        openModelButton.setText("O");
        saveImageButton.setText("I");
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
