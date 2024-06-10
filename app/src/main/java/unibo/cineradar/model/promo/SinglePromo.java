package unibo.cineradar.model.promo;

/**
 * Represents a single promotion with a promotion code, series code, and film code.
 *
 * @param codePromo the code of the single promotion.
 * @param seriesCode the code of the series associated with the promotion.
 * @param filmCode the code of the film associated with the promotion.
 */
public record SinglePromo(int codePromo, int seriesCode, int filmCode) {
    // No additional methods, as records provide default implementations.
}
