package unibo.cineradar.model.login;

import unibo.cineradar.controller.security.HashingAlgorithm;
import unibo.cineradar.controller.security.PasswordChecker;
import unibo.cineradar.model.db.DBManager;
import unibo.cineradar.model.utente.Account;

import java.util.List;

public final class Logger {

    private Logger() {

    }

    public Account logIn(final String username, final String password) {
        try (final DBManager mgr = new DBManager()) {
            final String gotPassword = mgr.getUserCredentials(username);
            if (new PasswordChecker(HashingAlgorithm.SHA_512).checkPassword(password, gotPassword)) {

            }
        }
        throw new UnsupportedOperationException();
    }


}
