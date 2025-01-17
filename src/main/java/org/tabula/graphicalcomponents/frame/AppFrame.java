package org.tabula.graphicalcomponents.frame;

import org.tabula.eventcontroller.frame.ButtonController;
import org.tabula.graphicalcomponents.tree.ModelTree;
import org.tabula.instances.ImageIcons;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    AppToolBar toolBar;
    JSplitPane splitPane;
    ModelTree tree;
    LayoutTabbedPane tabbedPane;

    ButtonController buttonController;

    public AppFrame() {
        super("Tabula");
        initFrame();
        initComponents();
        adding();

        buttonController = new ButtonController(this);

        setVisible(true);
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1280, 920);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width/2 - getSize().width/2, screenSize.height/2 - getSize().height/2);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        toolBar = new AppToolBar();
        tabbedPane = new LayoutTabbedPane();

        tree = new ModelTree();
        JScrollPane scrollPane = new JScrollPane(tree);
        tree.setAutoscrolls(true);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, tabbedPane);
        splitPane.setDividerLocation(300);
        //splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);

        setIconImage(ImageIcons.mainIcon);
    }

    private void adding() {
        add(toolBar, BorderLayout.PAGE_START);
        add(splitPane, BorderLayout.CENTER);
    }

    public LayoutTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public AppToolBar getToolBar() {
        return toolBar;
    }

    public ModelTree getTree() {
        return tree;
    }
}
