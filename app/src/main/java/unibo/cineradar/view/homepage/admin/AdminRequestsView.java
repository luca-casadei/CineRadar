package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.homepage.user.UserPanel;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.Serial;
import java.util.List;

// CHECKSTYLE: MagicNumber OFF

/**
 * Requests view of the Admin.
 */
public final class AdminRequestsView extends UserPanel {

    @Serial
    private static final long serialVersionUID = -47044683429723183L;

    /**
     * Constructor of the Admin Requests View.
     *
     * @param currentSessionContext The context of the current admin.
     */
    public AdminRequestsView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        // Adds the welcome label to the view
        final JLabel welcomeLabel = new JLabel("Benvenuto "
                + currentSessionContext.getController().getAccountDetails().get(0)
                + " nella pagina delle richieste degli utenti.");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);

        // Adds the Requests table to the view
        final JTable requestsTable = createRequestsTable(currentSessionContext);
        final JScrollPane scrollPane = new JScrollPane(requestsTable);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private JTable createRequestsTable(final ViewContext currentSessionContext) {
        // Retrieves the list of films from the controller
        final List<Request> requests = ((AdminSessionController) currentSessionContext.getController()).getInsertionRequests();

        // Creates the table
        final DefaultTableModel requestsModelTable = new DefaultTableModel();
        requestsModelTable.addColumn("Numero");
        requestsModelTable.addColumn("Tipo");
        requestsModelTable.addColumn("Titolo");
        requestsModelTable.addColumn("AnnoUscita");
        requestsModelTable.addColumn("Descrizione");
        requestsModelTable.addColumn("Chiusa");
        requestsModelTable.addColumn("UsernameUtente");

        // Adds Request data to the table
        for (final Request request : requests) {
            requestsModelTable.addRow(new Object[]{
                    request.getNumber(),
                    request.getType(),
                    request.getTitle(),
                    request.getReleaseDate(),
                    request.getDescription(),
                    request.isClosed(),
                    request.getUsername()});
        }

        // Creates the table with custom renderer for alternating row colors
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

        // Sets renderer to center-align cell contents
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        requestsTable.setDefaultRenderer(Object.class, centerRenderer);

        // Sets row height
        requestsTable.setRowHeight(30);

        // Customizes the table header
        final JTableHeader requestsHeader = requestsTable.getTableHeader();
        requestsHeader.setFont(new Font("Arial", Font.BOLD, 12));
        requestsHeader.setForeground(Color.BLACK);
        requestsHeader.setBackground(Color.WHITE);
        requestsHeader.setOpaque(true);
        requestsTable.setBackground(Color.WHITE);
        requestsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        return requestsTable;
    }
}

// CHECKSTYLE: MagicNumber ON
