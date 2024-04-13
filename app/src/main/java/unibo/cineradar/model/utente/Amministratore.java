package unibo.cineradar.model.utente;

public final class Amministratore extends Account {
    private final String numeroTelefono;

    public Amministratore(final String username,
                          final String password,
                          final String nome,
                          final String cognome) {
        this(username, password, nome, cognome, null);
    }

    public Amministratore(final String username,
                          final String password,
                          final String nome,
                          final String cognome,
                          final String numeroTelefono) {
        super(username, password, nome, cognome, AccountType.AMMINISTRATORE);
        this.numeroTelefono = numeroTelefono;
    }

    public String getNumeroTelefono() {
        return this.numeroTelefono;
    }
}
