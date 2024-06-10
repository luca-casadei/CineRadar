package unibo.cineradar.model.db.operations.admin;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.db.DBManager;

import java.sql.SQLException;
import java.util.Objects;

/**
 * The AdminAvailabilityOps class provides methods
 * for checking the availability of the objects.
 */
@SuppressFBWarnings(
        value = {
                "OBL_UNSATISFIED_OBLIGATION"
        },
        justification = "The parent class satisfies the obligation."
)
public final class AdminAvailabilityOps extends DBManager {

    /**
     * Checks if a specific season of a series is available.
     *
     * @param seriesId The id of the series to check.
     * @param seasonId The id of the season to check.
     * @return true if the season is not available, false otherwise.
     */
    public boolean isSeasonAvailable(final int seriesId, final int seasonId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice "
                    + "FROM serie, stagione "
                    + "WHERE serie.Codice = stagione.CodiceSerie "
                    + "AND Codice = ? AND NumeroStagione = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesId);
            getPreparedStatement().setInt(2, seasonId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a series is available.
     *
     * @param seriesId The id of the series to check.
     * @return true if the series is not available, false otherwise.
     */
    public boolean isSeriesAvailable(final int seriesId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice FROM serie"
                    + " WHERE Codice = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a film is available.
     *
     * @param filmId The id of the film to check.
     * @return true if the film is not available, false otherwise.
     */
    public boolean isFilmAvailable(final int filmId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice FROM film"
                    + " WHERE Codice = ? ";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, filmId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a cast member is available.
     *
     * @param castMemberId The id of the cast member to check.
     * @return true if the cast member is not available, false otherwise.
     */
    public boolean isCastMemberAvailable(final int castMemberId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice FROM membrocast"
                    + " WHERE Codice = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castMemberId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks whether a cast session is unavailable for the provided cast ID.
     *
     * @param castId The ID of the cast session to check availability for.
     * @return true if the cast session is unavailable, false otherwise.
     */
    public boolean isCastAvailable(final int castId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice FROM casting"
                    + " WHERE Codice = ? ";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a promo is available.
     *
     * @param promoId The id of the promo to check.
     * @return true if the promo is not available, false otherwise.
     */
    public boolean isPromoAvailable(final int promoId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT CodiceTemplatePromo FROM promo "
                    + "WHERE CodiceTemplatePromo = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, promoId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a cinema is available.
     *
     * @param cinemaId The id of the cinema to check.
     * @return true if the cinema is not available, false otherwise.
     */
    public boolean isCinemaAvailable(final int cinemaId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice FROM cinema "
                    + "WHERE Codice = ? ";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, cinemaId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a user is available.
     *
     * @param username The username of the user to check.
     * @return true if the user is not available, false otherwise.
     */
    public boolean isUserAvailable(final String username) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Username FROM utente "
                    + "WHERE Username = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setString(1, username);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a cast with the specified ID exists.
     *
     * @param castId The ID of the cast to be checked.
     * @return {@code true} if a cast with the specified ID exists, {@code false} otherwise.
     */
    public boolean isCast(final int castId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM casting, partecipazione_cast "
                    + "WHERE casting.Codice = partecipazione_cast.CodiceCast "
                    + "AND casting.Codice = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castId);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the cast with the specified ID is empty.
     *
     * @param castId the ID of the cast to check.
     * @return {@code true} if the cast is empty, {@code false} otherwise.
     * @throws IllegalArgumentException if there is an error checking if the cast is empty.
     */
    public boolean isEmptyCast(final int castId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM partecipazione_cast "
                    + "WHERE partecipazione_cast.CodiceCast = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castId);
            setResultSet(getPreparedStatement().executeQuery());
            return !getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the series with the specified ID is empty.
     *
     * @param seriesCode the ID of the series to check.
     * @return {@code true} if the series is empty, {@code false} otherwise.
     */
    public boolean isEmptySeries(final int seriesCode) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM STAGIONE "
                    + "WHERE CodiceSerie = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesCode);
            setResultSet(getPreparedStatement().executeQuery());
            return !getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if the season of a series with the specified ID is empty.
     *
     * @param seriesCode    the ID of the series to check.
     * @param seasonNumber  the ID of the season to check.
     * @return {@code true} if the series is empty, {@code false} otherwise.
     */
    public boolean isEmptySeason(final int seriesCode, final int seasonNumber) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM EPISODIO "
                    + "WHERE CodiceSerie = ? AND NumeroStagione = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesCode);
            getPreparedStatement().setInt(2, seasonNumber);
            setResultSet(getPreparedStatement().executeQuery());
            return !getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a template promotion with the specified code is available in the database.
     *
     * @param codePromo the code of the template promotion to check.
     * @return {@code true} if the template promotion is available, {@code false} otherwise.
     * @throws NullPointerException if the database connection is {@code null}.
     * @throws IllegalArgumentException if a SQL exception occurs.
     */
    public boolean isTemplatePromoAvailable(final int codePromo) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT CodicePromo FROM TEMPLATEPROMO "
                    + "WHERE CodicePromo = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, codePromo);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Checks if a multiple is available for the specified genre promotion in the database.
     *
     * @param genrePromo the genre promotion to check.
     * @return {@code true} if the multiple is available, {@code false} otherwise.
     * @throws NullPointerException if the database connection is {@code null}.
     * @throws IllegalArgumentException if a SQL exception occurs.
     */
    public boolean isMultipleAvailable(final int genrePromo) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT CodiceTemplatePromo FROM MULTIPLO "
                    + "WHERE CodiceTemplatePromo = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, genrePromo);
            setResultSet(getPreparedStatement().executeQuery());
            return getResultSet().next();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
