package unibo.cineradar.model.db.operations.admin;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import unibo.cineradar.model.card.CardReg;
import unibo.cineradar.model.cast.Actor;
import unibo.cineradar.model.cast.Cast;
import unibo.cineradar.model.cast.CastMember;
import unibo.cineradar.model.cast.Casting;
import unibo.cineradar.model.cast.Director;
import unibo.cineradar.model.cinema.Cinema;
import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.film.Film;
import unibo.cineradar.model.multimedia.Genre;
import unibo.cineradar.model.promo.GenrePromo;
import unibo.cineradar.model.promo.Promo;
import unibo.cineradar.model.promo.SinglePromo;
import unibo.cineradar.model.promo.TemplatePromo;
import unibo.cineradar.model.request.Request;
import unibo.cineradar.model.serie.Episode;
import unibo.cineradar.model.serie.Season;
import unibo.cineradar.model.serie.Serie;
import unibo.cineradar.model.utente.Registrar;
import unibo.cineradar.model.utente.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Database operations that the Admin can perform.
 */
@SuppressFBWarnings(
        value = {
                "OBL_UNSATISFIED_OBLIGATION"
        },
        justification = "The parent class satisfies the obligation."
)
public final class AdminGetOps extends DBManager {

    private static final String SERIES_CODE = "CodiceSerie";
    private static final String CODE = "Codice";
    private static final String NAME = "Nome";
    private static final String SURNAME = "Cognome";
    private static final int DEBUT_DATE = 7;

    /**
     * Retrieves the list of all requests.
     *
     * @return The list of all requests.
     */
    public List<Request> getRequests() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT * "
                    + "FROM richiesta";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            final List<Request> requests = new ArrayList<>();
            while (this.getResultSet().next()) {
                final Request request = new Request(
                        this.getResultSet().getInt("Numero"),
                        this.getResultSet().getString("UsernameUtente"),
                        this.getResultSet().getBoolean("Tipo"),
                        this.getResultSet().getString("Titolo"),
                        this.getResultSet().getString("Descrizione"),
                        this.getResultSet().getBoolean("Chiusa"),
                        this.getResultSet().getDate("AnnoUscita")
                );
                requests.add(request);
            }
            return List.copyOf(requests);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of all films.
     *
     * @return The list of all films.
     */
    public List<Film> getFilms() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM film";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Film> films = new ArrayList<>();
            while (getResultSet().next()) {
                final Film film = new Film(
                        getResultSet().getInt(CODE),
                        getResultSet().getString("Titolo"),
                        getResultSet().getInt("EtaLimite"),
                        getResultSet().getString("Trama"),
                        getResultSet().getInt("Durata"),
                        getResultSet().getInt("CodiceCast")
                );
                films.add(film);
            }
            return List.copyOf(films);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of all the series.
     *
     * @return The list of all the series.
     */
    public List<Serie> getSeries() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM serie";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Serie> series = new ArrayList<>();
            while (getResultSet().next()) {
                final Serie serie = new Serie(
                        getResultSet().getInt(CODE),
                        getResultSet().getString("Titolo"),
                        getResultSet().getInt("EtaLimite"),
                        getResultSet().getString("Trama"),
                        getResultSet().getInt("DurataComplessiva"),
                        getResultSet().getInt("NumeroEpisodi")
                );
                series.add(serie);
            }
            return series;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves details of films including their cast from the database.
     *
     * @return A map containing films as keys and their corresponding cast as values.
     */
    public Map<Film, Cast> getFilmsDetails() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT film.Codice AS CodiceFilm, "
                    + "film.Titolo AS TitoloFilm, "
                    + "film.EtaLimite AS EtaLimiteFilm, "
                    + "film.Trama AS TramaFilm, "
                    + "film.Durata AS DurataFilm, "
                    + "film.CodiceCast AS CodiceCastFilm, "
                    + "membrocast.Codice AS CodiceMembroCast, "
                    + "membrocast.Nome AS NomeMembroCast, "
                    + "membrocast.Cognome AS CognomeMembroCast, "
                    + "membrocast.DataNascita AS DataNascitaMembroCast, "
                    + "membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast, "
                    + "membrocast.NomeArte AS NomeArteMembroCast, "
                    + "membrocast.TipoAttore AS TipoAttoreMembroCast, "
                    + "membrocast.TipoRegista AS TipoRegistaMembroCast "
                    + "FROM film "
                    + "JOIN casting ON film.CodiceCast = casting.Codice "
                    + "JOIN partecipazione_cast ON casting.codice = partecipazione_cast.CodiceCast "
                    + "JOIN membrocast ON partecipazione_cast.CodiceMembro = membrocast.Codice";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final Map<Film, Cast> detailedFilms = new HashMap<>();
            while (getResultSet().next()) {
                final int filmCode = getResultSet().getInt("CodiceFilm");
                final Film film = new Film(
                        filmCode,
                        getResultSet().getString("TitoloFilm"),
                        getResultSet().getInt("EtaLimiteFilm"),
                        getResultSet().getString("TramaFilm"),
                        getResultSet().getInt("DurataFilm"),
                        getResultSet().getInt("CodiceCastFilm")
                );
                final Cast cast = detailedFilms.getOrDefault(film, new Cast());
                cast.addCastMember(new CastMember(
                        getResultSet().getInt("CodiceMembroCast"),
                        getResultSet().getString("NomeMembroCast"),
                        getResultSet().getString("CognomeMembroCast"),
                        getResultSet().getDate("DataNascitaMembroCast").toLocalDate(),
                        getResultSet().getDate("DataDebuttoCarrieraMembroCast").toLocalDate(),
                        getResultSet().getString("NomeArteMembroCast")
                ));
                detailedFilms.put(film, cast);
            }
            return Map.copyOf(detailedFilms);
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves detailed information about series, including seasons, episodes, and cast members.
     *
     * @return A list of Series objects containing detailed information about series, seasons, episodes, and cast members.
     */
    public List<Serie> getDetailedSeries() {
        Objects.requireNonNull(this.getConnection());
        try {
            final String query = "SELECT serie.Codice AS CodiceSerie,"
                    + " stagione.NumeroStagione,"
                    + " episodio.NumeroEpisodio,"
                    + " serie.Titolo AS TitoloSerie,"
                    + " serie.EtaLimite AS EtaLimiteSerie,"
                    + " serie.Trama AS TramaSerie,"
                    + " serie.DurataComplessiva AS DurataComplessivaSerie,"
                    + " serie.NumeroEpisodi AS NumeroEpisodiSerie,"
                    + " stagione.Sunto AS SuntoStagione,"
                    + " episodio.DurataMin AS DurataEpisodio,"
                    + " casting.Nome AS NomeCasting,"
                    + " casting.Codice AS CodiceCast,"
                    + " membrocast.Codice AS CodiceMembroCast,"
                    + " membrocast.Nome AS NomeMembroCast,"
                    + " membrocast.Cognome AS CognomeMembroCast,"
                    + " membrocast.DataNascita AS DataNascitaMembroCast,"
                    + " membrocast.DataDebuttoCarriera AS DataDebuttoCarrieraMembroCast,"
                    + " membrocast.NomeArte AS NomeArteMembroCast,"
                    + " membrocast.TipoAttore AS TipoAttoreMembroCast,"
                    + " membrocast.TipoRegista AS TipoRegistaMembroCast"
                    + " FROM serie"
                    + " JOIN stagione ON serie.Codice = stagione.CodiceSerie"
                    + " JOIN episodio ON episodio.NumeroStagione = stagione.NumeroStagione"
                    + " AND episodio.CodiceSerie = stagione.CodiceSerie"
                    + " JOIN casting ON casting.Codice = stagione.CodiceCast"
                    + " AND episodio.CodiceSerie = stagione.CodiceSerie"
                    + " JOIN partecipazione_cast ON partecipazione_cast.CodiceCast = casting.Codice"
                    + " JOIN membrocast ON membrocast.Codice = partecipazione_cast.CodiceMembro"
                    + " ORDER BY CodiceSerie";
            this.setPreparedStatement(this.getConnection().prepareStatement(query));
            this.setResultSet(this.getPreparedStatement().executeQuery());
            return processResultSet();
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of cast members from the database.
     *
     * @return A list of CastMember objects representing the cast members.
     */
    public List<CastMember> getCastMembers() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM membrocast";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<CastMember> members = new ArrayList<>();
            while (getResultSet().next()) {
                if (getResultSet().getBoolean("TipoAttore") && getResultSet().getBoolean("TipoRegista")) {
                    members.add(new CastMember(
                            getResultSet().getInt(1),
                            getResultSet().getString(2),
                            getResultSet().getString(3),
                            getResultSet().getDate(4).toLocalDate(),
                            getResultSet().getDate(DEBUT_DATE).toLocalDate(),
                            getResultSet().getString(8)
                    ));
                } else {
                    if (getResultSet().getBoolean("TipoAttore")) {
                        members.add(new Actor(
                                getResultSet().getInt(1),
                                getResultSet().getString(2),
                                getResultSet().getString(3),
                                getResultSet().getDate(4).toLocalDate(),
                                getResultSet().getDate(DEBUT_DATE).toLocalDate(),
                                getResultSet().getString(8)
                        ));
                    } else {
                        members.add(new Director(
                                getResultSet().getInt(1),
                                getResultSet().getString(2),
                                getResultSet().getString(3),
                                getResultSet().getDate(4).toLocalDate(),
                                getResultSet().getDate(DEBUT_DATE).toLocalDate(),
                                getResultSet().getString(8)
                        ));
                    }
                }
            }
            return List.copyOf(members);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving cast members: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of casting details from the database.
     *
     * @return A list of Casting objects representing the casting details.
     */
    public List<Casting> getCasting() {
        final String query = "SELECT * FROM casting";
        try {
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Casting> casting = new ArrayList<>();
            while (getResultSet().next()) {
                casting.add(new Casting(
                        getResultSet().getInt(1),
                        getResultSet().getString(2)
                ));
            }
            return casting;
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving casting: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the last added series from the database.
     *
     * @return The ID of the last series added to the system.
     */
    public Integer getLastSeriesId() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice"
                    + " FROM serie"
                    + " ORDER BY Codice DESC"
                    + " LIMIT 1";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());

            if (getResultSet().next()) {
                return getResultSet().getInt(1);
            } else {
                throw new SQLException("Error retrieving series id");
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the last added film from the database.
     *
     * @return The ID of the last film added to the system.
     */
    public int getLastFilmId() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice"
                    + " FROM film"
                    + " ORDER BY Codice DESC"
                    + " LIMIT 1";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());

            if (getResultSet().next()) {
                return getResultSet().getInt(1);
            } else {
                throw new SQLException("Error retrieving film id");
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the last added season for a given series from the database.
     *
     * @param seriesCode The code identifying the series.
     * @return The ID of the last season added to the specified series.
     */
    public Integer getLastSeasonId(final int seriesCode) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT NumeroStagione"
                    + " FROM stagione"
                    + " WHERE stagione.CodiceSerie = ?"
                    + " ORDER BY NumeroStagione DESC "
                    + "LIMIT 1";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, seriesCode);
            setResultSet(getPreparedStatement().executeQuery());

            if (getResultSet().next()) {
                return getResultSet().getInt(1);
            } else {
                throw new SQLException("Error retrieving season id");
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of promotional offers from the database.
     * This method executes an SQL query to fetch promo details by joining the 'templatepromo' and 'promo' tables.
     *
     * @return a list of {@link Promo} objects representing the current promotional offers.
     */
    public List<Promo> getPromos() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT templatepromo.CodicePromo, templatepromo.PercentualeSconto, promo.Scadenza"
                    + " FROM templatepromo"
                    + " JOIN promo ON templatepromo.CodicePromo = promo.CodiceTemplatePromo";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Promo> promos = new ArrayList<>();
            while (getResultSet().next()) {
                final Promo promo = new Promo(
                        getResultSet().getInt("CodicePromo"),
                        getResultSet().getInt("PercentualeSconto"),
                        getResultSet().getDate("Scadenza").toLocalDate()
                );
                promos.add(promo);
            }
            return List.copyOf(promos);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error updating request status: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the list of cards from the database.
     * This method executes an SQL query to fetch cards details.
     *
     * @return a list of {@link CardReg} objects representing the cards.
     */
    public List<CardReg> getCards() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM tessera";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<CardReg> cards = new ArrayList<>();
            while (getResultSet().next()) {
                final CardReg card = new CardReg(
                        getResultSet().getString("UsernameUtente"),
                        getResultSet().getDate("DataRinnovo").toLocalDate(),
                        getResultSet().getInt("CodiceCinema"),
                        getResultSet().getInt("NumeroTessera")
                );
                cards.add(card);
            }
            return List.copyOf(cards);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error updating request status: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves detailed information about the cast with the specified ID.
     *
     * @param castId The unique identifier of the cast.
     * @return A list of CastMember objects representing detailed information about the cast.
     */
    public List<CastMember> getDetailedCast(final int castId) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT membrocast.Codice, membrocast.Nome, membrocast.Cognome, "
                    + "membrocast.DataNascita, membrocast.DataDebuttoCarriera, membrocast.NomeArte "
                    + "FROM membrocast "
                    + "INNER JOIN partecipazione_cast ON membrocast.Codice = partecipazione_cast.CodiceMembro "
                    + "WHERE partecipazione_cast.CodiceCast = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castId);
            setResultSet(getPreparedStatement().executeQuery());
            final List<CastMember> castMembers = new ArrayList<>();
            while (getResultSet().next()) {
                final CastMember castMember = new CastMember(
                        getResultSet().getInt(CODE),
                        getResultSet().getString(NAME),
                        getResultSet().getString(SURNAME),
                        getResultSet().getDate("DataNascita").toLocalDate(),
                        getResultSet().getDate("DataDebuttoCarriera").toLocalDate(),
                        getResultSet().getString("NomeArte")
                );
                castMembers.add(castMember);
            }
            return List.copyOf(castMembers);
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving details of cast: " + ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of all users from the database.
     *
     * @return a list of User objects.
     */
    public List<User> getUsers() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT account.Username, account.Nome, account.Cognome, "
                    + "utente.DataNascita, utente.TargaPremio "
                    + "FROM account, utente "
                    + "WHERE account.Username = utente.Username";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<User> users = new ArrayList<>();
            while (getResultSet().next()) {
                users.add(new User(
                        getResultSet().getString("Username"),
                        getResultSet().getString(NAME),
                        getResultSet().getString(SURNAME),
                        getResultSet().getDate("DataNascita").toLocalDate(),
                        getResultSet().getBoolean("TargaPremio")
                ));
            }
            return users;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of all registrars from the database.
     *
     * @return a list of Registrar objects.
     */
    public List<Registrar> getRegistrars() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT account.Username, account.Nome, account.Cognome, "
                    + "registratore.EmailCinema, registratore.CodiceCinema "
                    + "FROM account, registratore "
                    + "WHERE account.Username = registratore.Username";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Registrar> registrars = new ArrayList<>();
            while (getResultSet().next()) {
                registrars.add(new Registrar(
                        getResultSet().getString("Username"),
                        getResultSet().getString(NAME),
                        getResultSet().getString(SURNAME),
                        getResultSet().getString("EmailCinema"),
                        getResultSet().getInt("CodiceCinema")
                ));
            }
            return registrars;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of all cinemas from the database.
     *
     * @return a list of Cinema objects.
     */
    public List<Cinema> getCinemas() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice, Nome, Ind_Via, "
                    + "Ind_CAP, Ind_Civico, Ind_Citta FROM cinema";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Cinema> cinemas = new ArrayList<>();
            while (getResultSet().next()) {
                cinemas.add(new Cinema(
                        getResultSet().getInt(CODE),
                        getResultSet().getString(NAME),
                        getResultSet().getString("Ind_Via"),
                        getResultSet().getString("Ind_CAP"),
                        getResultSet().getString("Ind_Citta"),
                        getResultSet().getInt("Ind_Civico"),
                        0
                ));
            }
            return cinemas;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the ID of the most recently added cast.
     *
     * @return the ID of the last cast.
     */
    public Integer getLastCastId() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT Codice"
                    + " FROM casting"
                    + " ORDER BY Codice DESC "
                    + "LIMIT 1";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            if (getResultSet().next()) {
                return getResultSet().getInt(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            throw new IllegalArgumentException("Error retrieving last cast id", ex);
        }
    }

    /**
     * Retrieves a list of cast member codes that are linked to a specified cast member.
     * This method executes a SQL query to fetch the linked cast members from the database.
     *
     * @param castMemberCode the unique code of the cast member whose linked cast members are to be retrieved
     * @return a list of integers representing the codes of the cast members linked to the specified cast member
     */
    public List<Integer> getCastLinked(final int castMemberCode) {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT CodiceCast FROM partecipazione_cast "
                    + "WHERE partecipazione_cast.CodiceMembro = ?";
            setPreparedStatement(getConnection().prepareStatement(query));
            getPreparedStatement().setInt(1, castMemberCode);
            setResultSet(getPreparedStatement().executeQuery());
            final List<Integer> castCodes = new ArrayList<>();
            while (getResultSet().next()) {
                castCodes.add(getResultSet().getInt("CodiceCast"));
            }
            return castCodes;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of multiples from the database.
     *
     * @return a list of integers representing the multiples.
     */
    public List<Integer> getMultiples() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT CodiceTemplatePromo FROM MULTIPLO";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<Integer> multipleCodes = new ArrayList<>();
            while (getResultSet().next()) {
                multipleCodes.add(getResultSet().getInt("CodiceTemplatePromo"));
            }
            return multipleCodes;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of template promotions from the database.
     *
     * @return a list of TemplatePromo objects.
     */
    public List<TemplatePromo> getTemplatePromos() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM TEMPLATEPROMO";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<TemplatePromo> templatePromos = new ArrayList<>();
            while (getResultSet().next()) {
                templatePromos.add(new TemplatePromo(
                        getResultSet().getInt("CodicePromo"),
                        getResultSet().getInt("PercentualeSconto")
                ));
            }
            return templatePromos;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of single promotions from the database.
     *
     * @return a list of SinglePromo objects.
     */
    public List<SinglePromo> getSinglePromos() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM SINGOLO";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<SinglePromo> singlePromos = new ArrayList<>();
            while (getResultSet().next()) {
                singlePromos.add(new SinglePromo(
                        getResultSet().getInt("CodiceTemplatePromo"),
                        getResultSet().getInt("CodiceSerie"),
                        getResultSet().getInt("CodiceFilm")
                ));
            }
            return singlePromos;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves a list of genre promotions from the database.
     *
     * @return a list of GenrePromo objects.
     */
    public List<GenrePromo> getGenrePromos() {
        Objects.requireNonNull(getConnection());
        try {
            final String query = "SELECT * FROM PROMO_GENERE";
            setPreparedStatement(getConnection().prepareStatement(query));
            setResultSet(getPreparedStatement().executeQuery());
            final List<GenrePromo> genrePromos = new ArrayList<>();
            while (getResultSet().next()) {
                genrePromos.add(new GenrePromo(
                        getResultSet().getInt("CodiceTemplateMultiplo"),
                        getResultSet().getString("NomeGenere")
                ));
            }
            return genrePromos;
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private List<Serie> processResultSet() throws SQLException {
        final Map<Integer, Serie> seriesMap = new HashMap<>();
        processSeries(seriesMap);
        processGenres(seriesMap);
        return List.copyOf(seriesMap.values());
    }

    private void processSeries(final Map<Integer, Serie> seriesMap) throws SQLException {
        while (this.getResultSet().next()) {
            final int seriesCode = this.getResultSet().getInt(SERIES_CODE);
            final Serie serie = seriesMap.computeIfAbsent(seriesCode, k -> {
                try {
                    return createSerie(seriesCode);
                } catch (SQLException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            });
            final Season season = createSeason(seriesCode);
            final Episode episode = createEpisode(seriesCode);
            final CastMember castMember = getNewCastMember();

            if (!serie.getSeasons().contains(season)) {
                season.addEpisode(episode);
                season.addCastMember(castMember);
                serie.addSeason(season);
            } else {
                final Season existingSeason = serie.getSeason(season);
                addEpisodeIfNotExists(existingSeason, episode);
                addCastMemberIfNotExists(existingSeason, castMember);
            }
        }
    }

    private void processGenres(final Map<Integer, Serie> seriesMap) throws SQLException {
        final String genreQuery = """
        SELECT NomeGenere, CodiceSerie, Descrizione, NumeroVisualizzati
        FROM categorizzazione_serie
        JOIN genere ON categorizzazione_serie.NomeGenere = genere.Nome""";
        this.setPreparedStatement(this.getConnection().prepareStatement(genreQuery));
        this.setResultSet(this.getPreparedStatement().executeQuery());

        while (this.getResultSet().next()) {
            final int seriesCode = this.getResultSet().getInt("CodiceSerie");
            final Genre genre = new Genre(
                    this.getResultSet().getString("NomeGenere"),
                    this.getResultSet().getString("Descrizione"),
                    this.getResultSet().getInt("NumeroVisualizzati"));

            if (seriesMap.containsKey(seriesCode)) {
                seriesMap.get(seriesCode).addGenre(genre);
            }
        }
    }

    private Serie createSerie(final int seriesCode) throws SQLException {
        return new Serie(
                seriesCode,
                this.getResultSet().getString("TitoloSerie"),
                this.getResultSet().getInt("EtaLimiteSerie"),
                this.getResultSet().getString("TramaSerie"),
                this.getResultSet().getInt("DurataComplessivaSerie"),
                this.getResultSet().getInt("NumeroEpisodiSerie"));
    }

    private Season createSeason(final int seriesCode) throws SQLException {
        return new Season(
                seriesCode,
                this.getResultSet().getInt("NumeroStagione"),
                this.getResultSet().getString("SuntoStagione"),
                this.getResultSet().getInt("CodiceCast"));
    }

    private Episode createEpisode(final int seriesCode) throws SQLException {
        return new Episode(
                seriesCode,
                this.getResultSet().getInt("NumeroStagione"),
                this.getResultSet().getInt("NumeroEpisodio"),
                this.getResultSet().getInt("DurataEpisodio"));
    }

    private void addEpisodeIfNotExists(final Season season, final Episode episode) {
        if (!season.getEpisodes().contains(episode)) {
            season.addEpisode(episode);
        }
    }

    private void addCastMemberIfNotExists(final Season season, final CastMember castMember) {
        if (!season.getCast().getCastMemberList().contains(castMember)) {
            season.addCastMember(castMember);
        }
    }

    private CastMember getNewCastMember() throws SQLException {
        final int memberCastCode =
                this.getResultSet().getInt("CodiceMembroCast");
        final String memberCastName =
                this.getResultSet().getString("NomeMembroCast");
        final String memberCastSurname =
                this.getResultSet().getString("CognomeMembroCast");
        final LocalDate memberCastBirthDate =
                this.getResultSet().getDate("DataNascitaMembroCast").toLocalDate();
        final LocalDate memberCastDebutDate =
                this.getResultSet().getDate("DataDebuttoCarrieraMembroCast").toLocalDate();
        final String memberCastArtisticName =
                this.getResultSet().getString("NomeArteMembroCast");
        final boolean isActor = this.getResultSet().getBoolean("TipoAttoreMembroCast");
        final boolean isDirector = this.getResultSet().getBoolean("TipoRegistaMembroCast");
        if (isActor && !isDirector) {
            return new Actor(
                    memberCastCode,
                    memberCastName,
                    memberCastSurname,
                    memberCastBirthDate,
                    memberCastDebutDate,
                    memberCastArtisticName);
        } else if (!isActor && isDirector) {
            return new Director(
                    memberCastCode,
                    memberCastName,
                    memberCastSurname,
                    memberCastBirthDate,
                    memberCastDebutDate,
                    memberCastArtisticName);
        } else if (isActor && isDirector) {
            return new CastMember(
                    memberCastCode,
                    memberCastName,
                    memberCastSurname,
                    memberCastBirthDate,
                    memberCastDebutDate,
                    memberCastArtisticName);
        }
        throw new IllegalArgumentException("Unable to determine member type");
    }
}
