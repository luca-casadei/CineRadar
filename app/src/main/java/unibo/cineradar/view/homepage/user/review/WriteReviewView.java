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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        setTitle("Scrivi una recensione");
        setLayout(new BorderLayout());

        final JPanel mainPanel = new JPanel(new GridLayout(5, 1));

        final JLabel titleLabel = new JLabel("Titolo recensione:");
        final JTextField titleField = new JTextField();
        titleField.setFont(new Font(titleField.getFont().getName(), Font.PLAIN, 19));
        final JLabel descriptionLabel = new JLabel("Descrizione:");
        final JTextArea descriptionArea = new JTextArea();
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

        final JButton submitButton = new JButton("Invia recensione");
        add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int multimediaId;
                if (multimedia instanceof Film) {
                    multimediaId = ((Film) multimedia).getFilmId();
                } else if (multimedia instanceof Serie) {
                    multimediaId = ((Serie) multimedia).getSeriesId();
                } else {
                    throw new IllegalArgumentException("Unknown multimedia type");
                }

                final List<ReviewSection> selectedSections = new ArrayList<>();
                /*
                for (final Section section : sectionRatingSpinners.keySet()) {
                    final JSpinner spinner = sectionRatingSpinners.get(section);
                    if (((JCheckBox) spinner.getParent().getComponent(1)).isSelected()) {
                        final int rating = (int) spinner.getValue();
                        // TODO: selectedSections.add(new ReviewSection(section, rating));
                    }
                }
                */
                insertReview(multimediaId, titleField.getText(), descriptionArea.getText(), selectedSections);
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
     */
    public abstract void insertReview(int multimediaId,
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
            final JLabel sectionNameLabel = new JLabel(section.getName() + ":");
            sectionNameLabel.setFont(new Font(sectionNameLabel.getFont().getName(), Font.BOLD, 14));

            final JCheckBox sectionCheckBox = new JCheckBox();
            sectionCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final JCheckBox checkBox = (JCheckBox) e.getSource();
                    if (checkBox.isSelected()) {
                        sectionRatingSpinners.get(section).setEnabled(true);
                    } else {
                        sectionRatingSpinners.get(section).setEnabled(false);
                    }
                }
            });

            final JLabel sectionDetailLabel = new JLabel(section.getDetail());
            sectionDetailLabel.setFont(new Font(sectionDetailLabel.getFont().getName(), Font.PLAIN, 12));
            final JSpinner sectionRatingSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            final JComponent editor = sectionRatingSpinner.getEditor();
            if (editor instanceof final JSpinner.DefaultEditor spinnerEditor) {
                spinnerEditor.getTextField().setFont(new Font("Arial", Font.PLAIN, 16));
                spinnerEditor.getTextField().setPreferredSize(new Dimension(40, 30));
            }
            sectionRatingSpinner.setEnabled(false);
            sectionRatingSpinners.put(section, sectionRatingSpinner);

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
}

// CHECKSTYLE: MagicNumber ON
