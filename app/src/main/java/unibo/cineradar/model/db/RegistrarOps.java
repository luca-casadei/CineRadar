package unibo.cineradar.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.utente.Registrar;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;


/**
 * Database operations that the registrar can perform.
 */
@SuppressFBWarnings(
        value = {
                "OBL_UNSATISFIED_OBLIGATION"
        },
        justification = "The parent class satisfies the obligation."
)
public final class RegistrarOps extends DBManager {
    /**
     * Gets the cinema associated with a registrar.
     *
     * @param cinemaCode The numeric code of the cinema to search.
     * @return Optional of a Cinema if found, empty otherwise.
     */
    public Optional<Cinema> getAssociatedCinema(final int cinemaCode) {
        Objects.requireNonNull(super.getConnection());
        try {
            final String query = "SELECT cinema.* "
                    + "FROM cinema WHERE cinema.Codice = ?";
            super.setPreparedStatement(super.getConnection().prepareStatement(query));
            super.getPreparedStatement().setInt(1, cinemaCode);
            super.setResultSet(super.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                return Optional.of(
                        new Cinema(
                                getResultSet().getInt("Codice"),
                                getResultSet().getString("Nome"),
                                getResultSet().getString("Ind_Via"),
                                getResultSet().getString("Ind_CAP"),
                                getResultSet().getString("Ind_Citta"),
                                getResultSet().getInt("Ind_Civico")
                        )
                );
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Gets the details of a registrar given its username.
     *
     * @param username The username of the registrar.
     * @return A list of details of the retrieved account.
     */
    public Optional<Registrar> getRegistrarDetails(final String username) {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT registratore.Username, Nome, Cognome, registratore.EmailCinema, "
                    + "registratore.CodiceCinema "
                    + "FROM registratore JOIN account "
                    + "ON registratore.Username = account.Username "
                    + "WHERE account.Username = ?";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.getPreparedStatement().setString(1, username);
            this.setResultSet(this.getPreparedStatement().executeQuery());
            if (this.getResultSet().next()) {
                return Optional.of(new Registrar(
                        this.getResultSet().getString("Username"),
                        this.getResultSet().getString("Nome"),
                        this.getResultSet().getString("Cognome"),
                        this.getResultSet().getString("EmailCinema"),
                        this.getResultSet().getInt("CodiceCinema")
                ));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
