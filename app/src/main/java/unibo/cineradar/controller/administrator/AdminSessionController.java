package unibo.cineradar.controller.administrator;

import unibo.cineradar.controller.SessionController;
import unibo.cineradar.controller.SessionControllerImpl;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Serie;

import java.util.List;

/**
 * Controller class for the admin.
 */
public final class AdminSessionController extends SessionControllerImpl {

    private final AdministratorContext administratorContext;

    /**
     * Creates the session controller of the admin.
     *
     * @param ctr The existing generic controller.
     */
    public AdminSessionController(final SessionController ctr) {
        super(ctr);
        this.administratorContext = (AdministratorContext) getGenericContext();
    }

    /**
     * Gets every insertion request.
     *
     * @return A list containing insertion requests.
     */
    public List<Request> getInsertionRequests() {
        return this.administratorContext.getInsertionsRequests();
    }

    /**
     * Gets the list of all films.
     *
     * @return A list of Films.
     */
    public List<Film> getFilms() {
        return this.administratorContext.getFilms();
    }

    /**
     * Gets the list of all series.
     *
     * @return A list of series.
     */
    public List<Serie> getSeries() {
        return this.administratorContext.getSeries();
    }

    public void addFilm(String titolo, int etaLimite, String trama, int durata) {
        this.administratorContext.addFilm(new Film(0, titolo, etaLimite, trama, durata, 0));
    }

    public void addSeries(String titolo, int etaLimite, String trama, int durataComplessiva, int numeroEpisodi) {
        this.administratorContext.addSerie(new Serie(0, titolo, etaLimite, trama, durataComplessiva, numeroEpisodi));
    }

    public boolean deleteFilm(final int code) {
        return this.administratorContext.deleteFilm(code);
    }

    public boolean deleteSeries(final int code) {
        return this.administratorContext.deleteSeries(code);
    }
}
