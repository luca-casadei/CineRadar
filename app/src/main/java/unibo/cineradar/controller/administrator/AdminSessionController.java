package unibo.cineradar.controller.administrator;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Serie;

import java.util.List;

/**
 * Controller class for the administrator session.
 * This class extends SessionControllerImpl and provides additional functionality specific to administrator operations.
 */
public final class AdminSessionController extends SessionControllerImpl {

    private final AdministratorContext administratorContext;

    /**
     * Constructs an AdminSessionController with the given generic controller.
     *
     * @param ctr The existing generic controller.
     */
    public AdminSessionController(final SessionController ctr) {
        super(ctr);
        this.administratorContext = (AdministratorContext) getGenericContext();
    }

    /**
     * Retrieves a list of insertion requests.
     *
     * @return A list containing insertion requests.
     */
    public List<Request> getInsertionRequests() {
        return this.administratorContext.getInsertionsRequests();
    }

    /**
     * Retrieves a list of all films.
     *
     * @return A list of Film objects.
     */
    public List<Film> getFilms() {
        return this.administratorContext.getFilms();
    }

    /**
     * Retrieves a list of all series.
     *
     * @return A list of series.
     */
    public List<Serie> getSeries() {
        return this.administratorContext.getSeries();
    }

    /**
     * Adds a new film with the specified details.
     *
     * @param title     The title of the film.
     * @param ageLimit  The age limit for the film.
     * @param plot      The plot summary of the film.
     * @param duration  The duration of the film in minutes.
     * @param idCast    The ID of the cast associated with the film.
     */
    public void addFilm(
            final String title, final int ageLimit, final String plot, final int duration, final int idCast) {
        this.administratorContext.addFilm(new Film(0, title, ageLimit, plot, duration, idCast));
    }

    /**
     * Adds a new TV series with the specified details.
     *
     * @param title           The title of the TV series.
     * @param ageLimit        The age limit for the TV series.
     * @param plot            The plot summary of the TV series.
     * @param duration        The total duration of the TV series in minutes.
     * @param episodesNumber  The number of episodes in the TV series.
     */
    public void addSeries(
            final String title, final int ageLimit, final String plot, final int duration, final int episodesNumber) {
        this.administratorContext.addSerie(new Serie(0, title, ageLimit, plot, duration, episodesNumber));
    }

    /**
     * Deletes a film with the specified code.
     *
     * @param code The code of the film to be deleted.
     * @return True if the film was successfully deleted, false otherwise.
     */
    public boolean deleteFilm(final int code) {
        return this.administratorContext.deleteFilm(code);
    }

    /**
     * Deletes a TV series with the specified code.
     *
     * @param code The code of the TV series to be deleted.
     * @return True if the TV series was successfully deleted, false otherwise.
     */
    public boolean deleteSeries(final int code) {
        return this.administratorContext.deleteSeries(code);
    }
}
