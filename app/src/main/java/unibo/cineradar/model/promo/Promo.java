package unibo.cineradar.model.promo;

import java.time.LocalDate;

/**
 * A record representing a promotional offer.
 * The {@code Promo} record encapsulates the details of a promotional offer, including its unique identifier,
 * the discount percentage, and the expiration date.
 *
 * @param id the unique identifier of the promotional offer.
 * @param percentageDiscount the discount percentage of the promotional offer.
 * @param expiration the expiration date of the promotional offer.
 */
public record Promo(int id, int percentageDiscount, LocalDate expiration) {
}
