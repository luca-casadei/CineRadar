package unibo.cineradar.model.utente;

public abstract class Account {
    private final String username;
    private final String password;
    private final String nome;
    private final String cognome;
    private final AccountType type;

    protected Account(final String username,
                      final String password,
                      final String nome,
                      final String cognome,
                      final AccountType type) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.type = type;
    }

    public final String getUsername() {
        return this.username;
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getNome() {
        return this.nome;
    }

    public final String getCognome() {
        return this.cognome;
    }

    public final AccountType getType() {
        return this.type;
    }
}
