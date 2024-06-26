package unibo.cineradar.view.homepage.user;

import unibo.cineradar.model.utente.User;
import unibo.cineradar.view.ViewContext;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * Abstract class for user views with filtering capability.
 */
public abstract class UserFilteredView extends UserPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    private JTable contentTable;
    private JScrollPane scrollPane;
    private boolean isPreferredGenresFiltered;

    /**
     * Constructor of the user filtered view.
     *
     * @param currentSessionContext The context of the current user.
     * @param welcomeMessage        The welcome message to display.
     */
    protected UserFilteredView(final ViewContext currentSessionContext, final String welcomeMessage) {
        super(currentSessionContext);
        initComponents(currentSessionContext, welcomeMessage);
    }

    private void initComponents(final ViewContext currentSessionContext, final String welcomeMessage) {
        final JLabel welcomeLabel = new JLabel(welcomeMessage);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(welcomeLabel, BorderLayout.NORTH);
        this.isPreferredGenresFiltered = false;

        if (currentSessionContext.getController().getAccount() instanceof User user) {
            contentTable = createContentTable(user.getAge());
        }
        scrollPane = new JScrollPane(contentTable);
        this.add(scrollPane, BorderLayout.CENTER);

        final JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        final JCheckBox filterCheckbox = new JCheckBox("Filtra per eta'");
        filterPanel.add(filterCheckbox);

        final JTextArea ageTextArea = new JTextArea(1, 10);
        ageTextArea.setEnabled(false);
        filterPanel.add(new JLabel("Eta' limite:"));
        filterPanel.add(ageTextArea);

        final JButton filterButton = new JButton("Applica filtro");
        filterButton.setEnabled(false);
        filterPanel.add(filterButton);

        final JButton preferredGenresButton = new JButton("Filtra in base ai generi preferiti");
        filterPanel.add(preferredGenresButton);

        final JButton genreRankingButton = new JButton("Apri classifica generi");
        filterPanel.add(genreRankingButton);

        final JButton requestContentButton = new JButton("Richiesta di aggiunta contenuto");
        filterPanel.add(requestContentButton);

        this.add(filterPanel, BorderLayout.SOUTH);

        filterCheckbox.addActionListener(e -> {
            final boolean selected = filterCheckbox.isSelected();
            ageTextArea.setEnabled(selected);
            filterButton.setEnabled(selected);

            if (!selected) {
                resetTable(currentSessionContext);
            }
        });

        filterButton.addActionListener(e -> {
            final String text = ageTextArea.getText();
            if (!text.isEmpty() && filterCheckbox.isSelected()) {
                try {
                    final int ageLimit = Integer.parseInt(text);
                    applyAgeFilter(ageLimit);
                } catch (NumberFormatException ignored) {
                }
            }
        });

        genreRankingButton.addActionListener(e -> {
            showGenreRanking();
        });

        preferredGenresButton.addActionListener(f -> {
            isPreferredGenresFiltered = !isPreferredGenresFiltered;
            if (isPreferredGenresFiltered) {
                preferredGenresButton.setText("Togli filtro sui generi preferiti");
                if (currentSessionContext.getController().getAccount() instanceof User user) {
                    this.remove(scrollPane);
                    contentTable = showPreferredGenres(user.getAge());
                    scrollPane = new JScrollPane(contentTable);
                    this.add(scrollPane, BorderLayout.CENTER);
                    this.revalidate();
                    this.repaint();
                }
            } else {
                preferredGenresButton.setText("Filtra in base ai generi preferiti");
                resetTable(currentSessionContext);
            }
        });

        requestContentButton.addActionListener(e -> {
            final UserRequestContentView requestContentView = new UserRequestContentView(currentSessionContext);
            requestContentView.setVisible(true);
        });
    }

    private void applyAgeFilter(final int ageLimit) {
        this.remove(scrollPane);
        contentTable = createContentTable(ageLimit);
        scrollPane = new JScrollPane(contentTable);
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void resetTable(final ViewContext currentSessionContext) {
        this.remove(scrollPane);
        if (currentSessionContext.getController().getAccount() instanceof User user) {
            contentTable = createContentTable(user.getAge());
        }
        scrollPane = new JScrollPane(contentTable);
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    /**
     * Abstract method to create the content table with a specified age filter.
     *
     * @param age The age limit to apply to the content.
     * @return The created JTable.
     */
    protected abstract JTable createContentTable(int age);

    /**
     * Abstract method to show the ranking of genres based on the number of views.
     */
    protected abstract void showGenreRanking();

    /**
     * Abstract method to show content based on preferred genres.
     *
     * @param age The age limit to apply to the content.
     * @return The created JTable.
     */
    protected abstract JTable showPreferredGenres(int age);
}
