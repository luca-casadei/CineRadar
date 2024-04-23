package unibo.cineradar.model;

import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.utente.Account;
import unibo.cineradar.model.utente.Administrator;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;

import java.time.Year;
import java.util.List;
import java.util.Objects;

/**
 * Saves the current session when logged in.
 *
 * @param currentlyLoggedAccount The current logged account.
 */
public record SessionContext(Account currentlyLoggedAccount) {
    /**
     * The current session constructor.
     *
     * @param currentlyLoggedAccount The account author of the session.
     */
    public SessionContext {
    }

    /**
     * Checks if the session is valid.
     *
     * @return True if the session is valid, false otherwise.
     */
    public boolean isValid() {
        return !Objects.isNull(this.currentlyLoggedAccount);
    }

    /**
     * Gets the type of the current user.
     *
     * @return A LoginType instance that states the type of the current user.
     */
    public LoginType getUserType() {
        if (this.currentlyLoggedAccount instanceof Administrator) {
            return LoginType.ADMINISTRATION;
        } else if (this.currentlyLoggedAccount instanceof User) {
            return LoginType.USER;
        } else if (this.currentlyLoggedAccount instanceof Registrar) {
            return LoginType.REGISTRATION;
        } else {
            throw new IllegalStateException("Login type not supported.");
        }
    }

    /**
     * Gets the currently logged account.
     *
     * @return The instance of the currently logged account.
     */
    @Override
    public Account currentlyLoggedAccount() {
        return this.currentlyLoggedAccount;
    }

    /**
     * Gets the films.
     *
     * @return The list of all films.
     */
    public List<Film> getFilms() {
        try (DBManager mgr = new DBManager()) {
            if (this.currentlyLoggedAccount instanceof User) {
                return List.copyOf(mgr.getFilms(
                        Year.now().getValue() - ((User) (this.currentlyLoggedAccount)).getBirthDate().getYear())
                );
            }
            throw new IllegalStateException("Type not supported.");
        }
    }

    /**
     * Gets the series.
     *
     * @return The list of all the series.
     */
    public List<Serie> getSeries() {
        try (DBManager mgr = new DBManager()) {
            if (this.currentlyLoggedAccount instanceof User) {
                return List.copyOf(mgr.getSeries(
                        Year.now().getValue() - ((User) (this.currentlyLoggedAccount)).getBirthDate().getYear())
                );
            }
            throw new IllegalStateException("Type not supported.");
        }
    }

    /**
     * Gets the requests.
     *
     * @return The list of all requests.
     */
    public List<Request> getRequests() {
        try (DBManager mgr = new DBManager()) {
            return List.copyOf(mgr.getRequests());
        }
    }
}
