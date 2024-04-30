package unibo.cineradar.view.homepage.user.details;

import unibo.cineradar.view.ViewContext;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A view to display detailed information about a multimedia, including reviews.
 */
public class ReviewDetailsView extends JFrame {
    private final ViewContext currentSessionContext;
    private final int multimediaId;

    /**
     * Constructs a new MultimediaDetailsView.
     *
     * @param currentSessionContext The current session context.
     * @param multimediaId          The ID of the multimedia.
     */
    public ReviewDetailsView(ViewContext currentSessionContext, int multimediaId) {
        this.currentSessionContext = currentSessionContext;
        this.multimediaId = multimediaId;
        initComponents();
    }

    private void initComponents() {
        setTitle("Dettagli di " + retrieveMultimediaDetails().get(1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(retrieveMultimediaDetails().get(1));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        List<String> detailsList = retrieveMultimediaDetails();
        StringBuilder detailsText = new StringBuilder();
        for (String detail : detailsList) {
            detailsText.append(detail).append("\n");
        }
        detailsArea.setText(detailsText.toString());

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private List<String> retrieveMultimediaDetails() {
        return List.of(
                "ID: " + multimediaId,
                "Titolo: " + "prova",
                "Anno di uscita: 2023",
                "Genere: Azione, Avventura",
                "Regista: John Doe",
                "Attori: Actor1, Actor2, Actor3",
                "Durata: 120 min",
                "Sinossi: Lorem ipsum dolor sit amet, consectetur adipiscing elit."
        );
    }
}
