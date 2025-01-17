package org.tabula.graphicalcomponents.frame;

import org.tabula.graphicalcomponents.layout.ModelScrollPane;
import org.tabula.instances.ImageIcons;
import org.tabula.objects.model.Model;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LayoutTabbedPane extends JTabbedPane {

    private Map<Model, ModelScrollPane> tabs;

    public LayoutTabbedPane() {
        tabs = new HashMap<>();
    }

    public void addTab(ModelScrollPane modelScrollPane) {
        String name = modelScrollPane.getModel().getName();
        addTab(name, ImageIcons.tabIcon, modelScrollPane);
        tabs.put(modelScrollPane.getModel(), modelScrollPane);
    }

    public void removeTab(ModelScrollPane modelScrollPane) {
        remove(modelScrollPane);
        tabs.remove(modelScrollPane.getModel());
    }

    public ModelScrollPane getTabByModel(Model model) {
        return tabs.get(model);
    }
}
