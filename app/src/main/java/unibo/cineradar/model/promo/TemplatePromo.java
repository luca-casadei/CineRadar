package unibo.cineradar.model.promo;

/**
 * Represents a template promotion with a promotion code and the percentage of discount.
 *
 * @param codePromo the code of the template promotion.
 * @param percentage the percentage of discount associated with the promotion.
 */
public record TemplatePromo(int codePromo, int percentage) {
    // No additional methods, as records provide default implementations.
}
