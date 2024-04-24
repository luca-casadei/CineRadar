package unibo.cineradar.model.context;

import unibo.cineradar.model.utente.Account;

import java.util.List;

/**
 * Generic session context.
 */
public interface SessionContext {
    /**
     * Gets the username of the generic logged account.
     *
     * @return A string containing the username.
     */
    String getUsername();

    /**
     * Gets the first name of the generic logged account.
     *
     * @return A string containing the first name.
     */
    String getFirstName();

    /**
     * Gets the last name of the generic logged account.
     *
     * @return A string containing the last name.
     */
    String getLastName();

    /**
     * Gets the account details of the generic logged account.
     *
     * @return A list of strings containing the every information of the generic account.
     */
    List<String> getAccountDetails();

    /**
     * Gets the account object.
     *
     * @return The account object.
     */
    Account getAccount();
}
