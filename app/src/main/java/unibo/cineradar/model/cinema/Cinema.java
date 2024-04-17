package unibo.cineradar.model.cinema;

/**
 * Represents a cinema in the model.
 *
 * @param codice The main ID of the cinema.
 * @param nome   The name of the cinema.
 * @param indVia The street of the cinema.
 * @param indCAP The postal code of the cinema.
 * @param citta  The town of the cinema.
 * @param civico The street number of the cinema.
 */
public record Cinema(int codice, String nome, String indVia, String indCAP, String citta, int civico) {
}
