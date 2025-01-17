package org.tabula.graphicalcomponents.layout;

import org.tabula.objects.model.Model;

import javax.swing.*;

public class ModelScrollPane extends JScrollPane {

    private Model model;

    public ModelScrollPane(Model model) {
        setAutoscrolls(true);
        getHorizontalScrollBar().setUnitIncrement(50);
        getVerticalScrollBar().setUnitIncrement(50);
        this.model = model;
        setViewportView(model);
    }

    public Model getModel() {
        return model;
    }
}
