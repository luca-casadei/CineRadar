package unibo.cineradar.view.homepage.user.review;

import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Multimedia;
import unibo.cineradar.model.review.Section;
import unibo.cineradar.model.review.ReviewSection;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.view.ViewContext;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CHECKSTYLE: MagicNumber OFF

/**
 * This abstract class represents the generic Review Write View.
 */
public abstract class WriteReviewView extends JFrame {
    @Serial
    private static final long serialVersionUID = -5729493408363904557L;

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 500;

    private final transient UserSessionController uc;
    private final Map<Section, JSpinner> sectionRatingSpinners;
    private final Map<Section, JCheckBox> sectionCheckboxes;
    private final JTextField titleField;
    private final JTextArea descriptionArea;
    private final JButton submitButton;

    /**
     * Constructs a new WriteReviewView with the given user session context and multimedia object.
     *
     * @param currentSessionContext the current session context
     * @param multimedia            the multimedia object for which the review is being written
     */
    public WriteReviewView(final ViewContext currentSessionContext, final Multimedia multimedia) {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.uc = (UserSessionController) currentSessionContext.getController();
        this.sectionRatingSpinners = new HashMap<>();
        this.sectionCheckboxes = new HashMap<>();

        setTitle("Scrivi una recensione");
        setLayout(new BorderLayout());

        final JPanel mainPanel = new JPanel(new GridLayout(5, 1));

        final JLabel titleLabel = new JLabel("Titolo recensione:");
        titleField = new JTextField();
        titleField.setFont(new Font(titleField.getFont().getName(), Font.PLAIN, 19));
        final JLabel descriptionLabel = new JLabel("Descrizione:");
        descriptionArea = new JTextArea();
        final JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT / 10));
        final JLabel sectionsLabel = new JLabel("Sezioni di valutazione:");
        final JPanel sectionsPanel = new JPanel(new GridBagLayout());

        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        descriptionLabel.setFont(new Font(descriptionLabel.getFont().getName(), Font.BOLD, 20));
        sectionsLabel.setFont(new Font(sectionsLabel.getFont().getName(), Font.BOLD, 20));

        mainPanel.add(titleLabel);
        mainPanel.add(titleField);
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionScrollPane);
        mainPanel.add(sectionsLabel);

        add(mainPanel, BorderLayout.NORTH);
        add(sectionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Invia recensione");
        submitButton.setEnabled(false);
        add(submitButton, BorderLayout.SOUTH);

        titleField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                validateFields();
            }
        });
        descriptionArea.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(final java.awt.event.KeyEvent evt) {
                validateFields();
            }
        });

        submitButton.addActionListener(e -> {
            final String title = titleField.getText().trim();
            final String description = descriptionArea.getText().trim();

            if (title.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Il titolo e la descrizione non possono essere vuoti.",
                        "Errore di validazione",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            final int multimediaId;
            if (multimedia instanceof Film) {
                multimediaId = ((Film) multimedia).getFilmId();
            } else if (multimedia instanceof Serie) {
                multimediaId = ((Serie) multimedia).getSeriesId();
            } else {
                throw new IllegalArgumentException("Unknown multimedia type");
            }

            final List<ReviewSection> selectedSections = new ArrayList<>();

            sectionCheckboxes.forEach((section, checkBox) -> {
                if (checkBox.isSelected()) {
                    final JSpinner spinner = sectionRatingSpinners.get(section);
                    final int rating = (int) spinner.getValue();
                    selectedSections.add(new ReviewSection(multimediaId, section, rating));
                }
            });


            final boolean reviewInserted = insertReview(multimediaId, title, description, selectedSections);
            if (reviewInserted) {
                JOptionPane.showMessageDialog(
                        this,
                        "Recensione inviata con successo!",
                        "Successo",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            }
        });

        loadSections(sectionsPanel);
    }

    /**
     * Retrieve the user session context.
     *
     * @return The user session context.
     */
    protected UserSessionController getUserSessionContext() {
        return this.uc;
    }

    /**
     * Inserts a review into the database.
     *
     * @param multimediaId the ID of the multimedia object
     * @param title        the title of the review
     * @param desc         the description of the review
     * @param sections     the list of review sections
     *
     * @return The status of the operation (true, false).
     */
    public abstract boolean insertReview(int multimediaId,
                                         String title,
                                         String desc,
                                         List<ReviewSection> sections);

    /**
     * Loads sections and respective rating scales.
     *
     * @param sectionsPanel the panel to load sections onto
     */
    private void loadSections(final JPanel sectionsPanel) {
        final List<Section> sections = this.uc.getSections();
        for (int i = 0; i < sections.size(); i++) {
            final Section section = sections.get(i);
            final JLabel sectionNameLabel = new JLabel(section.name() + ":");
            sectionNameLabel.setFont(new Font(sectionNameLabel.getFont().getName(), Font.BOLD, 14));

            final JCheckBox sectionCheckBox = getSectionCheckBox(section);

            final JLabel sectionDetailLabel = new JLabel(section.detail());
            sectionDetailLabel.setFont(new Font(sectionDetailLabel.getFont().getName(), Font.PLAIN, 12));
            final JSpinner sectionRatingSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            final JComponent editor = sectionRatingSpinner.getEditor();
            if (editor instanceof final JSpinner.DefaultEditor spinnerEditor) {
                spinnerEditor.getTextField().setFont(new Font("Arial", Font.PLAIN, 16));
                spinnerEditor.getTextField().setPreferredSize(new Dimension(40, 30));
            }
            sectionRatingSpinner.setEnabled(false);
            sectionRatingSpinners.put(section, sectionRatingSpinner);
            sectionCheckboxes.put(section, sectionCheckBox);


            final GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.LINE_START;
            sectionsPanel.add(sectionNameLabel, gbc);

            gbc.gridx = 1;
            gbc.insets = new Insets(0, 10, 0, 0);
            sectionsPanel.add(sectionCheckBox, gbc);

            gbc.gridx = 2;
            gbc.weightx = 1.0;
            sectionsPanel.add(sectionDetailLabel, gbc);

            gbc.gridx = 3;
            gbc.weightx = 0.0;
            gbc.insets = new Insets(0, 20, 0, 0);
            sectionsPanel.add(sectionRatingSpinner, gbc);
        }
    }

    private JCheckBox getSectionCheckBox(final Section section) {
        final JCheckBox sectionCheckBox = new JCheckBox();
        sectionCheckBox.addActionListener(e -> {
            final JCheckBox checkBox = (JCheckBox) e.getSource();
            sectionRatingSpinners.get(section).setEnabled(checkBox.isSelected());
            validateFields();
        });
        return sectionCheckBox;
    }

    private void validateFields() {
        final String title = titleField.getText().trim();
        final String description = descriptionArea.getText().trim();

        final boolean isTitleFilled = !title.isEmpty();
        final boolean isDescriptionFilled = !description.isEmpty();

        final boolean isAnySectionSelected = sectionCheckboxes.values().stream()
                .anyMatch(JCheckBox::isSelected);

        submitButton.setEnabled(isTitleFilled && isDescriptionFilled && isAnySectionSelected);
    }
}

// CHECKSTYLE: MagicNumber ON
