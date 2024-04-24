package unibo.cineradar.view.homepage.registrar;

import com.github.lgooddatepicker.components.DatePicker;
import unibo.cineradar.controller.registrar.RegistrarSessionController;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.view.ViewContext;
import unibo.cineradar.view.utilities.ViewUtilities;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serial;

// CHECKSTYLE: MagicNumber OFF

/**
 * The form that permits the registrar to see the associated cinema infos.
 */
public final class CardedCinemaPanel extends JPanel {
    private static final int TITLE_FONT_SIZE = 40;
    private static final int LABEL_FONT_SIZE = 30;
    private static final int MARGIN_RIGHT_LABEL = 100;
    private static final int UP_DOWN_MARGIN = 50;
    @Serial
    private static final long serialVersionUID = 6466145857318357871L;

    /**
     * The constructor of the component.
     *
     * @param context The view context.
     */
    public CardedCinemaPanel(final ViewContext context) {
        final Cinema gottenCinema = ((RegistrarSessionController) context.getController()).getCinemaDetails();
        setLayout(new BorderLayout());
        final JLabel cinemaLabel = new JLabel("CINEMA E REGISTRAZIONE");
        cinemaLabel.setFont(new Font("", Font.BOLD, TITLE_FONT_SIZE));
        cinemaLabel.setHorizontalAlignment(JLabel.CENTER);
        cinemaLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, TITLE_FONT_SIZE));
        cinemaLabel.setForeground(Color.BLUE);
        this.add(cinemaLabel, BorderLayout.NORTH);
        final JPanel cinemaPanel = new JPanel();
        this.add(cinemaPanel, BorderLayout.CENTER);
        cinemaPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = 1;

        // FORM

        ViewUtilities.setGridBagConstraints(gbc, 0, 0, 2, 1,
                new Insets(0, 0, UP_DOWN_MARGIN, 0));
        final JLabel cineTitleLbl = new JLabel("INFORMAZIONI CINEMA");
        cineTitleLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, TITLE_FONT_SIZE));
        cineTitleLbl.setHorizontalAlignment(JLabel.CENTER);
        cineTitleLbl.setForeground(new Color(255, 100, 0));
        cinemaPanel.add(cineTitleLbl, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 1, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JLabel nameLbl = new JLabel("NOME :");
        nameLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(nameLbl, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 1, 1, 1,
                new Insets(0, 0, 0, 0));
        final JLabel nameContainer = new JLabel(gottenCinema.nome());
        nameContainer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(nameContainer, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 2, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JLabel viaLbl = new JLabel("VIA :");
        viaLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(viaLbl, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 2, 1, 1,
                new Insets(0, 0, 0, 0));
        final JLabel viaContainer = new JLabel(gottenCinema.indVia() + " " + gottenCinema.civico());
        viaContainer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(viaContainer, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 3, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JLabel capLbl = new JLabel("CAP :");
        capLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(capLbl, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 3, 1, 1,
                new Insets(0, 0, 0, 0));
        final JLabel capContainer = new JLabel(gottenCinema.indCAP());
        capContainer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(capContainer, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 4, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JLabel cittaLbl = new JLabel("CITTA' :");
        cittaLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(cittaLbl, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 4, 1, 1,
                new Insets(0, 0, 0, 0));
        final JLabel cittaContainer = new JLabel(gottenCinema.citta());
        cittaContainer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(cittaContainer, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 5, 2, 1,
                new Insets(UP_DOWN_MARGIN, 0, UP_DOWN_MARGIN, 0));
        final JLabel regTitle = new JLabel("REGISTRA UN TESSERAMENTO");
        regTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, TITLE_FONT_SIZE));
        regTitle.setHorizontalAlignment(JLabel.CENTER);
        regTitle.setForeground(new Color(255, 100, 0));
        cinemaPanel.add(regTitle, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 6, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JLabel userSelLbl = new JLabel("Utente da tesserare:");
        userSelLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(userSelLbl, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 6, 1, 1,
                new Insets(0, 0, 0, 0));
        final JTextField userSelFld = new JTextField();
        userSelFld.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        userSelFld.setHorizontalAlignment(JTextField.CENTER);
        cinemaPanel.add(userSelFld, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 7, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JLabel cardNrL = new JLabel("Numero tessera:");
        cardNrL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(cardNrL, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 7, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JSpinner cardNr = new JSpinner(new SpinnerNumberModel(0, 0, 3500, 1));
        cardNr.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(cardNr, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 0, 8, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final JLabel dateL = new JLabel("Data rinnovo:");
        dateL.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LABEL_FONT_SIZE));
        cinemaPanel.add(dateL, gbc);

        ViewUtilities.setGridBagConstraints(gbc, 1, 8, 1, 1,
                new Insets(0, 0, 0, MARGIN_RIGHT_LABEL));
        final DatePicker dPr = new DatePicker();
        cinemaPanel.add(dPr, gbc);
    }
}

// CHECKSTYLE: MagicNumber ON
