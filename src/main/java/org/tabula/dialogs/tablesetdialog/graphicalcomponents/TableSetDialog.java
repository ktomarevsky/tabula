package org.tabula.dialogs.tablesetdialog.graphicalcomponents;

import org.tabula.dialogs.tablesetdialog.eventcontroller.Controller;
import org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels.BottomPanel;
import org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels.CenterPanel;
import org.tabula.dialogs.tablesetdialog.graphicalcomponents.panels.TopPanel;
import org.tabula.dialogs.tablesetdialog.utils.InputDataVerifier;
import org.tabula.instances.Tablesets;
import org.tabula.objects.Tableset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TableSetDialog extends JDialog {

    private TopPanel topPanel;
    private JScrollPane scrollPane;
    private JTable table;
    private CenterPanel centerPanel;
    private BottomPanel bottomPanel;

    static private final int MINIMAL_WIDTH = 600;

    static private final int MINIMAL_HEIGHT = 400;
    static private final int MAXIMAL_WIDTH = 800;
    static private final int MAXIMAL_HEIGHT = 600;

    public TableSetDialog(JFrame frame) {
        super(frame, "Table sets", ModalityType.DOCUMENT_MODAL);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        setMinimumSize(new Dimension(MINIMAL_WIDTH, MINIMAL_HEIGHT));
        setMaximumSize(new Dimension(MAXIMAL_WIDTH, MAXIMAL_HEIGHT));

        int x = frame.getLocation().x + frame.getSize().width/2 - getWidth()/2;
        int y = frame.getLocation().y + frame.getSize().height/2 - getHeight()/2;
        setLocation(x, y);

        setLayout(new BorderLayout());

        initComponents();

        Controller controller = new Controller(this);
        InputDataVerifier verifier = new InputDataVerifier(this);

        setVisible(true);
    }

    private void initComponents() {
        topPanel = new TopPanel();
        add(topPanel, BorderLayout.PAGE_START);

        List<Tableset> tablesets = Tablesets.getInstance().getTablesets();
        TablesetTableModel model = new TablesetTableModel(tablesets);
        table = new JTable(model);
        table.getTableHeader().setResizingAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(MINIMAL_WIDTH/2, 300));
        add(scrollPane, BorderLayout.LINE_START);

        centerPanel = new CenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        bottomPanel = new BottomPanel();
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getTablesetName() {
        return centerPanel.getTopCenterPanel().getTablesetName();
    }

    public JTextArea getTextArea() {
        return centerPanel.getTextArea();
    }

    public void setMessageText(String text) {
        bottomPanel.getMessagePanel().setMessageText(text);
    }

    public void setMessageColor(Color color) {
        bottomPanel.getMessagePanel().setTextColor(color);
    }

    public JButton getSaveButton() {
        return bottomPanel.getButtonPanel().getSaveButton();
    }

    public JButton getRemoveButton() {
        return bottomPanel.getButtonPanel().getRemoveButton();
    }

    public JButton getClearButton() {
        return bottomPanel.getButtonPanel().getClearButton();
    }

    public JButton getCloseButton() {
        return bottomPanel.getButtonPanel().getCloseButton();
    }
}
