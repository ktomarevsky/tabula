package org.tabula.instances;

import org.tabula.graphicalcomponents.frame.AppFrame;

public class ApplicationInstance {

    static private ApplicationInstance instance;
    private AppFrame frame;

    static public ApplicationInstance getInstance() {
        if(instance == null) {
            instance = new ApplicationInstance();
        }
        return instance;
    }

    private ApplicationInstance() {
        frame = new AppFrame();
    }

    public AppFrame getAppFrame() {
        return frame;
    }
}
