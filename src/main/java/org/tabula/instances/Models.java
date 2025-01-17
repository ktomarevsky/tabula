package org.tabula.instances;

import org.tabula.objects.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Models {

    static private Models instance;
    private List<Model> models;
    private Model currentModel;

    static public Models getInstance() {
        if(instance == null) {
            instance = new Models();
            return instance;
        }
        return instance;
    }

    private Models() {
        models = new ArrayList<Model>();
    }

    public List<Model> getModels() {
        return models;
    }

    public void addModel(Model model) {
        //model.setName(model.getName() + models.size());
        models.add(model);
    }

    public void removeModel(Model model) {
        models.remove(model);
    }

    public Model getCurrentModel() {
        return currentModel;
    }

    public void setCurrentModel(Model currentModel) {
        this.currentModel = currentModel;
    }

    public boolean modelExists(String modelName) {
        return models.stream().anyMatch(value -> modelName.equals(value.getName()));
    }
}
