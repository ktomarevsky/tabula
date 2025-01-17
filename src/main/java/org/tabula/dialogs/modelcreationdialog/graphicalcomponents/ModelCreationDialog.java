package org.tabula.dialogs.modelcreationdialog.graphicalcomponents;

import org.tabula.dialogs.connectiondialog.graphicalcomponents.ConnectionsTableModel;
import org.tabula.dialogs.modelcreationdialog.eventcontroller.Controller;
import org.tabula.dialogs.modelcreationdialog.graphicalcomponents.panels.BottomPanel;
import org.tabula.dialogs.modelcreationdialog.graphicalcomponents.panels.TopPanel;
import org.tabula.dialogs.modelcreationdialog.utils.InputDataVerifier;
import org.tabula.dialogs.tablesetdialog.graphicalcomponents.TablesetTableModel;
import org.tabula.instances.CredentialsInstance;
import org.tabula.instances.Tablesets;
import org.tabula.objects.Credentials;
import org.tabula.objects.Tableset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModelCreationDialog extends JDialog {

    private TopPanel topPanel;
    private JTable connectionsTable;
    private JPanel centerPanel;
    private JTable tablesetsTable;
    private BottomPanel bottomPanel;

    static private final int MINIMAL_WIDTH = 600;
    static private final int MINIMAL_HEIGHT = 400;
    static private final int MAXIMAL_WIDTH = 800;
    static private final int MAXIMAL_HEIGHT = 600;

    public ModelCreationDialog(JFrame frame) {
        super(frame, "Model creation", ModalityType.DOCUMENT_MODAL);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setMinimumSize(new Dimension(MINIMAL_WIDTH, MINIMAL_HEIGHT));
        setMaximumSize(new Dimension(MAXIMAL_WIDTH, MAXIMAL_HEIGHT));
        setResizable(false);


        int x = frame.getLocation().x + frame.getSize().width/2 - getWidth()/2;
        int y = frame.getLocation().y + frame.getSize().height/2 - getHeight()/2;
        setLocation(x, y);

        setLayout(new BorderLayout());

        initComponents();

        InputDataVerifier verifier = new InputDataVerifier(this);
        Controller controller = new Controller(this);

        setVisible(true);
    }

    private void initComponents() {
        topPanel = new TopPanel();
        add(topPanel, BorderLayout.PAGE_START);

        List<Credentials> credentials = CredentialsInstance.getInstance().getCredentialsList();
        ConnectionsTableModel model = new ConnectionsTableModel(credentials);
        connectionsTable = new JTable(model);
        connectionsTable.getTableHeader().setResizingAllowed(true);
        connectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane pane = new JScrollPane(connectionsTable);
        pane.setBorder(BorderFactory.createEmptyBorder(3, 5, 2, 5));
        connectionsTable.setFillsViewportHeight(true);
        pane.setPreferredSize(new Dimension(MINIMAL_WIDTH/2, 300));
        add(pane, BorderLayout.LINE_START);

        centerPanel = new JPanel();
        add(pane, BorderLayout.CENTER);

        List<Tableset> tablesets = Tablesets.getInstance().getTablesets();
        TablesetTableModel tablesetModel = new TablesetTableModel(tablesets);
        tablesetsTable = new JTable(tablesetModel);
        tablesetsTable.getTableHeader().setResizingAllowed(true);
        tablesetsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablesetsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tablesetsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(3, 0, 2, 5));
        scrollPane.setPreferredSize(new Dimension(MINIMAL_WIDTH/2, 300));
        add(scrollPane, BorderLayout.LINE_END);

        bottomPanel = new BottomPanel();
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    public JTextField getModelNameField() {
        return topPanel.getModelNameField();
    }

    public void setMessageText(String text) {
        bottomPanel.getMessagePanel().setMessageText(text);
    }

    public void setMessageColor(Color color) {
        bottomPanel.getMessagePanel().setTextColor(color);
    }

    public JButton getCreateModelButton() {
        return bottomPanel.getButtonPanel().getCreateModelButton();
    }

    public JTable getConnectionsTable() {
        return connectionsTable;
    }

    public JTable getTablesetsTable() {
        return tablesetsTable;
    }
}
