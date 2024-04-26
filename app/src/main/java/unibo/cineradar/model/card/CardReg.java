package unibo.cineradar.model.card;

import java.time.LocalDate;

/**
 * A card.
 */
public final class CardReg {
    private final String user;
    private final LocalDate date;
    private final int cinemaCode;
    private final int cardNr;

    /**
     * Creates the card.
     *
     * @param user        The user of the card.
     * @param renewalDate The renewal date of the card.
     * @param cinemaCode  The cinema code of the card.
     * @param cardNumber  The number of the card in the cinema.
     */
    public CardReg(final String user, final LocalDate renewalDate, final int cinemaCode, final int cardNumber) {
        this.user = user;
        this.cardNr = cardNumber;
        this.date = renewalDate;
        this.cinemaCode = cinemaCode;
    }

    /**
     * Gets the username of the owner.
     *
     * @return A string containing the username of the owner.
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets the renewal date.
     *
     * @return The renewal date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the associated cinema code.
     *
     * @return The cinema code.
     */
    public int getCinemaCode() {
        return cinemaCode;
    }

    /**
     * Gets the card number in the cinema.
     *
     * @return The card number
     */
    public int getCardNr() {
        return cardNr;
    }
}
