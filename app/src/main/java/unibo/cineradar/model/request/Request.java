package unibo.cineradar.model.request;

import java.sql.Date;

/**
 * The Request class.
 */
public class Request {
    private final int number;
    private final String username;
    private final String type;
    private final String title;
    private final Date releaseDate;
    private final String description;
    private final boolean closed;

    /**
     * Constructs a request instance.
     *
     * @param number        The number of the request.
     * @param username      The username of who wrote the request.
     * @param type          The type of the request (Film or TV Series).
     * @param title         The title of the Film or TV Series to add.
     * @param description   The description of the request.
     * @param closed        The state of the request.
     * @param releaseDate   The Date of Release of the Film or TV Series.
     */
    public Request(final int number,
                   final String username,
                   final boolean type,
                   final String title,
                   final String description,
                   final boolean closed,
                   final Date releaseDate) {
        this.number = number;
        this.title = title;
        this.username = username;
        this.type = type ? "SerieTV" : "Film";
        this.releaseDate = (Date) releaseDate.clone();
        this.description = description;
        this.closed = closed;
    }

    /**
     * Gets the number of the request.
     *
     * @return An integer containing the id of the request.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Gets the username of who wrote the request.
     *
     * @return A string containing the username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets title of the request.
     *
     * @return A string containing title of the request.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the release date of the film/series.
     *
     * @return A date containing the release date of the film/series.
     */
    public Date getReleaseDate() {
        return (Date) this.releaseDate.clone();
    }

    /**
     * Gets the description of the request.
     *
     * @return A string containing the description of the request.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the state of the request.
     *
     * @return A boolean (True if closed, False if still open)
     * containing the state of the request.
     */
    public boolean isClosed() {
        return this.closed;
    }

    /**
     * Gets the type of the request.
     *
     * @return A string containing the type of request.
     */
    public String getType() {
        return this.type;
    }
}
