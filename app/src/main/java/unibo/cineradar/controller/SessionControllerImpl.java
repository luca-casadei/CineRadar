package unibo.cineradar.controller;

import unibo.cineradar.controller.administrator.AdminSessionController;
import unibo.cineradar.controller.registrar.RegistrarSessionController;
import unibo.cineradar.controller.user.UserSessionController;
import unibo.cineradar.model.context.SessionContext;
import unibo.cineradar.model.context.SessionFactory;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.context.registrar.RegistrarContext;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.login.LoginType;
import unibo.cineradar.model.utente.Account;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class that represents generic session functions.
 */
public class SessionControllerImpl implements SessionController {
    private final SessionContext context;

    /**
     * Creates the current session using the factory.
     *
     * @param username The username to log.
     * @param password The password to authenticate.
     */
    protected SessionControllerImpl(final String username, final char[] password) {
        this.context = SessionFactory.getSession(username, password).orElse(null);
    }

    /**
     * Creates a specific controller starting from a generic one.
     *
     * @param existingController The existing controller.
     */
    protected SessionControllerImpl(final SessionController existingController) {
        switch (existingController.getType()) {
            case ADMINISTRATION -> {
                this.context = new AdministratorContext(existingController.getAccount());
            }
            case REGISTRATION -> {
                this.context = new RegistrarContext(existingController.getAccount());
            }
            case USER -> {
                this.context = new UserContext(existingController.getAccount());
            }
            default -> throw new IllegalStateException("Unexpected value: " + existingController.getType());
        }
    }

    /**
     * Creates a session controller from the inserted credentials.
     *
     * @param username The username of the account.
     * @param password The password of the account.
     * @return A session controller.
     */
    public static Optional<SessionController> of(final String username, final char[] password) {
        final SessionController ctr = new SessionControllerImpl(username, password);
        if (ctr.isSessionValid()) {
            switch (ctr.getType()) {
                case ADMINISTRATION -> {
                    return Optional.of(new AdminSessionController(ctr));
                }
                case REGISTRATION -> {
                    return Optional.of(new RegistrarSessionController(ctr));
                }
                case USER -> {
                    return Optional.of(new UserSessionController(ctr));
                }
                default -> throw new IllegalStateException("Unexpected type: " + ctr.getType());
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets the generic context of the session.
     *
     * @return The SessionContext of the current session.
     */
    protected final SessionContext getGenericContext() {
        return this.context;
    }

    @Override
    public final LoginType getType() {
        if (context instanceof RegistrarContext) {
            return LoginType.REGISTRATION;
        } else if (context instanceof AdministratorContext) {
            return LoginType.ADMINISTRATION;
        } else if (context instanceof UserContext) {
            return LoginType.USER;
        } else {
            throw new IllegalStateException("Unknown context type");
        }
    }

    @Override
    public final boolean isSessionValid() {
        return !Objects.isNull(context);
    }

    @Override
    public final List<String> getAccountDetails() {
        return context.getAccountDetails();
    }

    @Override
    public final Account getAccount() {
        return this.context.getAccount();
    }
}
