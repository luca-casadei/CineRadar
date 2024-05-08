package unibo.cineradar.view.homepage.admin;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.view.ViewContext;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.Serial;

/**
 * Represents the view for managing cast members and casting details in the administrator's homepage.
 * This class extends AdminPanel and provides a graphical user interface for administrators
 * to view and manage cast members and casting details.
 * It displays two tables: one for cast members and one for casting details.
 */
public class AdminCastView extends AdminPanel {
    @Serial
    private static final long serialVersionUID = -6223416854469473829L;
    private final JTable castMemberTable;
    private final JTable castTable;

    /**
     * Constructs an instance of AdminCastView.
     *
     * @param currentSessionContext The ViewContext representing the current session's context.
     */
    public AdminCastView(final ViewContext currentSessionContext) {
        super(currentSessionContext);
        setLayout(new GridBagLayout());

        this.castMemberTable = super.createCastMemberTable();
        this.castTable = super.createCastTable();

        final JScrollPane castMemberScrollPane = new JScrollPane(castMemberTable);
        final JScrollPane castScrollPane = new JScrollPane(castTable);

        final GridBagConstraints castMemberConstraints = new GridBagConstraints();
        castMemberConstraints.gridx = 1;
        castMemberConstraints.gridy = 0;
        castMemberConstraints.weightx = 0.5;
        castMemberConstraints.weighty = 1.0;
        castMemberConstraints.fill = GridBagConstraints.BOTH;
        add(castMemberScrollPane, castMemberConstraints);

        final GridBagConstraints castConstraints = new GridBagConstraints();
        castConstraints.gridx = 0;
        castConstraints.gridy = 0;
        castConstraints.weightx = 0.5;
        castConstraints.weighty = 1.0;
        castConstraints.fill = GridBagConstraints.BOTH;
        add(castScrollPane, castConstraints);
    }

    /**
     * Overrides the paintComponent method to update the tables when the component is painted.
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        updateMemberCastTable();
        updateCastTable();
    }

    /**
     * Updates the cast member table with the latest data from the database.
     */
    private void updateMemberCastTable() {
        final DefaultTableModel model = (DefaultTableModel) this.castMemberTable.getModel();
        model.setRowCount(0);
        for (final CastMember castMember
                : ((AdminSessionController) this.getCurrentSessionContext().getController()).getCastMembers()) {
            model.addRow(new Object[]{
                    castMember.getId(),
                    castMember.getName(),
                    castMember.getLastName(),
                    castMember.getBirthDate(),
                    castMember instanceof Actor ? "Attore" : "Regista",
                    castMember.getCareerDebutDate(),
                    castMember.getStageName()
            });
        }
    }

    /**
     * Updates the casting table with the latest data from the database.
     */
    private void updateCastTable() {
        final DefaultTableModel model = (DefaultTableModel) this.castTable.getModel();
        model.setRowCount(0);
        for (final Casting casting : ((AdminSessionController) this.getCurrentSessionContext().getController()).getCasting()) {
            model.addRow(new Object[]{
                    casting.id(),
                    casting.name(),
            });
        }
    }
}
