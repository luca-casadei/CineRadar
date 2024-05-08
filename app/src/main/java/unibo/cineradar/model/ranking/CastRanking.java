package unibo.cineradar.model.ranking;

/**
 * Represents a ranking entry for a member of a cast.
 * This record class contains the name, surname, and evaluation count of the cast member.
 *
 * @param name       The first name of the cast member.
 * @param surname    The last name of the cast member.
 * @param evaluation The count of evaluations or appearances of the cast member in the ranking.
 */
public record CastRanking(String name, String surname, int evaluation) {
}
