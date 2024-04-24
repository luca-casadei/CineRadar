package unibo.cineradar.controller;

import unibo.cineradar.model.context.SessionContext;
import unibo.cineradar.model.context.SessionFactory;
import unibo.cineradar.model.context.administrator.AdministratorContext;
import unibo.cineradar.model.context.registrar.RegistrarContext;
import unibo.cineradar.model.context.user.UserContext;
import unibo.cineradar.model.login.LoginType;

import java.util.List;
import java.util.Objects;

/**
 * Class that represents generic session functions.
 */
public abstract class SessionControllerImpl implements SessionController {
    private final SessionContext context;

    /**
     * Creates the current session using the factory.
     *
     * @param username  The username to log.
     * @param password  The password to authenticate.
     * @param loginType The type of login to perform.
     */
    protected SessionControllerImpl(final String username, final char[] password, final LoginType loginType) {
        this.context = SessionFactory.getSession(username, password, loginType).orElse(null);
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
}
