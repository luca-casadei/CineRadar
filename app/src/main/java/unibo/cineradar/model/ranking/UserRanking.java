package unibo.cineradar.model.ranking;

/**
 * Represents a ranking entry for a user.
 * This record class contains the username of the user and their corresponding evaluation.
 *
 * @param username   The username of the user.
 * @param evaluation The evaluation associated with the user.
 */
public record UserRanking(String username, int evaluation) {
}
