package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.view.ViewContext;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serial;
import java.util.List;

// CHECKSTYLE: MagicNumber OFF

/**
 * Requests view of the Admin.
 */
public final class AdminRequestsView extends AdminPanel {

    @Serial
    private static final long serialVersionUID = -8901234567890123456L;
    private static final String ERROR = "Errore";
    private static final String COMPLETED = "Operazione completata";
    private final JTable requestsTable;

    /**
     * Constructor of the Admin Requests View.
     *
     * @param currentSessionContext The context of the current admin.
     */
    public AdminRequestsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle richieste degli utenti.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        this.requestsTable = createRequestsTable(currentSessionContext);
        final JScrollPane scrollPane = new JScrollPane(requestsTable);
        this.add(scrollPane, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JButton addSeriesButton = new JButton("Completa Richiesta");
        addSeriesButton.addActionListener(e -> completeRequestDialog());
        buttonPanel.add(addSeriesButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the panel with the latest information.
     * This method triggers the update of the request table.
     */
    @Override
    public void updatePanel() {
        updateRequestTable();
    }

    /**
     * Updates the table displaying requests with the latest data.
     */
    private void updateRequestTable() {
        SwingUtilities.invokeLater(() -> {
            final DefaultTableModel model = (DefaultTableModel) this.requestsTable.getModel();
            model.setRowCount(0);
            for (final Request request
                    : ((AdminSessionController) getCurrentSessionContext().getController()).getInsertionRequests()) {
                model.addRow(new Object[]{
                        request.getNumber(),
                        request.getType(),
                        request.getTitle(),
                        request.getReleaseDate(),
                        request.getDescription(),
                        request.isClosed() ? "Si" : "No",
                        request.getUsername()
                });
            }
        });
    }

    private JTable createRequestsTable(final ViewContext currentSessionContext) {
        final List<Request> requests = ((AdminSessionController) currentSessionContext.getController()).getInsertionRequests();
        final DefaultTableModel requestsModelTable = new DefaultTableModel();
        requestsModelTable.addColumn("Numero");
        requestsModelTable.addColumn("Tipo");
        requestsModelTable.addColumn("Titolo");
        requestsModelTable.addColumn("AnnoUscita");
        requestsModelTable.addColumn("Descrizione");
        requestsModelTable.addColumn("Chiusa");
        requestsModelTable.addColumn("UsernameUtente");
        for (final Request request : requests) {
            requestsModelTable.addRow(new Object[]{
                    request.getNumber(),
                    request.getType(),
                    request.getTitle(),
                    request.getReleaseDate(),
                    request.getDescription(),
                    request.isClosed() ? "Si" : "No",
                    request.getUsername()});
        }
        final JTable requestsTable = new JTable(requestsModelTable) {
            @Override
            public Component prepareRenderer(
                    final TableCellRenderer requestRenderer,
                    final int requestRow,
                    final int requestColumn) {
                final Component component = super.prepareRenderer(
                        requestRenderer,
                        requestRow,
                        requestColumn);
                if (isRowSelected(requestRow)) {
                    component.setBackground(
                            new Color(254, 250, 246));
                } else {
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        };
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        requestsTable.setDefaultRenderer(Object.class, centerRenderer);
        requestsTable.setRowHeight(30);
        final JTableHeader requestsHeader = requestsTable.getTableHeader();
        requestsHeader.setFont(new Font("Arial", Font.BOLD, 12));
        requestsHeader.setForeground(Color.BLACK);
        requestsHeader.setBackground(Color.WHITE);
        requestsHeader.setOpaque(true);
        requestsTable.setBackground(Color.WHITE);
        requestsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        return requestsTable;
    }

    /**
     * Displays a dialog for deleting a request.
     * The dialog prompts the administrator to enter the code of the request to be deleted.
     */
    private void completeRequestDialog() {
        final JTextField reqCodeField = new JTextField(5);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Inserisci il Codice della Richiesta da Segnare come Completata:"));
        panel.add(reqCodeField);

        final int result = JOptionPane.showConfirmDialog(this, panel, "Completa Richiesta",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                final int codeRequest = Integer.parseInt(reqCodeField.getText());
                final boolean completed = completeRequest(codeRequest);
                if (completed) {
                    updateRequestTable();
                    JOptionPane.showMessageDialog(
                            this,
                            "La richiesta e' stata soddisfatta con successo.",
                            COMPLETED, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Errore durante il completamento della Richiesta.",
                            ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Inserisci un numero valido per il Codice.",
                        ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes the request with the specified code.
     *
     * @param code The code of the request to be deleted.
     * @return True if the request was successfully deleted, false otherwise.
     */
    private boolean completeRequest(final int code) {
        return ((AdminSessionController) getCurrentSessionContext().getController())
                .completeRequest(code);
    }
}

// CHECKSTYLE: MagicNumber ON
