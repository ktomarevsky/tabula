package org.tabula.eventcontroller.frame;

import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionDialog;
import org.tabula.dialogs.modelcreationdialog.graphicalcomponents.ModelCreationDialog;
import org.tabula.dialogs.tablesetdialog.graphicalcomponents.TableSetDialog;
import org.tabula.graphicalcomponents.frame.AppFrame;
import org.tabula.graphicalcomponents.frame.LayoutTabbedPane;
import org.tabula.graphicalcomponents.frame.ToolBarButton;
import org.tabula.graphicalcomponents.layout.ModelScrollPane;
import org.tabula.graphicalcomponents.tree.ModelNode;
import org.tabula.graphicalcomponents.tree.ModelTree;
import org.tabula.instances.ApplicationInstance;
import org.tabula.instances.Models;
import org.tabula.objects.model.Model;
import org.tabula.utils.fileutils.FileReaderWriter;
import org.tabula.utils.imageutils.ImageHandler;
import org.tabula.utils.xmlutils.XMLHandler;
import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ButtonController {
    
    private ToolBarButton loadModelButton;
    private ToolBarButton connectionDialogCallButton;
    private ToolBarButton createProjectButton;
    private ToolBarButton saveModelButton;
    private ToolBarButton openModelButton;
    private ToolBarButton saveImageButton;
    private LayoutTabbedPane layoutTabbedPane;

    private JFileChooser fileChooser;
    
    public ButtonController(final AppFrame frame) {
        loadModelButton = frame.getToolBar().getTablesetDialogButton();
        connectionDialogCallButton = frame.getToolBar().getConnectionDialogCallButton();
        createProjectButton = frame.getToolBar().getLoadDataAndCreateModelDialogButton();
        saveModelButton = frame.getToolBar().getSaveModelButton();
        openModelButton = frame.getToolBar().getOpenModelButton();
        saveImageButton = frame.getToolBar().getSaveImageButton();
        layoutTabbedPane = frame.getTabbedPane();

        fileChooser = new JFileChooser();
        
        loadModelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                 //ModelEngine.createModel(frame);
                try {
                    TableSetDialog dialog = new TableSetDialog(frame);
                } catch (RuntimeException runtimeException) {
                    var names = ResourceBundle.getBundle("names");
                    var message = String.format(names.getString("FILE_CORRUPTED"), runtimeException);
                    JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        connectionDialogCallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    ConnectionDialog dialog = new ConnectionDialog(frame);
                } catch (RuntimeException runtimeException) {
                    var names = ResourceBundle.getBundle("names");
                    var message = String.format(names.getString("FILE_CORRUPTED"), runtimeException);
                    JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        createProjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                try {
                    ModelCreationDialog dialog = new ModelCreationDialog(frame);
                } catch (RuntimeException runtimeException) {
                    var names = ResourceBundle.getBundle("names");
                    var message = String.format(names.getString("FILE_CORRUPTED"), runtimeException);
                    JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saveModelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model model = Models.getInstance().getCurrentModel();
                if(model != null) {
                    saveModelAsXML(model);
                } else {
                    var names = ResourceBundle.getBundle("names");
                    JOptionPane.showMessageDialog(frame, names.getString("NO_MODEL_FOR_SAVING"), names.getString("DIALOG_TITLE_INFO"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        openModelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    try {
                        Document document = FileReaderWriter.readXMLFromFile(file);

                        String modelName = document.getElementsByTagName("modelName").item(0).getTextContent();

                        if(Models.getInstance().modelExists(modelName)) {
                            var names = ResourceBundle.getBundle("names");
                            var message = String.format(names.getString("MODEL_ALREADY_OPENED"), modelName);
                            JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_INFO"), JOptionPane.INFORMATION_MESSAGE);

                        } else {
                            Model model = new Model();
                            model.setName(modelName);
                            XMLHandler.loadDataFromXML(document, model);

                            AppFrame frame = ApplicationInstance.getInstance().getAppFrame();

                            ModelTree tree = frame.getTree();
                            ModelNode modelNode = new ModelNode(model, tree);
                            tree.getRoot().add(modelNode);
                            modelNode.createModelTree();

                            model.restorePositions(document);

                            Models.getInstance().addModel(model);
                            Models.getInstance().setCurrentModel(model);
                            model.setSaved(true);
                            frame.getTabbedPane().addTab(model.getModelScrollPane());
                            frame.getTabbedPane().setSelectedComponent(model.getModelScrollPane());
                            tree.getRoot().add(modelNode);
                            tree.updateUI();
                            tree.expandRow(0);
                        }

                    } catch (RuntimeException runtimeException) {
                        var names = ResourceBundle.getBundle("names");
                        var message = String.format(names.getString("SELECTED_FILE_WRONG"), runtimeException.getMessage());
                        JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        saveImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model model = Models.getInstance().getCurrentModel();
                if(model != null) {
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    String fileName = model.getName() + ".png";
                    int result = fileChooser.showSaveDialog(frame);
                    File file = fileChooser.getSelectedFile();
                    if (file != null) {
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File outputFile = new File(file.getAbsolutePath() + "\\" + fileName);
                            if (!outputFile.exists()) {
                                BufferedImage image = ImageHandler.createPNGImage(model);
                                try {
                                    FileReaderWriter.writeImage(image, outputFile);
                                } catch (Exception exception) {
                                    var names = ResourceBundle.getBundle("names");
                                    var message = String.format(names.getString("UNKNOWN_ERROR"), exception.getMessage());
                                    JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                var names = ResourceBundle.getBundle("names");
                                var message = String.format(names.getString("FILE_EXISTS_OVERWRITE"), outputFile.getAbsolutePath());
                                int flag = JOptionPane.showConfirmDialog(frame, message, names.getString("DIALOG_TITLE_QUESTION"), JOptionPane.YES_NO_OPTION);
                                if (flag == JOptionPane.OK_OPTION) {
                                    BufferedImage image = ImageHandler.createPNGImage(model);
                                    try {
                                        FileReaderWriter.writeImage(image, outputFile);
                                    } catch (Exception exception) {
                                        message = String.format(names.getString("UNKNOWN_ERROR"), exception.getMessage());
                                        JOptionPane.showMessageDialog(frame, message, names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }
                    } else {
                        var names = ResourceBundle.getBundle("names");
                        JOptionPane.showMessageDialog(frame, names.getString("WRONG_DIR"), names.getString("DIALOG_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    var names = ResourceBundle.getBundle("names");
                    JOptionPane.showMessageDialog(frame, names.getString("NO_MODEL_FOR_SAVING"), names.getString("DIALOG_TITLE_INFO"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                List<Model> models = Models.getInstance().getModels();
                List<Model> notSavedModels = models.stream().filter(model -> !model.isSaved()).collect(Collectors.toList());
                if(!notSavedModels.isEmpty()) {
                    var names = ResourceBundle.getBundle("names");
                    int status = JOptionPane.showConfirmDialog(frame, names.getString("NOT_SAVED_MODELS_EXIST"), names.getString("DIALOG_TITLE_QUESTION"), JOptionPane.YES_NO_CANCEL_OPTION);
                    if(status == JOptionPane.YES_OPTION) {
                        notSavedModels.forEach(model -> saveModelAsXML(model));
                        frame.dispose();
                    }
                    if(status == JOptionPane.NO_OPTION) {
                        frame.dispose();
                    }
                } else {
                    frame.dispose();
                }
            }
        });

        layoutTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                ModelScrollPane modelScrollPane = (ModelScrollPane) layoutTabbedPane.getSelectedComponent();
                if(modelScrollPane != null) {
                    Models.getInstance().setCurrentModel(modelScrollPane.getModel());
                }
            }
        });
    }

    private void saveModelAsXML(Model model) {
        AppFrame frame = ApplicationInstance.getInstance().getAppFrame();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String fileName = model.getName() + ".mdl";
        int confirmed = 1;
        while(confirmed == 1) {
            fileChooser.setSelectedFile(new File(fileName));
            int result = fileChooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.exists()) {

                    Document document = XMLHandler.createXMLFromModel(model);
                    FileReaderWriter.writeXMLToFile(document, file.getAbsolutePath());
                    model.setSaved(true);
                    confirmed = 0;
                } else {
                    var names = ResourceBundle.getBundle("names");
                    var message = String.format(names.getString("FILE_EXISTS_OVERWRITE"), fileName);
                    confirmed = JOptionPane.showConfirmDialog(frame, message, names.getString("DIALOG_TITLE_QUESTION"), JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.OK_OPTION) {
                        Document document = XMLHandler.createXMLFromModel(model);
                        FileReaderWriter.writeXMLToFile(document, file.getAbsolutePath());
                        model.setSaved(true);
                    }
                }
            } else {
                confirmed = 0;
            }
        }
    }
}
