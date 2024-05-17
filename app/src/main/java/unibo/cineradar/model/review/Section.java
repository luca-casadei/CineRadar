package unibo.cineradar.model.review;

/**
 * This class represents a section of a review.
 *
 * @param name   The name of the section.
 * @param detail The full details of the section.
 */
public record Section(String name, String detail) {

    /**
     * Constructs a new Section with given name and detail.
     */
    public Section {
    }

    /**
     * Gets the name of the section.
     *
     * @return the name of the section
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Gets the detail of the section.
     *
     * @return the detail of the section
     */
    @Override
    public String detail() {
        return detail;
    }
}
