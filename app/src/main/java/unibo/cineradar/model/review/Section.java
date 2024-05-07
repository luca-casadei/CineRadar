package unibo.cineradar.model.review;

/**
 * This class represents a section of a review.
 */
public class Section {

    private final String name;
    private final String detail;

    /**
     * Constructs a new Section with given name and detail.
     *
     * @param name   the name of the section
     * @param detail the detail of the section
     */
    public Section(final String name, final String detail) {
        this.name = name;
        this.detail = detail;
    }

    /**
     * Gets the name of the section.
     *
     * @return the name of the section
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the detail of the section.
     *
     * @return the detail of the section
     */
    public String getDetail() {
        return detail;
    }
}
