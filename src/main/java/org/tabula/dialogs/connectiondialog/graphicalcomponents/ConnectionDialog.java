package org.tabula.dialogs.connectiondialog.graphicalcomponents;

import org.tabula.dialogs.connectiondialog.eventcontroller.Controller;
import org.tabula.dialogs.connectiondialog.graphicalcomponents.panels.BottomPanel;
import org.tabula.dialogs.connectiondialog.graphicalcomponents.panels.RightPanel;
import org.tabula.dialogs.connectiondialog.utils.InputDataVerifier;
import org.tabula.instances.CredentialsInstance;
import org.tabula.objects.Credentials;
import org.tabula.utils.dbutils.DBType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConnectionDialog extends JDialog {

    private JPanel commonPanel;
    private RightPanel rightPanel;
    private BottomPanel bottomPanel;

    JScrollPane pane;
    private JTable table;
    private List<Credentials> credentials;

    static private final int MINIMAL_WIDTH = 600;

    static private final int MINIMAL_HEIGHT = 400;
    static private final int MAXIMAL_WIDTH = 800;
    static private final int MAXIMAL_HEIGHT = 600;
    public ConnectionDialog(JFrame frame) {
        super(frame, "Connection", ModalityType.DOCUMENT_MODAL);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setMinimumSize(new Dimension(MINIMAL_WIDTH, MINIMAL_HEIGHT));
        setMaximumSize(new Dimension(MAXIMAL_WIDTH, MAXIMAL_HEIGHT));

        int x = frame.getLocation().x + frame.getSize().width/2 - getWidth()/2;
        int y = frame.getLocation().y + frame.getSize().height/2 - getHeight()/2;
        setLocation(x, y);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        commonPanel = new JPanel(new BorderLayout());
        commonPanel.setDoubleBuffered(true);
        setContentPane(commonPanel);

        rightPanel = new RightPanel();
        commonPanel.add(rightPanel);

        credentials = CredentialsInstance.getInstance().getCredentialsList();
        ConnectionsTableModel model = new ConnectionsTableModel(credentials);
        table = new JTable(model);
        table.getTableHeader().setResizingAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        pane.setPreferredSize(new Dimension(MINIMAL_WIDTH/2, 300));
        commonPanel.add(pane, BorderLayout.LINE_START);

        bottomPanel = new BottomPanel();
        commonPanel.add(bottomPanel, BorderLayout.PAGE_END);

        InputDataVerifier verifier = new InputDataVerifier(this);
        Controller controller = new Controller(this);
    }

    public JTextField getConnectionName() {
        return rightPanel.getTopPanel().getConnectionName();
    }

    public JTextField getUsername() {
        return rightPanel.getTopPanel().getUsername();
    }

    public JPasswordField getPassword() {
        return rightPanel.getTopPanel().getPassword();
    }

    public JTextField getHost() {
        return rightPanel.getCenterPanel().getTopPanelInCenterPanel().getHostname();
    }

    public JTextField getPort() {
        return rightPanel.getCenterPanel().getTopPanelInCenterPanel().getPort();
    }

    public JTextField getSID() {
        return rightPanel.getCenterPanel().getTopPanelInCenterPanel().getSID();
    }
    public JComboBox<DBType> getDbType() {
        return rightPanel.getCenterPanel().getTopPanelInCenterPanel().getDbType();
    }

    public void setMessageText(String text) {
        bottomPanel.getMessagePanel().setMessageText(text);
    }

    public void setMessageColor(Color color) {
        bottomPanel.getMessagePanel().setTextColor(color);
    }

    public JButton getSaveButton() {
        return bottomPanel.getButtonsPanel().getSaveButton();
    }

    public JButton getDeleteButton() {
        return bottomPanel.getButtonsPanel().getDeleteButton();
    }

    public JButton getClearButton() {
        return bottomPanel.getButtonsPanel().getClearButton();
    }

    public JButton getTestButton() {
        return bottomPanel.getButtonsPanel().getTestButton();
    }

    public JButton getCancelButton() {
        return bottomPanel.getButtonsPanel().getCancelButton();
    }

    public JScrollPane getPane() {
        return  pane;
    }

    public JTable getTable() {
        return table;
    }

    public List<Credentials> getConnections() {
        return credentials;
    }
}
