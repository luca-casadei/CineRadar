package unibo.cineradar.view.homepage.admin;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The ViewDocumentListener class implements the DocumentListener interface
 * to monitor changes in a Swing component's document.
 * It triggers a specified action whenever there's a modification in the document.
 */
public class ViewDocumentListener implements DocumentListener {

    private final Runnable checkFields;

    /**
     * Constructs a ViewDocumentListener with the specified action to be executed upon document changes.
     *
     * @param checkFields The action to be executed when a document change is detected.
     */
    public ViewDocumentListener(final Runnable checkFields) {
        this.checkFields = checkFields;
    }

    /**
     * Invoked when text is inserted into the document.
     *
     * @param e The DocumentEvent describing the change.
     */
    @Override
    public void insertUpdate(final DocumentEvent e) {
        checkFields.run();
    }

    /**
     * Invoked when text is removed from the document.
     *
     * @param e The DocumentEvent describing the change.
     */
    @Override
    public void removeUpdate(final DocumentEvent e) {
        checkFields.run();
    }

    /**
     * Invoked when attributes of the document are changed.
     *
     * @param e The DocumentEvent describing the change.
     */
    @Override
    public void changedUpdate(final DocumentEvent e) {
        checkFields.run();
    }
}
