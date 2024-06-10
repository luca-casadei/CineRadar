package unibo.cineradar.model.promo;

/**
 * Represents a genre promotion with a genre promo code and the associated genre name.
 *
 * @param genrePromoCode the code of the genre promotion.
 * @param genre the name of the genre associated with the promotion.
 */
public record GenrePromo(int genrePromoCode, String genre) {
    // No additional methods, as records provide default implementations.
}
